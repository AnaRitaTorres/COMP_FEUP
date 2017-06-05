package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.util.ArrayList;

public class ArrayExpression extends Expression {

    private ArrayList<Expression> elements;

    private ArrayExpression(){}

    public void print() {


    }

    public void print(String type){
        ParserUt.getInstance().writeToBuffer("new ArrayList<>(");

        if(elements.size()>0) {
            ParserUt.getInstance().writeToBuffer("Arrays.asList(");
            for (int i = 0; i < elements.size(); i++) {

                elements.get(i).print(type);
                if(i<elements.size() -1){
                    ParserUt.getInstance().writeToBuffer(",");
                }
            }
            ParserUt.getInstance().writeToBuffer(")");
        }
        ParserUt.getInstance().writeToBuffer(")");
    }
}


