package Objects;

import java.util.ArrayList;

import Nodes.BasicNode;

public class Root {
    private String type;
    private ArrayList<BasicNode> body;

    public Root(){

    }

    public void print(){
        System.out.println("type: " + type);
        System.out.println("body: " + body);

        for (int i = 0; i < body.size(); i++) {
            body.get(i).print();
        }
    }

    public String toString(){
        String code = "public void main(String[] args){\n\n";
        for (BasicNode node:body) {
            code += body.toString();
        }
        code += "\n}";
        return code;
    }
}
