package Objects;

import Nodes.Expression;
import Utils.ParserUt;

import java.lang.reflect.Type;

public class DoWhileStatement extends Expression{

    private Expression test;
    private Expression body;

    public DoWhileStatement(){}
    public DoWhileStatement createInstance(Type type){return new DoWhileStatement();}

    public void print() {
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("do{\n");
        ParserUt.getInstance().addNumSpaces();
        if(body.getClass().equals(BlockStatement.class)){
            body.print();
        }

        ParserUt.getInstance().subNumSpaces();
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("}while(");
        test.print();
        ParserUt.getInstance().writeToBuffer(");\n\n");
    }
}
