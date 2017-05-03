package Objects;


import Nodes.Expression;

import java.util.ArrayList;

public class VariableDeclaration extends Expression {
    private String kind;
    private ArrayList<Expression> declarations;

    public VariableDeclaration(){};

    public void print(){
        System.out.print(kind+" "); //aqui depois em vez de kind é a variavel com o tipo (int,String,Boolean,etc)
        for (int i = 0; i < declarations.size(); i++) {
            declarations.get(i).print();
            System.out.println(";");
        }
    }
}
