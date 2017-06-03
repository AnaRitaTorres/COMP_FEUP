package Objects;


import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

public class ExpressionStatement extends Expression {
    private Expression expression;

    public void print(){
        if(ParserUt.getInstance().getInFunction() && ParserUt.getInstance().getPrintState()!= Parser.PrintState.GLOBAL_VARIABLES){
            ParserUt.getInstance().printSpaces();
            expression.print();
        } else {
            ParserUt.getInstance().writeToBuffer("//Expression statement only valid inside functions.\n\n");
        }
    }
}
