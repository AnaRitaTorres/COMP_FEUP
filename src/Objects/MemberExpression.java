package Objects;

import Nodes.Expression;
import Parser.Parser;
import Utils.ParserUt;

import java.util.ArrayList;

public class MemberExpression extends Expression{
    private boolean computed;
    private Identifier object;
    private Identifier property;

    public void print() {}

    public String getObjectName(){
        return object.getName();
    }

    public String getPropertyName(){
        return property.getName();
    }

}
