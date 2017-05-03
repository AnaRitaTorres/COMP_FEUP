package Objects;


import Nodes.Expression;

public class ExpressionStatement extends Expression {
    private Expression expression;

    public void print(){
        expression.print();
    }
}
