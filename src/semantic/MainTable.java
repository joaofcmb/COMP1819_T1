package semantic;

import parser.Node;

class MainTable extends FunctionTable {
    MainTable(Node methodNode, SymbolTable classAttributes) throws SemanticException {
        super(methodNode, classAttributes);

        final Node parameterId = methodNode.jjtGetChild(0);

        //TODO Complete Semantic Error (Id already exists within scope)
        if (classAttributes.containsId(parameterId))    throw new SemanticException();
        parameters.addParameter(parameterId);

        fillVariables(methodNode.jjtGetChild(1));
    }
}
