package Objects;
import Nodes.Expression;
import Parser.ParserUt;

public class BinaryExpression extends Expression {

    private String operator;
    private Expression left;
    private Expression right;

    public BinaryExpression(){}

    public void print() {
        String type="";

        if(right.getType().equals(("ArrayExpression"))){
            try {
                type = left.getVarType();

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        if(left.getType().equals("MemberExpression")){
            left.print(operator);
            right.print(type);
            ParserUt.getInstance().writeToBuffer(")");
        }else {
            if(left.getType().equals("BinaryExpression")){
                ParserUt.getInstance().writeToBuffer("(");
                left.print();
                ParserUt.getInstance().writeToBuffer(") " + operator + " ");
            } else{
                left.print();
                ParserUt.getInstance().writeToBuffer(" " + operator + " ");
            }

            if(right.getType().equals("BinaryExpression")){
                ParserUt.getInstance().writeToBuffer("(");
                right.print();
                ParserUt.getInstance().writeToBuffer(")");
            }
            else {
                right.print();
            }
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
