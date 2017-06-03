package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.util.ArrayList;

public class CallExpression  extends Expression{
    private MemberExpression callee;
    private ArrayList<Expression> arguments;

    public void print(){
        ParserUt.getInstance().printSpaces();
        if(callee.getObjectName().equals("console") && callee.getPropertyName().equals("log")){
            ParserUt.getInstance().writeToBuffer("System.out.println(");
            for(int i=0;i<arguments.size();i++){
                arguments.get(i).print();
            }
            ParserUt.getInstance().writeToBuffer(");\n");
        }
    }
}
