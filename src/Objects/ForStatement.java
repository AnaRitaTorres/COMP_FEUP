package Objects;

import Nodes.Expression;
import Utils.ParserUt;

public class ForStatement extends Expression{


    private Expression init;
    private Expression test;
    private Expression update;
    private Expression body;

    public void print() {
        ParserUt.getInstance().writeToBuffer("for(");

        if(init!=null) {
            //i=0
            if (init.getClass().equals(AssignmentExpression.class)) {
                init.print();
            }
            //var i=0
            else if (init.getClass().equals(VariableDeclaration.class)) {
                init.print();
            }
        }
        else
            ParserUt.getInstance().writeToBuffer(";");



        //i < 10
        if(test.getClass().equals(BinaryExpression.class)) {

            test.print();
            ParserUt.getInstance().writeToBuffer(";");
        }
        //i++
        if(update!=null) {
            if (update.getClass().equals(UpdateExpression.class))
                update.print();
        }

        ParserUt.getInstance().writeToBuffer("){\n");

        if(body.getClass().equals(BlockStatement.class)) {
            body.print();
        }
        ParserUt.getInstance().writeToBuffer("\n}\n");

    }

}
