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

public class Parser {

    public static ArrayList<HashMap<String, String>> variables = new ArrayList<>();
    private static ArrayList<String> returns = new ArrayList<>();
    static Types types;

    public static void main(String[] args) throws JsonParseException, FileNotFoundException {

        if(args.length > 2 && args.length < 1){
            System.out.println("java Parser <jsFile> optional:<jsonFile>");
            return;
        }

        Parser parser=new Parser();
        if(args.length == 2)
            parser.start(args[0],args[1]);
        else parser.start(args[0], null);
    }

    public Parser() throws FileNotFoundException {
        File directory = new File("resources/JSONFiles");
        if(!directory.exists())
            directory.mkdir();

        types = new Types();
    }

    public String start(String jsFile, String jsonFile){
        if(jsonFile!=null && !jsonFile.isEmpty()) {
            try {
                Gson gson=new Gson();
                types=gson.fromJson(new FileReader(jsonFile), Types.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            String jsonPath = Esprima.readJS2JSON(jsFile);
            readEsprima(jsonPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String javaCode=ParserUt.getInstance().printFile();
        ParserUt.getInstance().reset();
        return javaCode;
    }

    private static void readEsprima(String json) {

        GsonFireBuilder fireBuilder = new GsonFireBuilder()
                .registerPostProcessor(BlockStatement.class, new PostProcessor<BlockStatement>() {
                    @Override
                    public void postDeserialize(BlockStatement result, JsonElement src, Gson gson) {
                        HashMap <String, String> map = variables.get(variables.size()-1);
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

                .registerPostProcessor(FunctionDeclaration.class, new PostProcessor<FunctionDeclaration>() {
                    @Override
                    public void postDeserialize(FunctionDeclaration result, JsonElement src, Gson gson) {
                        variables.remove(variables.size()-1);
                        String ret = returns.get(returns.size()-1);
                        if(ret.isEmpty()) ret = "void";
                        result.setReturn(ret);
                        returns.remove(returns.size()-1);
                    }

                    @Override
                    public void postSerialize(JsonElement result, FunctionDeclaration src, Gson gson) {

                    }
                })
                .registerPreProcessor(FunctionDeclaration.class, new PreProcessor<FunctionDeclaration>() {
                    @Override
                    public void preDeserialize(Class<? extends FunctionDeclaration> clazz, JsonElement src, Gson gson) {
                        variables.add(new HashMap<>());
                        returns.add("");
                        JsonArray args = src.getAsJsonObject().getAsJsonArray("params");
                        String functionName = src.getAsJsonObject().getAsJsonObject("id").get("name").getAsString();
                        try {
                            ArrayList<String> argsTypes = types.getTypeArgumentFunction(functionName, args.size());
                            if(argsTypes != null) {
                                for (int i = 0; i < argsTypes.size(); i++) {
                                    String argName = args.get(i).getAsJsonObject().get("name").getAsString();
                                    String type = argsTypes.get(i);
                                    addVar(argName, type);
                                }
                            }
                            addVar("function", functionName);

                        } catch (Exception e){
                            e.printStackTrace();
                        }
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

    static void addVar(String var, String type) throws JsonSyntaxException{
        HashMap<String, String> last = variables.get(variables.size()-1);
        String oldType = last.get(var);
        if(oldType != null)
            if(oldType.equals(type)) return;
            else throw new JsonSyntaxException("Cannot redefine variable type in java. Trying to change variable " + var);
        last.put(var, type);
        variables.set(variables.size()-1, last);
    }
    
    static boolean existsInScope(String var){
        for (int i = variables.size()-1; i >= 0; i--) {
            HashMap<String, String> scope = variables.get(i);
            if(scope.containsKey(var)){
                return true;
            } else if(scope.containsKey("function")){
                return false;
            }
        }
        return false;
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

    static String compareTypes(String type, String nextType) throws Exception{
        if(type != null && !type.isEmpty()){
            if(nextType.contains("ArrayList") && type.contains("ArrayList")){
                if(!nextType.equals(type)){
                    throw new Exception("Array contains incompatible elements: " + nextType + " and " + type);
                }
                return type;
            }else if(nextType.contains("ArrayList") ^ type.contains("ArrayList")){
                throw new Exception("Array contains incompatible elements: " + nextType + " and " + type);
            }else if(nextType.equals("String")){
                return nextType;
            } else if(nextType.equals("Double") && type.equals("Integer")){
                return nextType;
            } else return type;
        } else return nextType;
    }

    static String compareVarTypes(String type, String nextType) throws Exception{
        if(!type.isEmpty()) {
            if (type.equals(nextType)) return type;
            if ((type.equals("int") && nextType.equals("double"))
                    || type.equals("double") && nextType.equals("int")) {
                return "double";
            } else {
                throw new Exception("Trying to save incompatible return types in same function");
            }
        }else {
            return nextType;
        }
    }

    static void saveReturn(String returnType) throws Exception{

        String ret = returns.get(returns.size()-1);
        try {
            ret = compareVarTypes(ret, returnType);
            returns.set(returns.size()-1, ret);
        }catch(Exception e){
            throw new Exception("Trying to return incompatible return types in same function");
        }
    }

    public enum PrintState{
        GLOBAL_VARIABLES,FUNCTIONS,MAIN
    }

    public static Types getTypes(){
        return types;
    }
}