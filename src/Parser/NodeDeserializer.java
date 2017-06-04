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
                    String leftType = id.get("type").getAsString();
                    value = jsonObj.getAsJsonObject("right");
                    if(leftType.toUpperCase().equals(MyClass.IDENTIFIER.name())) {
                        name = id.get("name").getAsString();
                        varType = inferType(value.getAsJsonObject());
                    }else if(leftType.toUpperCase().equals(MyClass.MEMBEREXPRESSION.name())){
                        name = getVarFromMember(id);
                        varType = "ArrayList<" + inferType(value.getAsJsonObject()) + ">";
                    } else{
                        name = "";
                        varType = "";
                    }
                    Parser.getVarType(name);
                    Parser.addVar(name, varType);
                    break;
                case "CallExpression":
                    analyzeFunction(jsonObj);
                    break;
                case "ReturnStatement":
                    JsonObject arg = jsonObj.getAsJsonObject("argument");
                    varType = inferType(arg);
                    Parser.saveReturn(varType);
                    break;
                default: break;
            }

            return jsonDeserializationContext.deserialize(jsonElement, classToUse); // automatic deserialization.
        } catch (JsonSyntaxException e){
            throw new JsonSyntaxException(e.getMessage());
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    private void analyzeFunction(JsonObject jsonObj) throws Exception{
        JsonArray arguments = jsonObj.getAsJsonArray("arguments");
        JsonObject callee = jsonObj.getAsJsonObject("callee");
        if(!callee.get("type").getAsString().equals("Identifier")) return;
        ArrayList<String> args = new ArrayList<>();
        String functionName = callee.get("name").getAsString();
        for (int i = 0; i < arguments.size(); i++) {
            JsonObject element = arguments.get(i).getAsJsonObject();
            String type = inferType(element);
            args.add(type);
        }
        System.out.println(args);
        Parser.types.addFunction(functionName, args);
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
            else if(nextType.equals("double"))
                nextType = "Double";
            type = Parser.compareTypes(type, nextType);
        }

        return type;
    }

    private String inferBinary(JsonObject jsonObj) throws Exception{
        String varType;
        String right = inferType(jsonObj.getAsJsonObject("right"));
        String left = inferType(jsonObj.getAsJsonObject("left"));
        String operator = jsonObj.get("operator").getAsString();
        if(ParserUt.getInstance().isLogicalOperator(operator)){
            varType = "boolean";
        }else if(right.equals("String") || left.equals("String")){
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
        JsonElement value = jsonObj.get("raw");
        try {
            value.getAsDouble();
            if(!value.getAsString().contains("."))
                varType = "int";
            else varType = "double";
        }catch(Exception err){

            if(value.getAsBoolean()){
                varType = "boolean";
            }else varType = "String";
        }
        return varType;
    }

    private String getVarFromMember(JsonObject jsonObj) {
        String name;
        JsonObject obj = jsonObj.getAsJsonObject("object");
        if(obj.get("type").getAsString().equals("MemberExpression")){
            name = getVarFromMember(obj);
        }else name = obj.get("name").getAsString();

        return name;
    }

    private Class<? extends BasicNode> getClassToUse(String nodeType) {
        return (Class<? extends BasicNode>) MyClass.valueOf(nodeType.toUpperCase()).myClass; // we use an enum to map a node type to a specific class
    }

}
