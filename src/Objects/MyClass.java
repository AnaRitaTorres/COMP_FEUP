package Objects;

import Nodes.BasicNode;
import Nodes.Expression;
import Nodes.TypeReference;

public enum MyClass {
    // You Only need the concrete classes, NOT the abstract ones
    TYPEREFERENCE(TypeReference.class),
    VARIABLEDECLARATION(VariableDeclaration.class),
    BINARYEXPRESSION(BinaryExpression.class),
    LITERAL(Literal.class),
    VARIABLEDECLARATOR(VariableDeclarator.class),
    IDENTIFIER(Identifier.class),
    BLOCKSTATEMENT(BlockStatement.class),
    EXPRESSIONSTATEMENT(ExpressionStatement.class),
    IFSTATEMENT(IfStatement.class),
    FORSTATEMENT(ForStatement.class),
    ASSIGNMENTEXPRESSION(AssignmentExpression.class),
    ARRAYEXPRESSION(ArrayExpression.class),
    WHILESTATEMENT(WhileStatement.class),
    NEWEXPRESSION(NewExpression.class);

    public Class<?> myClass;

    private MyClass(Class<? extends BasicNode> myClass) {
        this.myClass = myClass;
    }
}
