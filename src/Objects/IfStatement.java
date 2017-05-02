package Objects;

import Nodes.Expression;

public class IfStatement extends Expression {
    private Expression test;
    private Expression consequent;
    private Expression alternate;

    public void print(){
        System.out.print("if(");
        test.print();
        if(consequent.getClass().equals(BlockStatement.class)){
            System.out.println("){");
            consequent.print();
        }
        else{
            System.out.print("}");
        }

    }

}
