package Objects;
import Nodes.Expression;

import java.lang.reflect.Type;

public class BinaryExpression extends Expression {
    //aqui não estendi de BasicNode para esta classe poder ser usada como expression
    private String operator;
    private Expression left;
    private Expression right;

    public BinaryExpression(){};
    public BinaryExpression createInstance(Type type){return new BinaryExpression();};

    public void print() {
        System.out.print(left+operator+right);
        /*System.out.println("operator: " + operator);
        System.out.println(getValue());*/
    }

    @Override
    public String toString(){
        String code;
        code = left + " " + operator + " " + right;
        return code;
    }

    public Object getValue(){
        Object ret = null, r, l;
        if(right instanceof BinaryExpression){
             r = ((BinaryExpression) right).getValue();
        }else{
            r = right.value;
        }
        if(left instanceof BinaryExpression){
            l = ((BinaryExpression) left).getValue();
        }else{
            l = left.value;
        }

        switch(operator){
            case "*":
                if(l instanceof Double && r instanceof Double){
                    ret = ((Double)r * (Double)l);
                }
                break;
            case "/":
                if(l instanceof Double && r instanceof Double){
                    ret = ((Double)l / (Double)r);
                }
                break;
            case "-":
                if(l instanceof Double && r instanceof Double){
                    ret = ((Double)l - (Double)r);
                }
                break;
            case "+":
                if(l instanceof Double && r instanceof Double) {
                    ret = ((Double)l + (Double)r);
                } else if(l instanceof String || r instanceof String){
                    ret = String.valueOf(l) + String.valueOf(r);
                }
                break;
            case "<":
                if(l instanceof Double && r instanceof Double){
                    ret=((Double)l<(Double)r);
                }
                break;
            case ">":
                if(l instanceof Double && r instanceof Double){
                    ret=((Double)l<(Double)r);
                }
                break;
        }
        return ret;
    }
}
