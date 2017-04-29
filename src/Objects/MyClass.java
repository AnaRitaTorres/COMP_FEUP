package Objects;

import Nodes.BasicNode;
import Nodes.TypeReference;

public enum MyClass {
    // You Only need the concrete classes, NOT the abstract ones
    TYPEREFERENCE(TypeReference.class),
    VARIABLEDECLARATION(VariableDeclaration.class),
    BINARYEXPRESSION(BinaryExpression.class),
    LITERAL(Literal.class),
    VARIABLEDECLARATOR(VariableDeclarator.class),
    ARRAYEXPRESSION(ArrayExpression.class),
    ID(Id.class);

    public Class<?> myClass;

    private MyClass(Class<? extends BasicNode> myClass) {
        this.myClass = myClass;
    }
}
