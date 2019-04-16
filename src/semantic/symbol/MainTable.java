package semantic.symbol;

import parser.Node;
import semantic.SemanticException;

class MainTable extends FunctionTable {
    MainTable(Node methodNode, SymbolTable classAttributes) throws SemanticException {
        super(methodNode, classAttributes);

        Node parameterId = methodNode.jjtGetChild(0);

        //TODO Complete Semantic Error (Id already exists within scope) NOT TESTED
        if (classAttributes.containsId(parameterId))    throw new SemanticException();
        parameters.addParameter(parameterId);

        fillVariables(methodNode.jjtGetChild(1));
    }
}
