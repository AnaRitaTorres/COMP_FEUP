package Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FunctionsTypes {
    private String name;
    private ArrayList<String> args;
    private HashMap<String,String> vars;
    private String ret;

    FunctionsTypes(String name, ArrayList<String> args){
        this.name = name;
        this.args = args;
    }

    public void print(){
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getArgs(){
        return args;
    }

    public void setArgs(ArrayList<String> args){
        this.args = args;
    }

    public void setReturn(String ret) {
        this.ret = ret;
    }

    public String getReturn() {
        return ret;
    }
}
