package Parser;

import Objects.Identifier;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;

public class Types {
    private HashMap<String,String> vars;
    private ArrayList<FunctionsTypes> functions;

    Types(){
        functions = new ArrayList<>();
    }

    public void print(){
        for(int i=0;i<functions.size();i++){
            functions.get(i).print();
        }
    }

    public boolean isCompatible(String functionName, ArrayList<String> params) throws JsonSyntaxException{
        for (int i = 0; i < functions.size(); i++) {
            if(functions.get(i).getName().equals(functionName)){
                ArrayList<String> args = functions.get(i).getArgs();
                ArrayList<String> modified = new ArrayList<>();
                if(params.size() != args.size()){
                    throw new JsonSyntaxException("Can't call functions with different types of parameters");
                }
                for (int j = 0; j < params.size(); j++) {
                    modified.add(Parser.compareVarTypes(args.get(i), params.get(i)));

                }
                functions.get(i).setArgs(modified);

                return true;
            }
        }
        return false;
    }

    public void addFunction(String functionName, ArrayList<String> params) throws JsonSyntaxException {
        if(!isCompatible(functionName, params)){
            FunctionsTypes type = new FunctionsTypes(functionName, params);
            functions.add(type);
        }

    }

    public String getFunctionReturn(String functionName){
        for (int i = 0; i < functions.size(); i++) {
            if(functions.get(i).getName().equals(functionName))
                return functions.get(i).getReturn();
        }
        return "";
    }

    public void setReturn(String functionName, String ret){
        for (int i = 0; i < functions.size(); i++) {
            if(functions.get(i).getName().equals(functionName))
                functions.get(i).setReturn(ret);
        }
    }

    public ArrayList<String> getTypeArgumentFunction(String functionName, int argSize) throws Exception {
        ArrayList<String> tmpArgs=null;
        for(int i=0;i<functions.size();i++){
            FunctionsTypes tmpFt=functions.get(i);
            if(tmpFt.getName().equals(functionName)){
                tmpArgs=tmpFt.getArgs();
                break;
            }
        }

        if(tmpArgs==null){
            return null;
        }

        if(tmpArgs.size() == argSize){
            return tmpArgs;
        }
        else
            throw new Exception("Not declared all the arguments.");
    }
}
