package Objects;

import Nodes.Expression;

import java.util.ArrayList;

/**
 * Created by iamgroot on 03/04/17.
 */
public class VariableDeclarator extends Expression {

    private Id id;

    public VariableDeclarator(){};

    public void print(){
        System.out.println("id: " + id);
        id.print();
    }
}
