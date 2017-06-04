package Objects;

import Nodes.Expression;
import Parser.Parser;
import Parser.ParserUt;

public class MemberExpression extends Expression{
    private boolean computed;
    private Expression object;
    private Expression property;

    public void print() {
        object.print();
        if(!computed){
            ParserUt.getInstance().writeToBuffer(".");
            property.print();
        }
//        object.print();
//        ParserUt.getInstance().writeToBuffer(".add(");
//        property.print();
//        ParserUt.getInstance().writeToBuffer(", ");
    }

    public String getObjectName(){
        return object.getName();
    }

    public String getPropertyName(){
        return property.getName();
    }

    public String getVarType() throws Exception{
        String type = object.getVarType();
        int first = type.indexOf('<');
        int last = type.lastIndexOf('>');
        type = type.substring(first+1, last);
        return type;
    }

}
