package Objects;

import Nodes.Expression;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockStatement extends Expression {
    ArrayList<Expression> body;
    HashMap<String, String> variables;

    public void print(){
        for(int i=0;i<body.size();i++){
            body.get(i).print();
        }
    }
}
