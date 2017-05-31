package Objects;

import java.util.ArrayList;
import java.util.HashMap;

import Nodes.BasicNode;
import Parser.Parser;

public class Root {
    private String type;
    private ArrayList<BasicNode> body;
    private HashMap<String, String> variables;

    public Root(){

    }

    public void print(){
        Parser.variables.add(variables);
        System.out.println("type: " + type);
        System.out.println("body: " + body);

        for (int i = 0; i < body.size(); i++) {
            body.get(i).print();
        }
        Parser.variables.remove(Parser.variables.size()-1);
    }

    public String toString(){
        String code = "public void main(String[] args){\n\n";
        for (BasicNode node:body) {
            code += body.toString();
        }
        code += "\n}";
        return code;
    }

    public void setVariables(HashMap<String, String> vars){
        variables = vars;
    }
}
