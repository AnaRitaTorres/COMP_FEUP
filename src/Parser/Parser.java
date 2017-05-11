package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Esprima.rhino.Esprima;
import Nodes.BasicNode;
import Nodes.Expression;
import Nodes.Reference;
import Utils.ParserUt;
import com.google.gson.*;
import com.google.gson.stream.*;
import Objects.*;
import gsonfire.GsonFireBuilder;
import gsonfire.PostProcessor;
import gsonfire.PreProcessor;

import javax.script.ScriptException;

public class Parser {

    public static ArrayList<HashMap<String, String>> variables = new ArrayList<>();
    //public static HashMap<String, String> variables = new HashMap<>();
    public static String varToAnalyze;
    public static boolean infer = false;

    public static void main(String[] args) throws JsonParseException {

        if(args.length != 1){
            System.out.println("java Parser <jsFile>.");
            return;
        }

        try {
            String jsonPath=Esprima.readJS2JSON(args[0]);
            readEsprima(jsonPath);
            ParserUt.getInstance().printFile();

        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }

//        for (String string : variables.keySet()) {
//            System.out.println(string);
//        }

        //InputStream esprimaStream = new ByteArrayInputStream(esprima.getBytes());
        //try {
        // esprimaStream.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    public static void readEsprima(String json) {

        GsonFireBuilder fireBuilder = new GsonFireBuilder()
                .registerPostProcessor(BlockStatement.class, new PostProcessor<BlockStatement>() {
                    @Override
                    public void postDeserialize(BlockStatement result, JsonElement src, Gson gson) {
                        String a = src.toString();
                        System.out.println(result);
                        System.out.println(a);
                        System.out.println(gson);
                        HashMap <String, String> map = variables.get(variables.size()-1);
                        for (String key: map.keySet()){
                            System.out.println(map.get(key) + " " + key);
                        }

                        variables.remove(variables.size()-1);
                    }

                    @Override
                    public void postSerialize(JsonElement result, BlockStatement src, Gson gson) {

                    }
                })
                .registerPreProcessor(BlockStatement.class, new PreProcessor<BlockStatement>() {
                    @Override
                    public void preDeserialize(Class<? extends BlockStatement> clazz, JsonElement src, Gson gson) {
                        System.out.println(clazz);
                        System.out.println(src);
                        System.out.println(gson);
                        variables.add(new HashMap<>());
                    }
                })
                .registerPreProcessor(Root.class, new PreProcessor<Root>() {
                    @Override
                    public void preDeserialize(Class<? extends Root> clazz, JsonElement src, Gson gson) {
                        variables.add(new HashMap<>());
                    }
                })
                .registerPostProcessor(Root.class, new PostProcessor<Root>() {
                    @Override
                    public void postDeserialize(Root result, JsonElement src, Gson gson) {
                        HashMap <String, String> map = variables.get(variables.size()-1);
                        for (String key: map.keySet()){
                            System.out.println(map.get(key) + " " + key);
                        }
                        variables.remove(variables.size()-1);
                    }

                    @Override
                    public void postSerialize(JsonElement result, Root src, Gson gson) {

                    }
                });

        Gson gson = fireBuilder.createGsonBuilder()
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
    }

    public static boolean addVar(String var, String type){
        HashMap<String, String> last = variables.get(variables.size()-1);
        last.put(var, type);
        variables.set(variables.size()-1, last);
        return true;
    }

    public static String getVarType(String varName) throws Exception{
        String type;
        for (int i = variables.size()-1; i >= 0; i--) {
            HashMap<String, String> scope = variables.get(i);
            if(scope.containsKey(varName)){
                return scope.get(varName);
            }
        }

        throw new Exception("variable wasn't defined before.");
    }
}