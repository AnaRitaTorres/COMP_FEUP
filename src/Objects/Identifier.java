package Objects;


import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

public class Identifier extends Expression {
    private String name;

    public void print(){
        ParserUt.getInstance().writeToBuffer(name);
    }

    public String getName(){
        return name;
    }

    public String getVarType() throws Exception{
        return Parser.getVarType(name);
    }
}
