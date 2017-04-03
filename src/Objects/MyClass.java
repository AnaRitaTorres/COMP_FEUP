package Objects;

import Nodes.BasicNode;
import Nodes.TypeReference;

public enum MyClass {
    // You Only need the concrete classes, NOT the abstract ones
    TYPE_REFERENCE(TypeReference.class),
    VARIABLE_DECLARATION(VariableDeclaration.class),
    BINARY_EXPRESSION(BinaryExpression.class),
    LITERAL(Literal.class),
    VARIABLE_DECLARATOR(VariableDeclarator.class),
    IDENTIFIER(Identifier.class);

    public Class<?> myClass;

    private MyClass(Class<? extends BasicNode> myClass) {
        this.myClass = myClass;
    }
}
