package Objects;

import Nodes.BasicNode;

public enum MyClass {
    // You Only need the concrete classes, NOT the abstract ones
    // TYPEREFERENCE(TypeReference.class),
    // LOCALVARIABLEREFERENCE(LocalVariableReference.class),   //TODO nao ta feito
    BINARYEXPRESSION(BinaryExpression.class),
    LITERAL(Literal.class);
    // VARIABLEREAD(VariableRead.class);   //TODO nao ta feito
    // // Define the remaining BasicNodetype and corresponding classes

    public Class<?> myClass;

    private MyClass(Class<? extends BasicNode> myClass) {
        this.myClass = myClass;
    }
}
