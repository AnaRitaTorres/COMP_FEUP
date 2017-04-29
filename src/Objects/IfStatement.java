package Objects;

import Nodes.Expression;

public class IfStatement extends Expression {
    private Expression test;
    private Expression consequent;
    private Expression alternate;

    public void print(){
        System.out.println("if(");
        test.print();
        if(consequent.getClass().equals(BlockStatement.class))
            System.out.println("){");
        else{
            System.out.println(")");
        }

    }

}
