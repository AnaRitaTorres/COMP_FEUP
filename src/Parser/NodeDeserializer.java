package Parser;
import Nodes.BasicNode;
import Objects.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NodeDeserializer implements JsonDeserializer<BasicNode> {

    @Override
    public BasicNode deserialize(JsonElement jsonElement, Type type,  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            JsonObject jsonObj = jsonElement.getAsJsonObject();  //vao buscar o objeto atual como um JsonObject para poderem ir buscar a propriedade que querem
            JsonElement nodeTypeEl = jsonObj.get("type");      // get the type of the node so we can use the correct class
            if (nodeTypeEl == null) {
                throw new RuntimeException("type property must be defined!"); // all JSON objects must have the property nodetype
            }

            String nodeType = nodeTypeEl.getAsString(); //simply casting the object as string

            Class<? extends BasicNode> classToUse = getClassToUse(nodeType); //somehow get the Class to use based on the node type given

            switch (nodeType) {
                case "VariableDeclarator":
                    JsonObject id = jsonObj.getAsJsonObject("id");
                    String name = id.get("name").getAsString();
                    String varType;
                    JsonElement value = jsonObj.get("init");
                    if (value.isJsonNull()) {
                        varType = null;
                    } else varType = inferType(jsonObj.getAsJsonObject("init"));

                    Parser.addVar(name, varType);
                    break;
                case "AssignmentExpression":
                    id = jsonObj.getAsJsonObject("left");
                    name = id.get("name").getAsString();
                    value = jsonObj.getAsJsonObject("right");
                    varType = inferType(value.getAsJsonObject());
                    Parser.addVar(name, varType);
                    break;
            }

            return jsonDeserializationContext.deserialize(jsonElement, classToUse); // automatic deserialization.
        } catch (JsonSyntaxException e){
            throw new JsonSyntaxException(e.getMessage());
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    private String inferType(JsonObject jsonObj) throws Exception{
        String type = jsonObj.get("type").getAsString();
        String varType = null;
        switch(type){
            case "Literal":
                varType = inferLiteral(jsonObj);
                break;
            case "BinaryExpression":
                varType = inferBinary(jsonObj);
                break;
            case "Identifier":
                String name = jsonObj.get("name").getAsString();
                varType = Parser.getVarType(name);
                break;
            case "AssignmentExpression":
                JsonObject var = jsonObj.getAsJsonObject("left");
                varType = inferType(jsonObj.getAsJsonObject("right"));
                Parser.addVar(var.get("name").getAsString(), varType);
                break;
            case "ArrayExpression":
                varType = "ArrayList<";
                JsonArray array = jsonObj.getAsJsonArray("elements");
                String arrayType = analyzeArray(array);
                varType += arrayType + ">";
                break;
        }
        return varType;
    }

    private String analyzeArray(JsonArray array) throws Exception{
        String type = "";

        for (int i = 0; i < array.size(); i++) {
            String nextType = inferType(array.get(i).getAsJsonObject());
            if(nextType.equals("int"))
                nextType = "Integer";
            else if(nextType.equals("long"))
                nextType = "Long";
            if(!type.isEmpty()){
                if(nextType.contains("ArrayList") && type.contains("ArrayList")){
                    if(!nextType.equals(type)){
                        throw new Exception("Array contains incompatible elements: " + nextType + " and " + type);
                    }
                }else if(nextType.contains("ArrayList") ^ type.contains("ArrayList")){
                    throw new Exception("Array contains incompatible elements: " + nextType + " and " + type);
                    //TODO in case of type number and next is String. See if String is a number with regex
                }else if(nextType.equals("String")){
                    type = nextType;
                } else if(nextType.equals("Long") && type.equals("Integer")){
                    type = nextType;
                }
            } else type = nextType;
        }

        return type;
    }

    private String inferBinary(JsonObject jsonObj) throws Exception{
        String varType;
        String right = inferType(jsonObj.getAsJsonObject("right"));
        String left = inferType(jsonObj.getAsJsonObject("left"));
        String operator = jsonObj.get("operator").getAsString();
        if(right.equals("String") || left.equals("String")){
            if(operator.equals("+")){
                varType = "String";
            }else{
                throw new Exception("String operations in Java are \"+\" only");
            }
        }else if(right.equals("long") || left.equals("long")){
            varType = "long";
        }else{
            varType = "int";
        }
        return varType;
    }

    private String inferLiteral(JsonObject jsonObj){
        String varType;
        JsonElement value = jsonObj.get("value");
        try {
            value.getAsInt();
            if(value.getAsString().contains("."))
                varType = "long";
            else varType = "int";
        }catch(Exception err){
            varType = "String";
        }
        return varType;
    }

    private Class<? extends BasicNode> getClassToUse(String nodeType) {
        return (Class<? extends BasicNode>) MyClass.valueOf(nodeType.toUpperCase()).myClass; // we use an enum to map a node type to a specific class
    }

}
