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
            ParserUt.getInstance().writeToBuffer("private "+kind+" "); //aqui depois em vez de kind é a variavel com o tipo (int,String,Boolean,etc)
        } else {
            ParserUt.getInstance().writeToBuffer(kind+" "); //aqui depois em vez de kind é a variavel com o tipo (int,String,Boolean,etc)
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
