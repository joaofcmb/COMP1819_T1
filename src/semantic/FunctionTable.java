package semantic;

import parser.Node;
import parser.ParserTreeConstants;

class FunctionTable {
    private final IntermediateRepresentation classTable;

    final SymbolTable parameters = new SymbolTable();
    private final SymbolTable variables = new SymbolTable();

    private final IntermediateCode intermediateCode = new IntermediateCode();


    FunctionTable(IntermediateRepresentation ir) {
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

                    // TODO Complete Semantic Error (Invalid Assignment Types) NOT TESTED
                    if (!assignType.equals(expressionType)) throw new SemanticException();
                    break;
                case ParserTreeConstants.JJTIF:
                case ParserTreeConstants.JJTWHILE:
                    final Type conditionType = analyseExpression(statementNode.jjtGetChild(0));

                    // TODO Complete Semantic Error (Condition not Boolean) NOT TESTED
                    if (!conditionType.isBoolean()) throw new SemanticException();

                    analyseStatements(statementNode.jjtGetChild(1), 0);

                    if (statementNode.jjtGetNumChildren() == 3)
                        analyseStatements(statementNode.jjtGetChild(2), 0);
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

    private Type[] analyseParameters(Node parameterNode) throws SemanticException {
        Type[] parameterTypes = new Type[parameterNode.jjtGetNumChildren()];

        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = analyseExpression(parameterNode.jjtGetChild(i));
        }

        return parameterTypes;
    }

    private Type analyseExpression(Node expressionNode) throws SemanticException {
        switch(expressionNode.getId()) {
            case ParserTreeConstants.JJTFCALL:
                final Type classType = analyseExpression(expressionNode.jjtGetChild(0));
                final String methodId = String.valueOf(expressionNode.jjtGetChild(1).jjtGetValue());
                final Type[] parameterTypes = analyseParameters(expressionNode.jjtGetChild(2));

                return classTable.checkMethod(classType, methodId, parameterTypes);
            case ParserTreeConstants.JJTINDEX:
                // TODO Add String array functionality once it is better known how it is meant to be used
                final Type arrayType = analyseExpression(expressionNode.jjtGetChild(0));

                // TODO Complete Semantic Error (Trying to access index of non array) NOT TESTED
                if (!arrayType.isIntArray())    throw new SemanticException();

                final Type indexType = analyseExpression(expressionNode.jjtGetChild(1));

                // TODO Complete Semantic Error (array index not an integer) NOT TESTED
                if (!indexType.isInt())         throw new SemanticException();

                return Type.INT();
            case ParserTreeConstants.JJTLENGTH:
                final Type targetType = analyseExpression(expressionNode.jjtGetChild(0));

                // TODO Complete Semantic Error (Trying to access length of non array) NOT TESTED
                if (!targetType.isIntArray() && !targetType.isStringArray()) throw new SemanticException();

                return Type.INT();
            case ParserTreeConstants.JJTAND:
                final Type firstBool = analyseExpression(expressionNode.jjtGetChild(0));

                // TODO Complete Semantic Error (Not a boolean) NOT TESTED
                if (!firstBool.isBoolean())    throw new SemanticException();

                final Type secondBool = analyseExpression(expressionNode.jjtGetChild(1));

                // TODO Complete Semantic Error (Not a boolean) NOT TESTED
                if (!secondBool.isBoolean())    throw new SemanticException();

                return Type.BOOLEAN();
            case ParserTreeConstants.JJTLOWER:
            case ParserTreeConstants.JJTPLUS:
            case ParserTreeConstants.JJTMINUS:
            case ParserTreeConstants.JJTTIMES:
            case ParserTreeConstants.JJTDIVIDE:
                final Type firstOp = analyseExpression(expressionNode.jjtGetChild(0));

                // TODO Complete Semantic Error (Invalid Operand Type) NOT TESTED
                if (!firstOp.isInt())    throw new SemanticException();

                final Type secondOp = analyseExpression(expressionNode.jjtGetChild(1));

                // TODO Complete Semantic Error (Invalid Operand Type) NOT TESTED
                if (!secondOp.isInt())    throw new SemanticException();

                if (expressionNode.getId() == ParserTreeConstants.JJTLOWER)     return Type.BOOLEAN();
                else                                                            return Type.INT();
            case ParserTreeConstants.JJTID:
                if (variables.containsId(expressionNode))
                    return variables.getId(expressionNode);
                else if (parameters.containsId(expressionNode))
                    return parameters.getId(expressionNode);
                else if (classTable.getAttributes().containsId(expressionNode))
                    return classTable.getAttributes().getId(expressionNode);
                else
                    throw new SemanticException(); // TODO Complete Semantic Error (Variable not found) NOT TESTED
            case ParserTreeConstants.JJTINTEGER:
                return Type.INT();
            case ParserTreeConstants.JJTNOT:
            case ParserTreeConstants.JJTTRUE:
            case ParserTreeConstants.JJTFALSE:
                return Type.BOOLEAN();
            case ParserTreeConstants.JJTTHIS:
                return Type.ID(classTable.getIdentifier());
            case ParserTreeConstants.JJTNEWARRAY:
                final Type lengthType = analyseExpression(expressionNode.jjtGetChild(0));

                // TODO Complete Semantic Error (Invalid Length) NOT TESTED
                if (!lengthType.isInt())        throw new SemanticException();

                return Type.INTARRAY();
            case ParserTreeConstants.JJTNEWOBJ:
                return new Type(expressionNode.jjtGetChild(0));
            default:
                // TODO Complete Semantic Error (Invalid Expression)
                throw new SemanticException();
        }
    }

    @Override
    public String toString() {
        return "- PARAMETERS:" + System.lineSeparator() +
                parameters + System.lineSeparator() +
                "- LOCAL VARIABLES:" + System.lineSeparator() +
                variables;
    }
}
