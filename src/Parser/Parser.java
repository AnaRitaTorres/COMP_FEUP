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
        } else types = new Types();


        String javaCode;
        try {
            String jsonPath = Esprima.readJS2JSON(jsFile);
            readEsprima(jsonPath);
            javaCode=ParserUt.getInstance().printFile(true);
        } catch (ScriptException e){
            ParserUt.getInstance().eraseBuffer();
            ParserUt.getInstance().writeToBuffer("There is a syntax error in your code.\n" + e.getLocalizedMessage());
            javaCode=ParserUt.getInstance().printFile(false);
        } catch (Exception e) {
            ParserUt.getInstance().eraseBuffer();
            ParserUt.getInstance().writeToBuffer("Error parsing from JS to Java.\n" + e.getMessage());
            javaCode=ParserUt.getInstance().printFile(false);


        }

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
                    public void postDeserialize(FunctionDeclaration result, JsonElement src, Gson gson){
                        variables.remove(variables.size()-1);
                        String ret = returns.get(returns.size()-1);
                        String name = src.getAsJsonObject().getAsJsonObject("id").get("name").getAsString();
                        JsonArray params = src.getAsJsonObject().getAsJsonArray("params");
                        if(ret.isEmpty()) ret = "void";
                        result.setReturn(ret);
                        ArrayList<String> par = new ArrayList<>();
                        for (int i = 0; i < params.size(); i++) {
                            par.add("");
                        }
                        try {
                            types.addFunction(name, par);
                            types.setReturn(name, ret);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
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

                            HashMap<String, String> vars = types.getVarsInFunction(functionName);
                            if(vars != null){
                                for (String var : vars.keySet()) {
                                    addVar(var, vars.get(var));
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
                        HashMap<String, String> globals = types.getGlobalVars();
                        globals.put("function", "root");
                        variables.add(globals);
                        variables.add(new HashMap<>());
                    }
                })
                .registerPostProcessor(Root.class, new PostProcessor<Root>() {
                    @Override
                    public void postDeserialize(Root result, JsonElement src, Gson gson) {
                        HashMap <String, String> map = variables.get(variables.size()-1);
                        result.setVariables(map);
                        variables.clear();
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
        try {
            String newType = compareVarTypes(oldType, type);
            last.put(var, newType);
            variables.set(variables.size() - 1, last);
        }catch (JsonSyntaxException e){
            throw new JsonSyntaxException("Trying to redefine variable " + var + " from " + oldType + " to " + type);
        }
    }

    static void assignVar(String var, String type){
        for (int i = variables.size()-1; i >= 0; i--) {
            HashMap<String, String> scope = variables.get(i);
            if(scope.containsKey(var)){
                String old = scope.get(var);
                String newType = compareVarTypes(old, type);
                scope.put(var, newType);
                variables.set(i, scope);
                return;
            }
        }
    }
    
    static String existsInScope(String var){
        for (int i = variables.size()-1; i >= 0; i--) {
            HashMap<String, String> scope = variables.get(i);
            if(scope.containsKey(var)){
                if(scope.containsKey("function")){
                    return scope.get(var);
                }
                return null;
            } else if(scope.containsKey("function")){
                return "";
            }
        }
        return "";
    }

    public static String getVarType(String varName) throws JsonSyntaxException{

        for (int i = variables.size()-1; i >= 0; i--) {
            HashMap<String, String> scope = variables.get(i);
            if(scope.containsKey(varName)){
                return scope.get(varName);
            }
        }

        throw new JsonSyntaxException("Variable " + varName + " wasn't defined before.\nCheck if the argument variable type is in the optional json or given a value before.");
    }

    static String compareTypes(String type, String nextType) throws JsonSyntaxException{
        if(type != null && !type.isEmpty()){
            if(nextType.contains("ArrayList") && type.contains("ArrayList")){
                if(!nextType.equals(type)){
                    throw new JsonSyntaxException("Array contains incompatible elements: " + nextType + " and " + type);
                }
                return type;
            }else if(nextType.contains("ArrayList") ^ type.contains("ArrayList")){
                throw new JsonSyntaxException("Array contains incompatible elements: " + nextType + " and " + type);
            }else if(nextType.equals("String")){
                return nextType;
            } else if(nextType.equals("Double") && type.equals("Integer")){
                return nextType;
            } else return type;
        } else return nextType;
    }

    static String compareVarTypes(String type, String nextType) throws JsonSyntaxException{
        if(nextType != null && nextType.isEmpty()) return type;
        if(type != null && !type.isEmpty()) {
            if (type.equals(nextType)) return type;
            if ((type.equals("int") && nextType.equals("double"))
                    || type.equals("double") && nextType.equals("int")) {
                return "double";
            } else if(type.contains("ArrayList") && nextType.contains("ArrayList")){
                int i0 = type.indexOf("<") + 1, i1 = type.lastIndexOf(">");
                int n0 = nextType.indexOf("<") + 1, n1 = nextType.lastIndexOf(">");
                String type1 = type.substring(i0, i1);
                String type2 = nextType.substring(n0, n1);
                return "ArrayList<" + compareVarTypes(type1, type2) + ">";
            } else {
                throw new JsonSyntaxException("Trying to save incompatible types in same variable");
            }
        }else {
            return nextType;
        }
    }

    static void saveReturn(String returnType) throws JsonSyntaxException{

        String ret = returns.get(returns.size()-1);
        try {
            ret = compareVarTypes(ret, returnType);
            returns.set(returns.size()-1, ret);
        }catch(Exception e){
            throw new JsonSyntaxException("Trying to return incompatible return types in same function: " + returnType + " and " + ret);
        }
    }

    public enum PrintState{
        GLOBAL_VARIABLES,FUNCTIONS,MAIN
    }

    public static Types getTypes(){
        return types;
    }
}