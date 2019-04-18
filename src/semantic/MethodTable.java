package semantic;

import parser.Node;

public class MethodTable extends FunctionTable {
    MethodTable(Node methodNode, IntermediateRepresentation ir) throws SemanticException {
        super(methodNode.jjtGetChild(3), ir, new Type(methodNode.jjtGetChild(0)));

        final Node parameterNode = methodNode.jjtGetChild(2);
        for (int i = 0; i < parameterNode.jjtGetNumChildren(); i+=2) {
            Node parameterId = parameterNode.jjtGetChild(i+1);

            //TODO Complete Semantic Error (Id already exists within scope)
            if (ir.getAttributes().containsId(parameterId))    throw new SemanticException();

            super.getParameters().addParameter(parameterNode.jjtGetChild(i), parameterId);
        }
    }

    @Override
    public String toString() {
        return "- RETURN: " + getReturnType() + System.lineSeparator()
                + System.lineSeparator()
                + super.toString();
    }
}
