package Objects;

import Nodes.Expression;

public class Id extends Expression{
    String name;

    public void print(){
        System.out.println("name: " + name);
    }
}
