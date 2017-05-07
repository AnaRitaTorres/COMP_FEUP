package Objects;

import Nodes.Expression;
import Utils.ParserUt;

import java.util.ArrayList;

public class ForStatement extends Expression{


    //for(var i=0; i < 10;i++)

    private Expression init;
    private Expression test;
    private Expression update;

    public void print() {
        ParserUt.getInstance().writeToBuffer("for(");
        //i=0
        if(init.getClass().equals(AssignmentExpression.class)) {
            init.print();
        }
        //var i=0
        else if(init.getClass().equals(VariableDeclaration.class)){
        }
        ParserUt.getInstance().writeToBuffer(";\n");
        //i < 10
        if(test.getClass().equals(BinaryExpression.class))
            test.print();
        //i++
        if(update.getClass().equals(UpdateExpression.class))
            update.print();

        ParserUt.getInstance().writeToBuffer("){\n");

    }

}
