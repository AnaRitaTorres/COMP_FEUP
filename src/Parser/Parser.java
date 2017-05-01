package Parser;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

import Esprima.rhino.Esprima;
import Nodes.BasicNode;
import Nodes.Expression;
import Nodes.Reference;
import com.google.gson.*;
import com.google.gson.stream.*;
import Objects.*;

import javax.script.ScriptException;

public class Parser {

    public static HashMap<String, String> variables = new HashMap<>();
    public static String varToAnalyze;
    public static boolean infer = false;

    public static void main(String[] args) throws JsonParseException {

        if(args.length != 1){
            System.out.println("java Parser <jsFile>.");
            return;
        }

        try {
            String jsonPath=Esprima.readJS2JSON(args[0]);
            String esprima = readEsprima(jsonPath);

        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        for (String string :
                variables.keySet()) {
            System.out.println(string);
        }
        //System.out.print(esprima);

        //InputStream esprimaStream = new ByteArrayInputStream(esprima.getBytes());
        //try {
        // esprimaStream.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    public static String readEsprima(String json) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BasicNode.class, new NodeDeserializer())
                .registerTypeAdapter(Reference.class, new NodeDeserializer())
                .registerTypeAdapter(Expression.class, new NodeDeserializer())
                .create();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(json));
            Root init = gson.fromJson(reader, Root.class);
            //init.print();
        } catch (FileNotFoundException err) {
            System.err.println(err);
        }
        return "nope";
    }
}