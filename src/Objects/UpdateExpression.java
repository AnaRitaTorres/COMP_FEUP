package Objects;

import Nodes.Expression;

/**
 * Created by rita on 29-04-2017.
 */
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