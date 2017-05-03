package Objects;


import Nodes.Expression;

public class Identifier extends Expression {
    private String name;

    public void print(){
        System.out.print(name);
    }

    public String getName(){
        return name;
    }
}
