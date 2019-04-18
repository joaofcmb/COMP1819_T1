package semantic;

import parser.Node;

class MainTable extends FunctionTable {
    MainTable(Node methodNode, IntermediateRepresentation ir) throws SemanticException {
        super(methodNode.jjtGetChild(1), ir);

        final Node parameterId = methodNode.jjtGetChild(0);

        // TODO Complete Semantic Error (Id already exists within scope)
        if (ir.getAttributes().containsId(parameterId))    throw new SemanticException();
        getParameters().addParameter(parameterId);
    }
}
