package Nodes;

public abstract class Expression extends BasicNode {
    //Como todas as expressões vão ter um tipo associado, deixei aqui o type,
    //que pode ser acedido p.e. pela classe Literal
    protected TypeReference nodetype;
    public Object value;

    public Expression getId(){return new Expression() {
    };};
    public String getName(){return "";};
}
