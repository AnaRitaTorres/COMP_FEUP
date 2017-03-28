public abstract class BinaryExpression extends Expression { 
    //aqui não estendi de BasicNode para esta classe poder ser usada como expression
	private String operator;
    private Expression lhs;
    private Expression rhs;
}
