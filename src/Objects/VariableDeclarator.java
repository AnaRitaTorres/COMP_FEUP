package Objects;

import Nodes.Expression;
import Parser.ParserUt;

public class VariableDeclarator extends Expression {
    private Expression id;
    private Expression init;

    public VariableDeclarator(){};

    public void print(){

        id.print();

        if(init != null) {
            ParserUt.getInstance().writeToBuffer(" = ");
            init.print();
        }
    }

    public Expression getId(){
        return id;
    }
}
