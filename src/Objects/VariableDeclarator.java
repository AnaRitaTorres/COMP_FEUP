package Objects;

import Nodes.Expression;

import java.util.ArrayList;

public class VariableDeclarator extends Expression {

    private Expression id;
    private Expression init;

    public VariableDeclarator(){};

    public void print(){
        id.print();
        init.print();
    }
}
