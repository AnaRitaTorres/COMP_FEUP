package Objects;

import Nodes.Expression;
import Utils.ParserUt;

public class IfStatement extends Expression {
    private Expression test;
    private Expression consequent;
    private Expression alternate;

    public void print(){
        ParserUt.getInstance().writeToBuffer("if(");
        test.print();
        if(consequent.getClass().equals(BlockStatement.class)){
            ParserUt.getInstance().writeToBuffer("){\n");
            consequent.print();
        }

        ParserUt.getInstance().writeToBuffer("}\n");
    }

}
