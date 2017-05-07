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
        ParserUt.getInstance().writeToBuffer("while(");

        test.print();
        if(body.getClass().equals(BlockStatement.class)){
            ParserUt.getInstance().writeToBuffer("){\n");
            body.print();
        }

        ParserUt.getInstance().writeToBuffer("}\n");
    }
}