package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

public class UpdateExpression extends Expression{

    private String operator;
    private Identifier argument;
    private Boolean prefix;

    public void print() {
        if(ParserUt.getInstance().getInFunction() && ParserUt.getInstance().getPrintState()!= Parser.PrintState.GLOBAL_VARIABLES){
            if(prefix){
                ParserUt.getInstance().writeToBuffer(operator);
                argument.print();
            }
            else{
                argument.print();
                ParserUt.getInstance().writeToBuffer(operator);
            }
            ParserUt.getInstance().writeToBuffer(";\n");
        } else {
            ParserUt.getInstance().writeToBuffer("//Update expression only valid inside functions.\n\n");
        }
    }

 }