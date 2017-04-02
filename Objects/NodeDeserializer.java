import com.google.gson.*;
import com.google.gson.stream.*;
import Objects.*;
import java.lang.reflect.Type;


public class NodeDeserializer implements JsonDeserializer<Class<? extends BasicNode>> {


    @Override
    public Class<? extends BasicNode> deserialize(JsonElement jsonElement, Type type,  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            JsonObject jsonObj = jsonElement.getAsJsonObject();  //vao buscar o objeto atual como um JsonObject para poderem ir buscar a propriedade que querem
            JsonElement nodeTypeEl = jsonObj.get("nodetype");      // get the type of the node so we can use the correct class
            if (nodeTypeEl == null) {
                throw new RuntimeException("nodetype property must be defined!"); // all JSON objects must have the property nodetype
            }

            String nodeType = nodeTypeEl.getAsString(); //simply casting the object as string
            System.out.println(nodeType);
            Class classToUse = getClassToUse(nodeType); //somehow get the Class to use based on the node type given
            return jsonDeserializationContext.deserialize(jsonElement, classToUse); // automatic desearialization.
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    private Class getClassToUse(String nodeType) {
        return MyClass.valueOf(nodeType.toUpperCase()).myClass; // we use an enum to map a node type to a specific class
    }
}
