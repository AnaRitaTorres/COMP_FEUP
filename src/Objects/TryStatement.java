package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

import java.util.ArrayList;

public class TryStatement extends Expression{
    private BlockStatement block;
    private ArrayList<String> guardedHandlers;
    private ArrayList<CatchClause> handlers;
    private BlockStatement finalizer;

    public void print(){
        ParserUt.getInstance().printSpaces();
        if(ParserUt.getInstance().getInFunction() && ParserUt.getInstance().getPrintState()!= Parser.PrintState.GLOBAL_VARIABLES){
            ParserUt.getInstance().writeToBuffer("try {\n");
            ParserUt.getInstance().addNumSpaces();
            block.print();
            ParserUt.getInstance().subNumSpaces();
            ParserUt.getInstance().printSpaces();
            ParserUt.getInstance().writeToBuffer("}");
            for(int i=0;i<handlers.size();i++){
                handlers.get(i).print();
            }
            if(finalizer!=null){
                ParserUt.getInstance().writeToBuffer("finally{\n");
                ParserUt.getInstance().addNumSpaces();
                finalizer.print();
                ParserUt.getInstance().subNumSpaces();
                ParserUt.getInstance().printSpaces();
                ParserUt.getInstance().writeToBuffer("}\n\n");
            }
        } else {
            ParserUt.getInstance().writeToBuffer("/Try statement can only be used inside functions.\n\n");
        }

    }
}
