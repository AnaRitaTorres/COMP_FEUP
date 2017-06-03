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
        ParserUt.getInstance().writeToBuffer("switch(");
        discriminant.print();
        ParserUt.getInstance().writeToBuffer("){\n");
        for(int i=0;i<cases.size();i++){
            cases.get(i).print();
        }
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("}\n\n");
    }
}
