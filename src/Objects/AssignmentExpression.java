package Objects;

import Nodes.Expression;


public class AssignmentExpression extends BinaryExpression {
    private String varType;

    public void print(){
        super.print();
        System.out.println(";");
    }
}
