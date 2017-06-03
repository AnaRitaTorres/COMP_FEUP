package Objects;


import Nodes.Expression;
import Parser.ParserUt;

public class BreakStatement extends Expression{
    private String label; //não sei se é String pelo que vi é sempre null

    public BreakStatement(){}
    public void print(){
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("break;\n");
    }
}
