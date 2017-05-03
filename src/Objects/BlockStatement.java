package Objects;

import Nodes.Expression;

import java.util.ArrayList;

public class BlockStatement extends Expression {
    ArrayList<Expression> body;

    public void print(){
        for(int i=0;i<body.size();i++){
            body.get(i).print();
        }
    }
}
