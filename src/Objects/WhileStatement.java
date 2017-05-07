package Objects;

import Nodes.Expression;
import Utils.ParserUt;

import java.lang.reflect.Type;

public class WhileStatement extends Expression{

    private Expression test;
    private Expression body;

    public WhileStatement(){};
    public WhileStatement createInstance(Type type){return new WhileStatement();}

    public void print(){
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("while(");

        test.print();
        if(body.getClass().equals(BlockStatement.class)){
            ParserUt.getInstance().writeToBuffer("){\n");
            ParserUt.getInstance().addNumSpaces();
            body.print();
        }

        ParserUt.getInstance().subNumSpaces();
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("}\n");
    }
}