package Objects;

import Nodes.Expression;
import Parser.ParserUt;

public class ForStatement extends Expression{


    private Expression init;
    private Expression test;
    private Expression update;
    private Expression body;

    public void print() {
        ParserUt.getInstance().writeToBuffer("for(");

        if(init!=null) { //i=0
            if (init.getClass().equals(AssignmentExpression.class) || (init.getClass().equals(VariableDeclaration.class))){
                init.print();
                ParserUt.getInstance().eraseCharactersFromBuffer(1);
            }
        }
        else
            ParserUt.getInstance().writeToBuffer(";");

        if(test.getClass().equals(BinaryExpression.class)) { //i<10

            test.print();
            ParserUt.getInstance().writeToBuffer(";");
        }

        if(update!=null) { //i++
            if (update.getClass().equals(UpdateExpression.class))
            {
                update.print();
                ParserUt.getInstance().eraseCharactersFromBuffer(2);
            }
        }

        ParserUt.getInstance().writeToBuffer("){\n");
        ParserUt.getInstance().addNumSpaces();
        if(body.getClass().equals(BlockStatement.class)) {
            body.print();
        }
        ParserUt.getInstance().subNumSpaces();
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("}\n\n");

    }

}
