package Parser;

import java.io.*;

import Nodes.BasicNode;
import Nodes.Expression;
import Nodes.Reference;
import com.google.gson.*;
import com.google.gson.stream.*;
import Objects.*;

public class Parser {

    public static void main(String[] args) throws JsonParseException {

        String esprima = readEsprima();

        //System.out.print(esprima);

//        InputStream esprimaStream = new ByteArrayInputStream(esprima.getBytes());

//        try {
//            esprimaStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static String readEsprima() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BasicNode.class, new NodeDeserializer())
                .registerTypeAdapter(Reference.class, new NodeDeserializer())
                .registerTypeAdapter(Expression.class, new NodeDeserializer())
                .create();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("esprima.json"));
            Root init = gson.fromJson(reader, Root.class);
            init.print();
        } catch (FileNotFoundException err) {

        }
        return "nope";
    }
}