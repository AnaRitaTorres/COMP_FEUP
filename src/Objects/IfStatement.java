package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

public class IfStatement extends Expression {
    private Expression test;
    private Expression consequent;
    private Expression alternate;

    public void print(){
        ParserUt.getInstance().printSpaces();
        if(ParserUt.getInstance().getInFunction() && ParserUt.getInstance().getPrintState()!= Parser.PrintState.GLOBAL_VARIABLES){
            ParserUt.getInstance().writeToBuffer("if(");
            test.print();
            if(consequent.getClass().equals(BlockStatement.class)){
                ParserUt.getInstance().writeToBuffer("){\n");
                ParserUt.getInstance().addNumSpaces();
                consequent.print();
            }

            ParserUt.getInstance().subNumSpaces();
            ParserUt.getInstance().printSpaces();
            ParserUt.getInstance().writeToBuffer("}\n\n");
        } else {
            ParserUt.getInstance().writeToBuffer("//If statement only valid inside functions.\n\n");
        }
    }

}
