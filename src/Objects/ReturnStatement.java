package Objects;

import Nodes.Expression;
import Parser.ParserUt;

public class ReturnStatement extends Expression {
    private Expression argument;

    public void print(){
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("return ");
        argument.print();
        ParserUt.getInstance().writeToBuffer(";\n");
    }

}
