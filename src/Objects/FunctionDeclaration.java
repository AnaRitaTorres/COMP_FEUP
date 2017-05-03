package Objects;

import Nodes.Expression;

import java.util.ArrayList;

public class FunctionDeclaration extends Expression{

    private Identifier id;
    private ArrayList<Identifier> params;
    private BlockStatement body;
    private Boolean generator; //ainda nao sei para que serve
    private Boolean expression; //ainda não sei para qe serve

    public void print(){
        System.out.print("public void "); //é preciso verificar o type da função
        id.print();
        System.out.print("(");
        printArgs();
        System.out.println("){");
        body.print();
        System.out.print("}");
    }

    private void printArgs(){
        int i=0;
        while (true) {
            params.get(i).print();
            i++;
            if (i < params.size()) {
                System.out.print(",");
            } else {
                break;
            }
        }
    }
}
