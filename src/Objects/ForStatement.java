package Objects;

import Nodes.Expression;

import java.util.ArrayList;

public class ForStatement extends Expression{


    //for(var i=0; i < 10;i++)

    private Expression init;
    private Expression test;
    private Expression update;

    public void print() {
        System.out.print("for(");
        //i=0
        if(init.getClass().equals(AssignmentExpression.class)) {
            init.print();
        }
        //var i=0
        else if(init.getClass().equals(VariableDeclaration.class)){
        }
        System.out.println(";");
        //i < 10
        if(test.getClass().equals(BinaryExpression.class))
            test.print();
        //i++
        if(update.getClass().equals(UpdateExpression.class))
            update.print();

        System.out.println("){");

    }

}
