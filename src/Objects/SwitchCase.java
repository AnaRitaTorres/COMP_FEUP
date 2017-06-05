package Objects;


import Nodes.Expression;
import Parser.ParserUt;

import java.util.ArrayList;

public class SwitchCase extends Expression{
    private Expression test;
    private ArrayList<Expression> consequent;

    public void print(){
        ParserUt.getInstance().printSpaces();
        if(test==null){
            ParserUt.getInstance().writeToBuffer( "default:\n");
        }
        else{
            ParserUt.getInstance().writeToBuffer("case ");
            test.print();
            ParserUt.getInstance().writeToBuffer(":\n");
        }
        for(int i=0;i<consequent.size();i++){
            consequent.get(i).print();
        }
    }
}
