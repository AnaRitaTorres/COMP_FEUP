package Objects;
import Nodes.Expression;
import Parser.ParserUt;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryExpression extends Expression {

    private String operator;
    private Expression left;
    private Expression right;

    public BinaryExpression(){}
    public BinaryExpression createInstance(Type type){return new BinaryExpression();}

    public void print() {
        if(left.getType().equals("MemberExpression")){
            //TODO a[i] = 4; -> a.add(i, 4);
            //     a[i] += 3 -> a.add(i, a.get(i) + 4);
            System.out.println("lol");
        }else {
            left.print();
            ParserUt.getInstance().writeToBuffer(" " + operator + " ");
            right.print();
        }
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
