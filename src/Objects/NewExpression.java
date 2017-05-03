package Objects;


import Nodes.Expression;

import java.util.ArrayList;

public class NewExpression extends Expression{
    private Identifier callee;
    private ArrayList<Literal> arguments;

    public void print(){
        if(callee.getName().equals("Array")){
            if(allString() || allNumber()){
                System.out.print("new type["); //type é para ser substituido por int,String, entre outros
                if(arguments.size()>0) {
                    printArgs();
                }
                else
                    System.out.print("0");
                System.out.print("]");
            }
            else
                System.out.println("//The elements of the array must all be of the same type.");
        }
        else{
            System.out.print("new ");
            callee.print();
            System.out.print("(");
            printArgs();
            System.out.print(")");
        }
    }

    private void printArgs(){
        int i=0;
        while (true) {
            arguments.get(i).print();
            i++;
            if (i >= arguments.size()) {
                break;
            } else {
                System.out.print(",");
            }
        }
    }

    private boolean allString(){
        int n=0;
        for(int i=0; i < arguments.size(); i++){
            if (arguments.get(i).value.getClass().equals(String.class)){
                n++;
            }
        }

        return n == arguments.size();
    }

    private boolean allNumber(){
        int n=0;
        for(int i=0; i < arguments.size(); i++){
            if (arguments.get(i).value.getClass().equals(Double.class)){
                n++;
            }
        }

        return n == arguments.size();
    }

}
