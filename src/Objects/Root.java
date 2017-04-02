package Objects;

import java.util.ArrayList;

import Nodes.BasicNode;

public class Root {
    private String type;
    private ArrayList<BasicNode> body;

    public Root(){

    };

    public void print(){
        System.out.println("type: " + type);
        System.out.println("body: " + body);
    };
}
