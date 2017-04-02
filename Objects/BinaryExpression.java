package Objects;
import java.lang.reflect.Type;

public class BinaryExpression extends Expression {
    //aqui não estendi de BasicNode para esta classe poder ser usada como expression
    public BinaryExpression(){};
    public BinaryExpression createInstance(Type type){return new BinaryExpression();};

	private String operator;
    private Expression lhs;
    private Expression rhs;
}
