package Objects;

import Nodes.Expression;

public class UpdateExpression {

    private String operator;
    private Literal argument;

    public void print() {
        System.out.println("operator: " + operator);


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