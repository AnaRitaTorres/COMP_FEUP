package Objects;

import Nodes.Expression;

import java.util.ArrayList;

/**
 * Created by iamgroot on 03/04/17.
 */
public class VariableDeclarator extends Expression {

    private Id id;
    private Expression init;

    public VariableDeclarator(){};

    public void print(){
        id.print();
        init.print();
    }
}
