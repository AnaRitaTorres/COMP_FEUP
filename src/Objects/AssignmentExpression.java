package Objects;

import Nodes.Expression;
import Utils.ParserUt;


public class AssignmentExpression extends BinaryExpression {
    private String varType;

    public void print(){
        super.print();
        ParserUt.getInstance().writeToBuffer(";\n");
    }
}
