package Objects;

import Nodes.Expression;
import Parser.Parser;
import Utils.ParserUt;

import java.util.ArrayList;

public class VariableDeclarator extends Expression {

    private Expression id;
    private Expression init;

    public VariableDeclarator(){};

    public void print(){

        id.print();
        ParserUt.getInstance().writeToBuffer("=");
        init.print();
    }

    public Expression getId(){
        return id;
    }
}
