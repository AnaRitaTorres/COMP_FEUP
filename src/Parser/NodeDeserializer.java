package Parser;
import Nodes.BasicNode;
import Objects.AssignmentExpression;
import Objects.Identifier;
import Objects.MyClass;
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
            if(Parser.varToAnalyze != null){
                String varType = "";
                switch(nodeType){
                    case "Literal":
                        JsonElement value = jsonObj.get("value");
                        try {
                            value.getAsInt();
                            if(value.getAsString().contains("."))
                                varType = "long";
                            else varType = "int";
                        }catch(Exception err){
                            varType = "String";
                        }
                        break;
                }
                Parser.variables.put(Parser.varToAnalyze, varType);
                Parser.varToAnalyze = null;
                Parser.infer = false;
            }

            if(classToUse.equals(AssignmentExpression.class)){
                Parser.infer = true;
            }
            if(classToUse.equals(Identifier.class) && Parser.infer){
                Parser.varToAnalyze = jsonObj.get("name").getAsString();
            }


            return jsonDeserializationContext.deserialize(jsonElement, classToUse); // automatic desearialization.
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    private Class<? extends BasicNode> getClassToUse(String nodeType) {
        return (Class<? extends BasicNode>) MyClass.valueOf(nodeType.toUpperCase()).myClass; // we use an enum to map a node type to a specific class
    }

}
