package Objects;

import Nodes.Expression;
import Nodes.TypeReference;
import Parser.ParserUt;

public class ReturnStatement extends Expression {
    private Expression argument;
    private String argType;

    public void setArgType(String at){
        argType = at;
        ParserUt.getInstance().writeToBuffer("wrote arg type ");
        ParserUt.getInstance().writeToBuffer(argType);
    }

    public void print(){
        ParserUt.getInstance().printSpaces();
        ParserUt.getInstance().writeToBuffer("return ");
        argument.print();
        ParserUt.getInstance().writeToBuffer(";\n");
    }

    public void printType(){
        TypeReference argType = argument.getNodetype();
        ParserUt.getInstance().writeToBuffer(argType.toString());
    }

    public String getArgumentType(){
        return argType;
    }

}
