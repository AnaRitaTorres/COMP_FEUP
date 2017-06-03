package Objects;


import Nodes.Expression;
import Parser.ParserUt;

public class ExpressionStatement extends Expression {
    private Expression expression;

    public void print(){
        ParserUt.getInstance().printSpaces();
        expression.print();
    }
}
