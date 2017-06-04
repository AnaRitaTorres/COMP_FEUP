package Nodes;

public abstract class BasicNode{
	private String type;

    public BasicNode(){};
    //Comment é uma classe para representar os comentários, que estenderá também BasicNode (Comment extends BasicNode)
    //TODO: Queremos isto?

    public void print(){

    }

    public void print(String type){print();}

    public String getType() {
        return type;
    }
}
