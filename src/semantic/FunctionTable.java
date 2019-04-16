package semantic;

import parser.Node;
import parser.ParserTreeConstants;

class FunctionTable {
    private final Node methodNode;
    private final SymbolTable classAttributes;

    final SymbolTable parameters = new SymbolTable();
    private final SymbolTable variables = new SymbolTable();

    private int firstStatementNum;


    FunctionTable(Node methodNode, SymbolTable classAttributes) throws SemanticException {
        this.methodNode = methodNode;
        this.classAttributes = classAttributes;
    }

    void fillParameters(Node parameterNode) throws SemanticException {
        for (int i = 0; i < parameterNode.jjtGetNumChildren(); i+=2) {
            Node parameterId = parameterNode.jjtGetChild(i+1);

            //TODO Complete Semantic Error (Id already exists within scope)
            if (classAttributes.containsId(parameterId))    throw new SemanticException();

            parameters.addParameter(parameterNode.jjtGetChild(i), parameterId);
        }
    }

    void fillVariables(Node bodyNode) throws SemanticException {
        int i = 0;
        Node varNode = bodyNode.jjtGetChild(i);
        while (varNode.getId() == ParserTreeConstants.JJTVAR) {
            Node idNode = varNode.jjtGetChild(1);

            //TODO Complete Semantic Error (Id already exists within scope)
            if (classAttributes.containsId(idNode) || parameters.containsId(idNode)) throw new SemanticException();

            variables.addDeclaration(varNode);

            varNode = bodyNode.jjtGetChild(++i);
        }

        this.firstStatementNum = i;
    }

    @Override
    public String toString() {
        return "- PARAMETERS:" + System.lineSeparator() +
                parameters + System.lineSeparator() +
                "- LOCAL VARIABLES:" + System.lineSeparator() +
                variables;
    }
}
