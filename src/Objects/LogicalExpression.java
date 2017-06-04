package Objects;

import Nodes.Expression;
import Parser.ParserUt;

public class LogicalExpression extends Expression{
    private String operator;
    private Expression left;
    private Expression right;

    public void print(){
        if(left.getType().equals("LogicalExpression")){
            ParserUt.getInstance().writeToBuffer("(");
            left.print();
            ParserUt.getInstance().writeToBuffer(") "+operator+" ");
        } else {
            left.print();
            ParserUt.getInstance().writeToBuffer(" "+operator+" ");
        }

        if(right.getType().equals("LogicalExpression")){
            ParserUt.getInstance().writeToBuffer("(");
            right.print();
            ParserUt.getInstance().writeToBuffer(")");
        }
        else {
            right.print();
        }

    }
}
