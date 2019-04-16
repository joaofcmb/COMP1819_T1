package semantic;

import parser.Node;

public class MethodTable extends FunctionTable {
    private final Type returnType;

    MethodTable(Node methodNode, SymbolTable classAttributes) throws SemanticException {
        super(methodNode, classAttributes);
        this.returnType = new Type(methodNode.jjtGetChild(0));

        fillParameters(methodNode.jjtGetChild(2));
        fillVariables(methodNode.jjtGetChild(3));
    }

    @Override
    public String toString() {
        return "- RETURN: " + returnType + System.lineSeparator()
                + System.lineSeparator()
                + super.toString();
    }
}
