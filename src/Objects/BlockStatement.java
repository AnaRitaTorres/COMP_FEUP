package Objects;

import Nodes.Expression;
import Parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockStatement extends Expression {
    private ArrayList<Expression> body;
    private HashMap<String, String> variables;

    public void print(){
        Parser.variables.add(variables);
        for(int i=0;i<body.size();i++){
            body.get(i).print();
        }
        Parser.variables.remove(Parser.variables.size()-1);
    }

    public void setVariables(HashMap<String, String> vars){
        variables = vars;
    }
}
