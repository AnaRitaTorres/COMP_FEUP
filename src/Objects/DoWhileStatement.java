package Objects;

import Nodes.Expression;
import Utils.ParserUt;

import java.lang.reflect.Type;

/**
 * Created by rita on 31-05-2017.
 */
public class DoWhileStatement extends Expression{

    private Expression test;
    private Expression body;

    public DoWhileStatement(){}
    public DoWhileStatement createInstance(Type type){return new DoWhileStatement();}

    public void print() {
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("do{");

        if(body.getClass().equals(BlockStatement.class)){
            ParserUt.getInstance().writeToBuffer("\n");
            body.print();
            ParserUt.getInstance().writeToBuffer("\n");
        }

        ParserUt.getInstance().subNumSpaces();
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("}while(");
        test.print();
        ParserUt.getInstance().writeToBuffer(");\n");
    }
}
