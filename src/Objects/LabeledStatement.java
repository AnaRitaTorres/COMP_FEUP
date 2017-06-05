package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;


public class LabeledStatement extends Expression {

    private Identifier label;
    private Expression body;

    public void print(){
        ParserUt.getInstance().printSpaces();
        label.print();
        ParserUt.getInstance().writeToBuffer(":\n");
        if(body != null)
            body.print();
    }

}
