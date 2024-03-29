package semantic;

import parser.Node;

/**
 * Class containing all the relevant information of a method
 *
 * @see FunctionTable
 */
public class MethodTable extends FunctionTable {

    /**
     * Creates a Method Table, initializing its Base Attributes and filling its Symbol Tables
     *
     * @param methodNode AST Root containing the method
     * @param ir IR of the class this method belongs to
     *
     * @param optimize
     * @throws SemanticException on Semantic Error (Conflicting Symbols)
     */
    MethodTable(Node methodNode, IntermediateRepresentation ir, boolean optimize) throws SemanticException {
        super(methodNode.jjtGetChild(3), ir, new Type(methodNode.jjtGetChild(0)), optimize);

        final Node parameterNode = methodNode.jjtGetChild(2);
        for (int i = 0; i < parameterNode.jjtGetNumChildren(); i+=2) {
            Node parameterId = parameterNode.jjtGetChild(i+1);

            if (ir.getAttributes().containsId(parameterId))
                throw new SemanticException(parameterId,
                        "Invalid parameter Identifier (Conflicting with class attributes)");

            getParameters().addParameter(parameterNode.jjtGetChild(i), parameterId);
        }
    }

    @Override
    public String toString() {
        return "  RETURN: " + getReturnType() + System.lineSeparator()
                + System.lineSeparator()
                + super.toString();
    }
}
