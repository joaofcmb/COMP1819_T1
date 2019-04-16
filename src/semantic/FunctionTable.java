package semantic;

import parser.Node;
import parser.ParserTreeConstants;

class FunctionTable {
    private final Node methodNode;
    private final IntermediateRepresentation classTable;

    final SymbolTable parameters = new SymbolTable();
    private final SymbolTable variables = new SymbolTable();

    private final IntermediateCode intermediateCode = new IntermediateCode();


    FunctionTable(Node methodNode, IntermediateRepresentation ir) {
        this.methodNode = methodNode;
        this.classTable = ir;
    }

    private int fillVariables(Node bodyNode) throws SemanticException {
        int i = 0;
        Node varNode = bodyNode.jjtGetChild(i);
        while (varNode.getId() == ParserTreeConstants.JJTVAR) {
            Node idNode = varNode.jjtGetChild(1);

            //TODO Complete Semantic Error (Id already exists within scope)
            if (classTable.getAttributes().containsId(idNode) || parameters.containsId(idNode))
                throw new SemanticException();

            variables.addDeclaration(varNode);

            varNode = bodyNode.jjtGetChild(++i);
        }

        return i;
    }

    void analyseBody(Node bodyNode) throws SemanticException {
        final int firstStatement = fillVariables(bodyNode);
        analyseStatements(bodyNode, firstStatement);
    }

    private void analyseStatements(Node bodyNode, int i) throws SemanticException {
        Node statementNode;
        while (i < bodyNode.jjtGetNumChildren()) {
            statementNode = bodyNode.jjtGetChild(i++);

            switch(statementNode.getId()) {
                case ParserTreeConstants.JJTASSIGN:
                    final Type assignType = analyseExpression(statementNode.jjtGetChild(0));
                    final Type expressionType = analyseExpression(statementNode.jjtGetChild(1));

                    // TODO Complete Semantic Error (Invalid Assignment) NOT TESTED
                    if (!assignType.equals(expressionType)) throw new SemanticException();
                    break;
                case ParserTreeConstants.JJTIF:
                    break;
                case ParserTreeConstants.JJTWHILE:
                    break;
                case ParserTreeConstants.JJTFCALL:
                    final Type classType = analyseExpression(statementNode.jjtGetChild(0));
                    final String methodId = String.valueOf(statementNode.jjtGetChild(1).jjtGetValue());
                    final Type[] parameterTypes = analyseParameters(statementNode.jjtGetChild(2));

                    classTable.checkMethod(classType, methodId, parameterTypes);
                    break;
                default:
                    // TODO Complete Semantic Error (Invalid Statement) NOT TESTED
                    throw new SemanticException();
            }
        }
    }

    private Type analyseExpression(Node classNode) {
        return new Type(); // Temp
    }

    private Type[] analyseParameters(Node jjtGetChild) {
        return new Type[]{new Type()}; // Temp
    }

    @Override
    public String toString() {
        return "- PARAMETERS:" + System.lineSeparator() +
                parameters + System.lineSeparator() +
                "- LOCAL VARIABLES:" + System.lineSeparator() +
                variables;
    }
}
