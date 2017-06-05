package Objects;


import Nodes.Expression;
import Parser.ParserUt;

public class BreakStatement extends Expression{
    private Identifier label=null; //não sei se é String pelo que vi é sempre null


    public void print(){
        if(label==null){
            ParserUt.getInstance().printSpaces();
            ParserUt.getInstance().writeToBuffer("break;\n");
        }
        else{
            ParserUt.getInstance().printSpaces();
            ParserUt.getInstance().writeToBuffer("break ");
            label.print();
            ParserUt.getInstance().writeToBuffer(";\n");
        }


    }
}
