package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArrayExpression extends Expression {

    private ArrayList<Literal> elements;

    public ArrayExpression(){};
    public ArrayExpression createInstance(Type type){
        return new ArrayExpression();
    }

    public void print() {

        if(allString() || allNumber()){
            ParserUt.getInstance().writeToBuffer("new type["); //type é para ser substituido por int,String, entre outros
            if(elements.size()>0) {
                int i = 0;
                while (true) {
                    elements.get(i).print();
                    i++;
                    if(i<elements.size()){
                        ParserUt.getInstance().writeToBuffer(",");
                    }
                    else
                        break;
                }
            }
            else
                ParserUt.getInstance().writeToBuffer("0");
            ParserUt.getInstance().writeToBuffer("]");
        }
        else{
            ParserUt.getInstance().writeToBuffer("//The elements of the array must all be of the same type.\n");
            System.out.println("//The elements of the array must all be of the same type.");
        }
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

}


