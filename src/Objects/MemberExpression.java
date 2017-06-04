package Objects;

import Nodes.Expression;

public class MemberExpression extends Expression{
    private boolean computed;
    private Expression object;
    private Expression property;

    public void print() {}

    public String getObjectName(){
        return object.getName();
    }

    public String getPropertyName(){
        return property.getName();
    }

}
