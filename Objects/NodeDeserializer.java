package Objects;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.lang.reflect.Type;

import java.util.Map;
import java.util.TreeMap;

public class NodeDeserializer implements JsonDeserializer<Class<? extends BasicNode>> {

    private static Map<String, Class> map = new TreeMap<>();

        static {
            map.put("BasicNode", BasicNode.class);
            map.put("Literal", Literal.class);
            map.put("BinaryExpression", BinaryExpression.class);
        }

    @Override
    public Class deserialize(JsonElement jsonElement, Type type,  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            JsonObject jsonObj = jsonElement.getAsJsonObject();  //vao buscar o objeto atual como um JsonObject para poderem ir buscar a propriedade que querem
            JsonElement nodeTypeEl = jsonObj.get("nodetype");      // get the type of the node so we can use the correct class
            if (nodeTypeEl == null) {
                throw new RuntimeException("nodetype property must be defined!"); // all JSON objects must have the property nodetype
            }

            String nodeType = nodeTypeEl.getAsString(); //simply casting the object as string
            System.out.println(nodeType);

            System.out.println("eu vi um sapo");
            Class classToUse = map.get(nodeType);
            // Class classToUse = (Class<? extends BasicNode>) getClassToUse(nodeType); //somehow get the Class to use based on the node type given
            return jsonDeserializationContext.deserialize(jsonElement, classToUse); // automatic desearialization.
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

}
