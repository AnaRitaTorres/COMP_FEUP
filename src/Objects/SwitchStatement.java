package Objects;


import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

import java.util.ArrayList;

public class SwitchStatement extends Expression{
    private Expression discriminant;
    private ArrayList<SwitchCase> cases;

    public SwitchStatement(){}

    public void print(){
        ParserUt.getInstance().printSpaces();
        if(ParserUt.getInstance().getInFunction() && ParserUt.getInstance().getPrintState()!= Parser.PrintState.GLOBAL_VARIABLES){
            ParserUt.getInstance().writeToBuffer("switch(");
            discriminant.print();
            ParserUt.getInstance().writeToBuffer("){\n");
            ParserUt.getInstance().addNumSpaces();
            for(int i=0;i<cases.size();i++){
                cases.get(i).print();
            }
            ParserUt.getInstance().subNumSpaces();
            ParserUt.getInstance().printSpaces();
            ParserUt.getInstance().writeToBuffer("}\n\n");
        } else {
            ParserUt.getInstance().writeToBuffer("//Switch statement only valid inside functions.\n\n");
        }
    }
}
