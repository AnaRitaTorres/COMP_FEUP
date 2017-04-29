package Objects;


import Nodes.Expression;

public class Identifier extends Expression {
    String name;

    public void print(){
        System.out.println("name: " + name);
    }
}
