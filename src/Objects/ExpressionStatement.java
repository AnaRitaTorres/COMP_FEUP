package Objects;


import Nodes.Expression;
import Utils.ParserUt;

public class ExpressionStatement extends Expression {
    private Expression expression;

    public void print(){
        ParserUt.getInstance().printSpaces();
        expression.print();
    }
}
