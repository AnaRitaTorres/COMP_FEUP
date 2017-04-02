import java.util.*;
import Objects.*;

public class Main {
    private String type;
    private ArrayList<BasicNode> body = new ArrayList<>();

    public Main(){

    };

    public void print(){
        System.out.println("type: " + type);
        System.out.println("body: " + body.get(0).getClass().getName());
    };
}
