package Objects;

import Nodes.Expression;
import Parser.ParserUt;

import java.util.ArrayList;

public class FunctionDeclaration extends Expression{

    private Identifier id;
    private ArrayList<Identifier> params;
    private BlockStatement body;
    private Boolean generator; //ainda nao sei para que serve
    private Boolean expression; //ainda não sei para qe serve

    public void print(){
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("public void "); //é preciso verificar o type da função
        id.print();
        ParserUt.getInstance().writeToBuffer("(");
        printArgs();
        ParserUt.getInstance().writeToBuffer("){\n");
        ParserUt.getInstance().addNumSpaces();
        body.print();
        ParserUt.getInstance().subNumSpaces();
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("}\n\n");
    }

    private void printArgs(){
        int i=0;
        while (true) {
            params.get(i).print();
            i++;
            if (i < params.size()) {
                ParserUt.getInstance().writeToBuffer(",");
            } else {
                break;
            }
        }
    }
}
