package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.LinkedList;

class FunctionTable {
    private final Node bodyNode;

    private final IntermediateRepresentation classTable;

    private final SymbolTable parameters = new SymbolTable();
    private final SymbolTable variables = new SymbolTable();

    private final Type returnType;

    private final IntermediateCode intermediateCode = new IntermediateCode(this);


    FunctionTable(Node bodyNode, IntermediateRepresentation ir) {
        this(bodyNode, ir, null);
    }

    FunctionTable(Node bodyNode, IntermediateRepresentation ir, Type returnType) {
        this.bodyNode = bodyNode;
        this.classTable = ir;
        this.returnType = returnType;
    }

    private int fillVariables(Node bodyNode) throws SemanticException {
        int i = 0;
        while (i < bodyNode.jjtGetNumChildren()) {
            Node varNode = bodyNode.jjtGetChild(i++);

            if (varNode.getId() != ParserTreeConstants.JJTVAR)  return i - 1;

            Node idNode = varNode.jjtGetChild(1);

            //TODO Complete Semantic Error (Id already exists within scope)
            if (classTable.getAttributes().containsId(idNode) || parameters.containsId(idNode))
                throw new SemanticException();

            variables.addDeclaration(varNode);
        }

        return i;
    }

    void analyseBody() throws SemanticException {
        final int firstStatement = fillVariables(bodyNode);
        LinkedList<Type> typeList = analyseStatements(bodyNode, firstStatement);
        intermediateCode.generateFunctionCode(bodyNode, firstStatement, typeList);
    }

    private LinkedList<Type> analyseStatements(Node bodyNode, int i) throws SemanticException {
        LinkedList<Type> typeList = new LinkedList<>();

        Node statementNode;
        while (i < bodyNode.jjtGetNumChildren()) {
            statementNode = bodyNode.jjtGetChild(i++);

            switch(statementNode.getId()) {
                case ParserTreeConstants.JJTASSIGN:
                    final Type assignType = analyseExpression(statementNode.jjtGetChild(0), typeList);
                    final Type expressionType = analyseExpression(statementNode.jjtGetChild(1), typeList);

                    // TODO Complete Semantic Error (Invalid Assignment Types)
                    if (!assignType.equals(expressionType)) throw new SemanticException();

                    // TODO Remove restriction for array assignments
                    if (statementNode.jjtGetChild(0).getId() == ParserTreeConstants.JJTID)
                        typeList.add(assignType);
                    break;
                case ParserTreeConstants.JJTIF:
                case ParserTreeConstants.JJTWHILE:
                    final Type conditionType = analyseExpression(statementNode.jjtGetChild(0).jjtGetChild(0), typeList);

                    // TODO Complete Semantic Error (Condition not Boolean)
                    if (!conditionType.isBoolean()) throw new SemanticException();

                    analyseStatements(statementNode.jjtGetChild(1), 0);

                    if (statementNode.jjtGetNumChildren() == 3)
                        analyseStatements(statementNode.jjtGetChild(2), 0);
                    break;
                case ParserTreeConstants.JJTFCALL:
                    final Type classType = analyseExpression(statementNode.jjtGetChild(0), typeList);
                    final String methodId = String.valueOf(statementNode.jjtGetChild(1).jjtGetValue());
                    final Type[] parameterTypes = analyseParameters(statementNode.jjtGetChild(2), typeList);

                    classTable.checkMethod(classType, methodId, parameterTypes);

                    typeList.add(classType);
                    break;
                case ParserTreeConstants.JJTRETURN:
                    final Type returnType = analyseExpression(statementNode.jjtGetChild(0), typeList);

                    // TODO Complete Semantic Error (Invalid Return Type)
                    if (!this.returnType.equals(returnType))    throw new SemanticException();

                    typeList.add(returnType);
                    break;
                default:
                    // TODO Complete Semantic Error (Invalid Statement)
                    throw new SemanticException();
            }
        }

        return typeList;
    }

    private Type[] analyseParameters(Node parameterNode, LinkedList<Type> typeList) throws SemanticException {
        Type[] parameterTypes = new Type[parameterNode.jjtGetNumChildren()];

        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = analyseExpression(parameterNode.jjtGetChild(i), typeList);
        }

        return parameterTypes;
    }

    private Type analyseExpression(Node expressionNode, LinkedList<Type> typeList) throws SemanticException {
        switch(expressionNode.getId()) {
            case ParserTreeConstants.JJTFCALL:
                final Type classType = analyseExpression(expressionNode.jjtGetChild(0), typeList);
                final String methodId = String.valueOf(expressionNode.jjtGetChild(1).jjtGetValue());
                final Type[] parameterTypes = analyseParameters(expressionNode.jjtGetChild(2), typeList);

                typeList.add(classType);

                return classTable.checkMethod(classType, methodId, parameterTypes);
            case ParserTreeConstants.JJTINDEX:
                final Type indexType = analyseExpression(expressionNode.jjtGetChild(1), typeList);

                // TODO Complete Semantic Error (array index not an integer)
                if (!indexType.isInt())         throw new SemanticException();

                // TODO Add String array functionality once it is better known how it is meant to be used
                final Type arrayType = analyseExpression(expressionNode.jjtGetChild(0), typeList);

                // TODO Complete Semantic Error (Trying to access index of non array)
                if (!arrayType.isIntArray())    throw new SemanticException();

                return Type.INT();
            case ParserTreeConstants.JJTLENGTH:
                final Type targetType = analyseExpression(expressionNode.jjtGetChild(0), typeList);

                // TODO Complete Semantic Error (Trying to access length of non array)
                if (!targetType.isIntArray() && !targetType.isStringArray()) throw new SemanticException();

                return Type.INT();
            case ParserTreeConstants.JJTAND:
                final Type firstBool = analyseExpression(expressionNode.jjtGetChild(0), typeList);

                // TODO Complete Semantic Error (Not a boolean)
                if (!firstBool.isBoolean())    throw new SemanticException();

                final Type secondBool = analyseExpression(expressionNode.jjtGetChild(1), typeList);

                // TODO Complete Semantic Error (Not a boolean)
                if (!secondBool.isBoolean())    throw new SemanticException();

                return Type.BOOLEAN();
            case ParserTreeConstants.JJTLOWER:
            case ParserTreeConstants.JJTPLUS:
            case ParserTreeConstants.JJTMINUS:
            case ParserTreeConstants.JJTTIMES:
            case ParserTreeConstants.JJTDIVIDE:
                final Type secondOp = analyseExpression(expressionNode.jjtGetChild(1), typeList);

                // TODO Complete Semantic Error (Invalid Operand Type)
                if (!secondOp.isInt())    throw new SemanticException();

                final Type firstOp = analyseExpression(expressionNode.jjtGetChild(0), typeList);

                // TODO Complete Semantic Error (Invalid Operand Type)
                if (!firstOp.isInt())    throw new SemanticException();

                if (expressionNode.getId() == ParserTreeConstants.JJTLOWER)     return Type.BOOLEAN();
                else                                                            return Type.INT();
            case ParserTreeConstants.JJTID:
                final Type idType = getIdType(expressionNode);

                // TODO Complete Semantic Error (Variable not found)
                if (idType == null)
                    throw new SemanticException("Variable not Found: " + expressionNode.jjtGetValue());

                return idType;
            case ParserTreeConstants.JJTINTEGER:
                return Type.INT();
            case ParserTreeConstants.JJTNOT:
            case ParserTreeConstants.JJTTRUE:
            case ParserTreeConstants.JJTFALSE:
                return Type.BOOLEAN();
            case ParserTreeConstants.JJTTHIS:
                return Type.ID(classTable.getIdentifier());
            case ParserTreeConstants.JJTNEWARRAY:
                final Type lengthType = analyseExpression(expressionNode.jjtGetChild(0), typeList);

                // TODO Complete Semantic Error (Invalid Length)
                if (!lengthType.isInt())        throw new SemanticException();

                return Type.INTARRAY();
            case ParserTreeConstants.JJTNEWOBJ:
                return new Type(expressionNode.jjtGetChild(0));
            default:
                // TODO Complete Semantic Error (Invalid Expression)
                throw new SemanticException();
        }
    }

    Type getIdType(Node expressionNode) {
        if (variables.containsId(expressionNode))
            return variables.getId(expressionNode);
        else if (parameters.containsId(expressionNode))
            return parameters.getId(expressionNode);
        else if (classTable.getAttributes().containsId(expressionNode))
            return classTable.getAttributes().getId(expressionNode);

        return null;
    }

    SymbolTable getParameters() {
        return parameters;
    }

    Type getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "- PARAMETERS:" + System.lineSeparator()
                + parameters + System.lineSeparator()
                + "- LOCAL VARIABLES:" + System.lineSeparator()
                + variables + System.lineSeparator()
                + "- INTERMEDIATE CODE:" + System.lineSeparator()
                + intermediateCode;
    }
}
