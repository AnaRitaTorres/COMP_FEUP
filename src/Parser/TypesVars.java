package Parser;

import java.util.ArrayList;
import java.util.HashMap;

public class TypesVars {
    private HashMap<String,String> vars;
    private ArrayList<FunctionsTypes> functions;

    public void print(){
        for(int i=0;i<functions.size();i++){
            functions.get(i).print();
        }
    }
}
