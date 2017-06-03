package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayExpression extends Expression {

    private ArrayList<Expression> elements;

    public ArrayExpression(){};
    public ArrayExpression createInstance(Type type){
        return new ArrayExpression();
    }

    public void print() {


    }

    private boolean allString(){
        int n=0;
        for(int i=0; i < elements.size(); i++){
           if (elements.get(i).value.getClass().equals(String.class)){
               n++;
           }
        }

        return n == elements.size();
    }

    private boolean allNumber(){
        int n=0;
        for(int i=0; i < elements.size(); i++){
            if (elements.get(i).value.getClass().equals(Double.class)){
                n++;
            }
        }

        return n == elements.size();
    }

    public void print(String type){
        //if(allString() || allNumber()){//TODO switch this line with something else
        ParserUt.getInstance().writeToBuffer("new ArrayList<>(Arrays.asList(");
        if(elements.size()>0) {
            for (int i = 0; i < elements.size(); i++) {

                elements.get(i).print(type);
                if(i<elements.size() -1){
                    ParserUt.getInstance().writeToBuffer(",");
                }
                else
                    break;
            }
        }
        else
            ParserUt.getInstance().writeToBuffer("0");
        ParserUt.getInstance().writeToBuffer("))");
        //}
//        else{
//            ParserUt.getInstance().writeToBuffer("//The elements of the array must all be of the same type.\n");
//            System.out.println("//The elements of the array must all be of the same type.");
//        }
    }
}


