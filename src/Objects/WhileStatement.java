package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

import java.lang.reflect.Type;

public class WhileStatement extends Expression{

    private Expression test;
    private Expression body;

    public void print(){
        ParserUt.getInstance().printSpaces();
        if(ParserUt.getInstance().getInFunction() && ParserUt.getInstance().getPrintState()!= Parser.PrintState.GLOBAL_VARIABLES){
            ParserUt.getInstance().writeToBuffer("while(");

            test.print();
            if(body.getClass().equals(BlockStatement.class)){
                ParserUt.getInstance().writeToBuffer("){\n");
                ParserUt.getInstance().addNumSpaces();
                body.print();
            }

            ParserUt.getInstance().subNumSpaces();
            ParserUt.getInstance().printSpaces();
            ParserUt.getInstance().writeToBuffer("}\n\n");
        } else {
            ParserUt.getInstance().writeToBuffer("//While statement only valid inside functions.\n\n");
        }
    }
}