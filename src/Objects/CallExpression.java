package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.util.ArrayList;

public class CallExpression  extends Expression{
    private Expression callee;
    private ArrayList<Expression> arguments;

    public void print(){
        String obj = callee.getType();
        if(obj.equals("MemberExpression")){
            if(callee.getObjectName().equals("console") && callee.getPropertyName().equals("log")){
                ParserUt.getInstance().writeToBuffer("System.out.println(");
                for(int i=0;i<arguments.size();i++){
                    arguments.get(i).print();
                }
                ParserUt.getInstance().writeToBuffer(")");
                return;
            }
        }
        callee.print("");
        printArgs();

    }

    public void print(String type){
        print();
        ParserUt.getInstance().writeToBuffer(";\n");

    }

    private void printArgs(){
        ParserUt.getInstance().writeToBuffer("(");
        for (int i = 0; i < arguments.size(); i++) {
            arguments.get(i).print();
            if(i != arguments.size()-1)
                ParserUt.getInstance().writeToBuffer(", ");
        }
        ParserUt.getInstance().writeToBuffer(")");

    }
}
