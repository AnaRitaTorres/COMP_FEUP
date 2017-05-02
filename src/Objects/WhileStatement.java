package Objects;

import Nodes.Expression;

import java.lang.reflect.Type;

public class WhileStatement extends Expression{

    private Expression test;
    private Expression body;

    public WhileStatement(){};
    public WhileStatement createInstance(Type type){return new WhileStatement();}

    public void print(){
        System.out.print("while(");

        test.print();
        if(body.getClass().equals(BlockStatement.class)){
            System.out.print("){\n");
            body.print();
        }
        else
            System.out.println("}");
    }
}