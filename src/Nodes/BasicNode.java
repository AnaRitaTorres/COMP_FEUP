package Nodes;

import java.util.*;

public abstract class BasicNode{
	private String nodetype;

    public BasicNode(){};
    //Comment é uma classe para representar os comentários, que estenderá também BasicNode (Comment extends BasicNode)
    //TODO: Queremos isto?
    private List<Object> declarations;
}
