package Parser;

import java.util.HashMap;
import java.util.Iterator;

public class FunctionsTypes {
    private String name;
    private HashMap<String,String> args;
    private HashMap<String,String> vars;

    public void print(){
    }

    public String getName(){
        return name;
    }

    public HashMap<String,String> getArgs(){
        return args;
    }
}
