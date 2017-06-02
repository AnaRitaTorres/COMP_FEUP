package Objects;


import Nodes.Expression;
import Parser.Parser;
import Utils.ParserUt;

import java.util.ArrayList;

public class SwitchStatement extends Expression{
    private Expression discriminant;
    private ArrayList<SwitchCase> cases;

    public SwitchStatement(){}

    public void print(){
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("switch(");
        discriminant.print();
        ParserUt.getInstance().writeToBuffer("){\n");
        ParserUt.getInstance().addNumSpaces();
        for(int i=0;i<cases.size();i++){
            cases.get(i).print();
        }
        ParserUt.getInstance().subNumSpaces();
        ParserUt.getInstance().writeToBuffer("}\n\n");
    }
}
