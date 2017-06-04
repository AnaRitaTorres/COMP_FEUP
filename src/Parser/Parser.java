package Parser;

import java.io.*;
import java.util.*;

import Esprima.rhino.Esprima;
import Nodes.BasicNode;
import Nodes.Expression;
import Nodes.Reference;
import com.google.gson.*;
import com.google.gson.stream.*;
import Objects.*;
import gsonfire.GsonFireBuilder;
import gsonfire.PostProcessor;
import gsonfire.PreProcessor;

import javax.script.ScriptException;

public class Parser {

    public static ArrayList<HashMap<String, String>> variables = new ArrayList<>();
    public static String varToAnalyze;
    public static boolean infer = false;
    public static Types types;

    public static void main(String[] args) throws JsonParseException, FileNotFoundException {

        if(args.length != 1){
            System.out.println("java Parser <jsFile>.");
            return;
        }

        Parser parser=new Parser();
        parser.start(args[0]);
    }

    public Parser() throws FileNotFoundException {
        Gson gson=new Gson();
        types=gson.fromJson(new FileReader("resources/JSONFiles/types.json"),Types.class);

        File directory = new File("resources/JSONFiles");
        if(!directory.exists())
            directory.mkdir();
    }

    public void start(String jsFile){
        try {
            String jsonPath = Esprima.readJS2JSON(jsFile);
            readEsprima(jsonPath);
            ParserUt.getInstance().printFile();
        } catch (JsonSyntaxException e){
            System.err.println(e.getMessage());
        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void readEsprima(String json) {

        GsonFireBuilder fireBuilder = new GsonFireBuilder()
                .registerPostProcessor(BlockStatement.class, new PostProcessor<BlockStatement>() {
                    @Override
                    public void postDeserialize(BlockStatement result, JsonElement src, Gson gson) {
                        HashMap <String, String> map = variables.get(variables.size()-1);
//                        for (String key: map.keySet()){
//                            System.out.println(map.get(key) + " " + key);
//                        }
                        result.setVariables(map);
                        variables.remove(variables.size()-1);
                    }

                    @Override
                    public void postSerialize(JsonElement result, BlockStatement src, Gson gson) {

                    }
                })
                .registerPreProcessor(BlockStatement.class, new PreProcessor<BlockStatement>() {
                    @Override
                    public void preDeserialize(Class<? extends BlockStatement> clazz, JsonElement src, Gson gson) {
                        variables.add(new HashMap<>());
                    }
                })


                .registerPostProcessor(ReturnStatement.class, new PostProcessor<ReturnStatement>() {
                    @Override
                    public void postDeserialize(ReturnStatement result, JsonElement src, Gson gson) {
                        HashMap <String, String> map = variables.get(variables.size()-1);
//                        for (String key: map.keySet()){
//                            System.out.println(map.get(key) + " " + key);
//                        }
                        //result.setVariables(map);
                        variables.remove(variables.size()-1);
                    }

                    @Override
                    public void postSerialize(JsonElement result, ReturnStatement src, Gson gson) {

                    }
                })
                .registerPreProcessor(ReturnStatement.class, new PreProcessor<ReturnStatement>() {
                    @Override
                    public void preDeserialize(Class<? extends ReturnStatement> clazz, JsonElement src, Gson gson) {
                        variables.add(new HashMap<>());
                    }
                })


                .registerPostProcessor(FunctionDeclaration.class, new PostProcessor<FunctionDeclaration>() {
                    @Override
                    public void postDeserialize(FunctionDeclaration result, JsonElement src, Gson gson) {
                        HashMap <String, String> map = variables.get(variables.size()-1);
//                        for (String key: map.keySet()){
//                            System.out.println(map.get(key) + " " + key);
//                        }
                        //result.setVariables(map);
                        variables.remove(variables.size()-1);
                    }

                    @Override
                    public void postSerialize(JsonElement result, FunctionDeclaration src, Gson gson) {

                    }
                })
                .registerPreProcessor(FunctionDeclaration.class, new PreProcessor<FunctionDeclaration>() {
                    @Override
                    public void preDeserialize(Class<? extends FunctionDeclaration> clazz, JsonElement src, Gson gson) {
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
//                        for (String key: map.keySet()){
//                            System.out.println(map.get(key) + " " + key);
//                        }
                        result.setVariables(map);
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
            init.print();
        } catch (FileNotFoundException err) {
            System.err.println(err);
        }
    }

    public static void addVar(String var, String type) throws JsonSyntaxException{
        HashMap<String, String> last = variables.get(variables.size()-1);
        String oldType = last.get(var);
        if(oldType != null)
            if(oldType.equals(type)) return;
            else throw new JsonSyntaxException("Cannot redefine variable type in java. Trying to change variable " + var);
        last.put(var, type);
        variables.set(variables.size()-1, last);
    }

    public static String getVarType(String varName) throws Exception{
        String type;
        for (int i = variables.size()-1; i >= 0; i--) {
            HashMap<String, String> scope = variables.get(i);
            if(scope.containsKey(varName)){
                return scope.get(varName);
            }
        }

        throw new JsonSyntaxException("Variable " + varName + " wasn't defined before.");
    }

    public enum PrintState{
        GLOBAL_VARIABLES,FUNCTIONS,MAIN
    }

    public static Types getTypes(){
        return types;
    }
}