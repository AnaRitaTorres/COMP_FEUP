package Objects;


import Nodes.Expression;

import java.util.ArrayList;

/**
 * Created by iamgroot on 03/04/17.
 */
public class VariableDeclaration extends Expression {
    private String kind;
    private ArrayList<Expression> declarations;

    public VariableDeclaration(){};

    public void print(){
        for (int i = 0; i < declarations.size(); i++) {
            declarations.get(i).print();
        }
    }
}
