package Parser;
import Nodes.BasicNode;
import Objects.*;
import com.google.gson.*;

import java.lang.reflect.Type;

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

            switch(nodeType){
                case "VariableDeclarator":
                    JsonObject id = jsonObj.getAsJsonObject("id");
                    String name = id.get("name").getAsString();
                    String varType;
                    JsonElement value = jsonObj.get("init");
                    if(value.isJsonNull()){
                        varType = null;
                    } else varType = inferType(jsonObj.getAsJsonObject("init"));

                    Parser.addVar(name, varType);
                    break;
                case "AssignmentExpression":
                    break;

            }

            //Parser.variables.put(Parser.varToAnalyze, varType);
            Parser.varToAnalyze = null;
            Parser.infer = false;

            if(classToUse.equals(AssignmentExpression.class)){
                if(classToUse.equals(Identifier.class) && Parser.infer){
                    JsonObject id = jsonObj.get("left").getAsJsonObject();
                    //if id type = identifier
                    //then add vartype to jsonObj value: 
                }
            }



            return jsonDeserializationContext.deserialize(jsonElement, classToUse); // automatic desearialization.
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
        }
        return varType;
    }

    private String inferBinary(JsonObject jsonObj) throws Exception{
        String varType;
        String right = inferType(jsonObj.getAsJsonObject("right"));
        String left = inferType(jsonObj.getAsJsonObject("left"));
        String operator = jsonObj.getAsJsonObject("operator").getAsString();
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
