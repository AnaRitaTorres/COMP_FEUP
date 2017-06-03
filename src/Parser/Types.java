package Parser;

import Objects.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

public class Types {
    private HashMap<String,String> vars;
    private ArrayList<FunctionsTypes> functions;

    public void print(){
        for(int i=0;i<functions.size();i++){
            functions.get(i).print();
        }
    }

    public ArrayList<String> getTypeArgumentFunction(String functionName,ArrayList<Identifier> argument) throws Exception {
        HashMap<String,String> tmpArgs=null;
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

        ArrayList<String> tmpTypeArgs=new ArrayList<>();
        for(int i=0;i<argument.size();i++){
            String tmpArg=argument.get(i).getName();
            if(tmpArgs.containsKey(tmpArg)){
                tmpTypeArgs.add(tmpArgs.get(tmpArg));
            }
        }

        if(tmpTypeArgs.size()==argument.size()){
            return tmpTypeArgs;
        }
        else
            throw new Exception("Not declared all the arguments.");
    }
}
