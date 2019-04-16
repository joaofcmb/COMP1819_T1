package semantic;

import parser.Node;

public class MethodTable extends FunctionTable {
    private final Type returnType;

    MethodTable(Node methodNode, IntermediateRepresentation ir) throws SemanticException {
        super(methodNode, ir);
        this.returnType = new Type(methodNode.jjtGetChild(0));

        final Node parameterNode = methodNode.jjtGetChild(2);
        for (int i = 0; i < parameterNode.jjtGetNumChildren(); i+=2) {
            Node parameterId = parameterNode.jjtGetChild(i+1);

            //TODO Complete Semantic Error (Id already exists within scope)
            if (ir.getAttributes().containsId(parameterId))    throw new SemanticException();

            parameters.addParameter(parameterNode.jjtGetChild(i), parameterId);
        }

        analyseBody(methodNode.jjtGetChild(3));
    }

    @Override
    public String toString() {
        return "- RETURN: " + returnType + System.lineSeparator()
                + System.lineSeparator()
                + super.toString();
    }
}
