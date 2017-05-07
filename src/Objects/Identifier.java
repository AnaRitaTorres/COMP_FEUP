package Objects;


import Nodes.Expression;
import Utils.ParserUt;

public class Identifier extends Expression {
    private String name;

    public void print(){
        ParserUt.getInstance().writeToBuffer(name);
    }

    public String getName(){
        return name;
    }
}
