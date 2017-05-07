package Objects;

import Nodes.Expression;
import Utils.ParserUt;

public class UpdateExpression extends Expression{

    private String operator;
    private Identifier argument;
    private Boolean prefix;

    public void print() {
        if(prefix){
            ParserUt.getInstance().writeToBuffer(operator);
            argument.print();
        }
        else
        {
            argument.print();
            ParserUt.getInstance().writeToBuffer(operator);
        }
        ParserUt.getInstance().writeToBuffer(";\n");
    }

 }


 /*
 *
 * {
 *  "a": {type="int" isFunction=false},
 *  "print: {type="void", isFunction=true, args: {}, vars:{}}
 * }
 *
 *
 * var a = new A;
 * */