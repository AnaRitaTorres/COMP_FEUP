package Objects;


import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

import java.util.ArrayList;

public class VariableDeclaration extends Expression {
    private String kind;
    private ArrayList<Expression> declarations;

    public VariableDeclaration(){};

    public void print(){
        defKind();
        ParserUt.getInstance().printSpaces();
        if(ParserUt.getInstance().getPrintState()== Parser.PrintState.GLOBAL_VARIABLES){
            ParserUt.getInstance().writeToBuffer("private "+kind+" ");
        } else if(ParserUt.getInstance().getInFunction()){
            ParserUt.getInstance().writeToBuffer(kind +" ");
        } else {
            ParserUt.getInstance().writeToBuffer("//Variables can only be declared inside functions.\n\n");
            return;
        }

        for (int i = 0; i < declarations.size(); i++) {
            declarations.get(i).print();
            ParserUt.getInstance().writeToBuffer(";\n");
        }
    }

    public void defKind(){
        try{
            kind = Parser.getVarType(declarations.get(0).getId().getName());
        }catch (Exception e){
        }
    }
}
