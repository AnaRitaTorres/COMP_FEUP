package Objects;

import Nodes.Expression;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by rita on 29-04-2017.
 */
public class ArrayExpression extends Expression {

    public ArrayExpression(){};
    public ArrayExpression createInstance(Type type){
        return new ArrayExpression();
    };

    private ArrayList<Literal> elements;

    public void print() {

        if(allString() || allNumber()) {
            for (int i = 0; i < elements.size(); i++) {
                System.out.println("element:" + elements.get(i).value);
            }
        }
        else
            System.out.println("The elements of the array must all be of the same type");
    }

    public boolean allString(){
        int n=0;
        for(int i=0; i < elements.size(); i++){
           if (elements.get(i).value.getClass().equals(String.class)){
               n++;
           }
        }

        if(n == elements.size())
            return true;
        else return false;
    }

    public boolean allNumber(){
        int n=0;
        for(int i=0; i < elements.size(); i++){
            if (elements.get(i).value.getClass().equals(Double.class)){
                n++;
            }
        }

        if(n == elements.size())
            return true;
        else return false;
    }

}


