package Objects;

import Nodes.Expression;

public class UpdateExpression extends Expression{

    private String operator;
    private Identifier argument;
    private Boolean prefix;

    public void print() {
        if(prefix){
            System.out.print(operator);
            argument.print();
        }
        else
        {
            argument.print();
            System.out.print(operator);
        }
        System.out.println(";");
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