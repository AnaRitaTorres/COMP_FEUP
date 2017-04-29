package Objects;

import Nodes.Expression;

import java.util.ArrayList;

public class VariableDeclarator extends Expression {

    private Identifier id;
    private Expression init;

    public VariableDeclarator(){};

    public void print(){
        id.print();
        init.print();
    }
}
