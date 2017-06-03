package Parser;

import java.util.HashMap;
import java.util.Iterator;

public class FunctionsTypes {
    private String name;
    private HashMap<String,String> args;
    private HashMap<String,String> vars;

    public void print(){
        System.out.println("Name: "+name);

        Iterator it=args.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            System.out.println(pair.getKey()+": "+pair.getValue());
            it.remove();
        }
    }
}
