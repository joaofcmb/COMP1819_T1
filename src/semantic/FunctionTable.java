package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Abstract Class containing all relevant information of a Function (Method or Main)
 *
 * Keeps its return type, Symbol Tables of its parameters and local variables, references to the AST Node containing
 * the function body and the IR to which the function belongs to, and the function's Intermediate Code.
 *
 * @see IntermediateRepresentation
 * @see SymbolTable
 * @see IntermediateCode
 */
public abstract class FunctionTable {
    private final Node bodyNode;

    private final IntermediateRepresentation classTable;

    private final LinkedSymbolTable parameters = new LinkedSymbolTable();
    private final SymbolTable variables = new SymbolTable();

    private final Type returnType;

    private final IntermediateCode intermediateCode = new IntermediateCode(this);
    private final LinkedList<Type> typeList = new LinkedList<>();
    private final LinkedList<MethodSignature> methodList = new LinkedList<>();

    /**
     * Constructor of the class, initializing the Function Table with its AST body Node and its belonging IR without a
     * return type (Only case is Main)
     *
     * @param bodyNode AST Root of the Function body
     * @param ir IntermediateRepresentation to which this Function Table belongs to
     */
    FunctionTable(Node bodyNode, IntermediateRepresentation ir) {
        this(bodyNode, ir, null);
    }

    /**
     * Constructor of the class, initializing the Function Table with its AST body Node, its belonging IR and its
     * Return Type
     *
     * @param bodyNode AST Root of the Function body
     * @param ir IntermediateRepresentation to which this Function Table belongs to
     * @param returnType Return Type of the Function
     */
    FunctionTable(Node bodyNode, IntermediateRepresentation ir, Type returnType) {
        this.bodyNode = bodyNode;
        this.classTable = ir;
        this.returnType = returnType;
    }

    /**
     * Iterates the Local Variable Declarations of the Function Body, adding them to the Symbol Table
     *
     * @return Index of the first statement child in the body node
     *
     * @throws SemanticException on Semantic Error (Conflicting Symbols)
     */
    private int fillVariables() throws SemanticException {
        int i = 0;
        while (i < bodyNode.jjtGetNumChildren()) {
            Node varNode = bodyNode.jjtGetChild(i++);

            if (varNode.getId() != ParserTreeConstants.JJTVAR)  return i - 1;

            Node idNode = varNode.jjtGetChild(1);

            if (parameters.containsId(idNode) || (returnType == null && isClassField(idNode)))
                throw new SemanticException(idNode, "Redefinition of variable (Conflicting Ids within scope)");

            variables.addDeclaration(varNode);
        }

        return i;
    }

    /**
     * Analyses the body for Semantic Errors and, afterwards, generates Intermediate Code
     *
     * @throws SemanticException on Semantic Error
     */
    void analyseAndGenerateBody() throws SemanticException {
        final int firstStatement = fillVariables();
        analyseStatements(bodyNode, firstStatement);
        intermediateCode.generateFunctionCode(bodyNode, firstStatement, typeList, methodList);
    }

    /**
     * Analyses a series of statements (compound statement or body statements)
     *
     * @param statementsNode AST Root containing the statements to be analysed
     * @param i Index of the first statement child in the provided node
     *
     * @return List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Generation
     *
     * @throws SemanticException on Semantic Error
     */
    private void analyseStatements(Node statementsNode, int i) throws SemanticException {
        Node statementNode;
        while (i < statementsNode.jjtGetNumChildren()) {
            statementNode = statementsNode.jjtGetChild(i++);

            switch(statementNode.getId()) {
                case ParserTreeConstants.JJTASSIGN:
                    // TODO If Forward chaining doesnt work (Cannot infer assign type and use it on expression type),
                    //  check if the expression type can be deduced and use it to deduce the assignType.
                    final Type assignType = analyseExpression(statementNode.jjtGetChild(0), typeList, Type.UNKNOWN());
                    final Type expressionType = analyseExpression(statementNode.jjtGetChild(1), typeList, assignType);

                    if (!assignType.equals(expressionType))
                        throw new SemanticException(statementNode,
                                "Invalid assignment of " + expressionType + " to variable of type " + assignType);

                    typeList.add(assignType);
                    break;
                case ParserTreeConstants.JJTIF:
                case ParserTreeConstants.JJTWHILE:
                    final Type conditionType = analyseExpression(statementNode.jjtGetChild(0).jjtGetChild(0),
                            typeList, Type.BOOLEAN());

                    if (!conditionType.isBoolean())
                        throw new SemanticException(statementNode,
                                "Invalid condition of non-boolean type ( " + conditionType + " )");

                    analyseStatements(statementNode.jjtGetChild(1), 0);

                    if (statementNode.jjtGetNumChildren() == 3)
                        analyseStatements(statementNode.jjtGetChild(2), 0);
                    break;
                case ParserTreeConstants.JJTFCALL:
                    // TODO If Backward Chaining of type deduction doesn't work (i.e. Unknown return type used
                    //  as a parameter to a method), attempt to use Forward Chaining (Using the same example, check if
                    //  any known method fits the first method being called and, from there, deduce the unknown return
                    //  type being called as a parameter).
                    Node classNode = statementNode.jjtGetChild(0);
                    Type classType;
                    if (classNode.getId() == ParserTreeConstants.JJTID && getIdType(classNode) == null)
                        classType = Type.CLASS(String.valueOf(classNode.jjtGetValue()));
                    else
                        classType = analyseExpression(statementNode.jjtGetChild(0), typeList, Type.UNKNOWN());
                    final String methodId = String.valueOf(statementNode.jjtGetChild(1).jjtGetValue());
                    final Type[] parameterTypes = analyseParameters(statementNode.jjtGetChild(2), typeList);

                    MethodSignature methodSignature = classTable.checkMethod(statementNode, classType,
                            methodId, parameterTypes);

                    // Deduce the return type of unknown methods
                    if (methodSignature.getReturnType() == null)
                        methodSignature.setReturnType(Type.VOID());

                    if (methodSignature.isParent())
                        typeList.add(Type.ID(classTable.getExtendIdentifier()));
                    else
                        typeList.add(classType);

                    methodList.add(methodSignature);

                    break;
                case ParserTreeConstants.JJTRETURN:
                    final Type returnType = analyseExpression(statementNode.jjtGetChild(0), typeList, this.returnType);

                    if (!this.returnType.equals(returnType))
                        throw new SemanticException(statementNode,
                                "Invalid return expression type, expected " + this.returnType
                                        + " instead of " + returnType + ",");

                    typeList.add(returnType);
                    break;
                default:
                    throw new SemanticException(statementNode, "Invalid statement");
            }
        }
    }

    /**
     * Analyses the parameters from a function call
     *
     * @param parameterNode AST Root containing the parameters being called
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     *
     * @return Types of each analysed parameter
     *
     * @throws SemanticException on Semantic Error
     */
    private Type[] analyseParameters(Node parameterNode, LinkedList<Type> typeList) throws SemanticException {
        Type[] parameterTypes = new Type[parameterNode.jjtGetNumChildren()];

        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = analyseExpression(parameterNode.jjtGetChild(i), typeList, Type.UNKNOWN());
        }

        return parameterTypes;
    }

    /**
     * Analyses an expression
     *
     * @param expressionNode AST Root containing the expression
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     * @param desiredType Desired Type for this expression. Used to infer return types of unknown methods
     *
     * @return Final Type of the expression
     *
     * @throws SemanticException on Semantic Error
     */
    private Type analyseExpression(Node expressionNode, LinkedList<Type> typeList, Type desiredType)
            throws SemanticException {
        switch(expressionNode.getId()) {
            case ParserTreeConstants.JJTFCALL:
                final int typeIndex = typeList.size(), methodIndex = methodList.size();

                final Node classNode = expressionNode.jjtGetChild(0);

                Type classType;
                if (classNode.getId() == ParserTreeConstants.JJTID && getIdType(classNode) == null)
                    classType = Type.CLASS(String.valueOf(classNode.jjtGetValue()));
                else
                    classType = analyseExpression(expressionNode.jjtGetChild(0), typeList, Type.UNKNOWN());

                final String methodId = String.valueOf(expressionNode.jjtGetChild(1).jjtGetValue());
                final Type[] parameterTypes = analyseParameters(expressionNode.jjtGetChild(2), typeList);

                MethodSignature methodSignature = classTable.checkMethod(expressionNode, classType,
                        methodId, parameterTypes);

                // Deduce the return type of unknown methods
                if (methodSignature.getReturnType() == null) {
                    if (desiredType.isUnknown())
                        throw new SemanticException(expressionNode,
                                "Return type of unknown method could not be inferred");

                    methodSignature.setReturnType(desiredType);
                }

                if (methodSignature.isParent())
                    typeList.add(Type.ID(classTable.getExtendIdentifier()));
                else
                    typeList.add(classType);

                methodList.add(methodSignature);

                // When generating stack based intermediate code, the traversal order of parameters is reversed
                Collections.reverse(typeList.subList(typeIndex, typeList.size()));
                Collections.reverse(methodList.subList(methodIndex, methodList.size()));

                return methodSignature.getReturnType();
            case ParserTreeConstants.JJTINDEX:
                final Type indexType = analyseExpression(expressionNode.jjtGetChild(1), typeList, Type.INT());

                if (!indexType.isInt())
                    throw new SemanticException(expressionNode.jjtGetChild(1),
                            "Invalid index, must be an integer instead of " + indexType);

                // TODO Use the current desired type to deduce desired type of arrayType
                final Type arrayType = analyseExpression(expressionNode.jjtGetChild(0), typeList, Type.UNKNOWN());

                if (arrayType.isIntArray()) {
                    expressionNode.jjtSetValue(Type.INT());
                    return Type.INT();
                } else if (arrayType.isStringArray()) {
                    expressionNode.jjtSetValue(Type.STRING());
                    return Type.STRING();
                } else
                    throw new SemanticException(expressionNode.jjtGetChild(0), "Trying to access index of non array");
            case ParserTreeConstants.JJTLENGTH:
                final Type targetType = analyseExpression(expressionNode.jjtGetChild(0), typeList, Type.UNKNOWN());

                if (!targetType.isIntArray() && !targetType.isStringArray())
                    throw new SemanticException(expressionNode,
                            "Attempting to retrieve length of non array ( " + targetType + " )");

                return Type.INT();
            case ParserTreeConstants.JJTAND:
                final Type secondBool = analyseExpression(expressionNode.jjtGetChild(1), typeList, Type.BOOLEAN());

                if (!secondBool.isBoolean())
                    throw new SemanticException(expressionNode.jjtGetChild(1),
                            "Invalid expression of non-boolean type " + secondBool);

                final Type firstBool = analyseExpression(expressionNode.jjtGetChild(0), typeList, Type.BOOLEAN());

                if (!firstBool.isBoolean())
                    throw new SemanticException(expressionNode.jjtGetChild(0),
                            "Invalid expression of non-boolean type " + firstBool);

                return Type.BOOLEAN();
            case ParserTreeConstants.JJTLOWER:
            case ParserTreeConstants.JJTPLUS:
            case ParserTreeConstants.JJTMINUS:
            case ParserTreeConstants.JJTTIMES:
            case ParserTreeConstants.JJTDIVIDE:
                final Type secondOp = analyseExpression(expressionNode.jjtGetChild(1), typeList, Type.INT());

                if (!secondOp.isInt())
                    throw new SemanticException(expressionNode.jjtGetChild(1),
                        "Invalid expression of type " + secondOp + " instead of int");

                final Type firstOp = analyseExpression(expressionNode.jjtGetChild(0), typeList, Type.INT());

                if (!firstOp.isInt())
                    throw new SemanticException(expressionNode.jjtGetChild(0),
                            "Invalid expression of type " + firstOp + " instead of int");

                if (expressionNode.getId() == ParserTreeConstants.JJTLOWER)     return Type.BOOLEAN();
                else                                                            return Type.INT();
            case ParserTreeConstants.JJTID:
                final Type idType = getIdType(expressionNode);

                if (idType == null || (returnType == null && isClassField(expressionNode)))
                    throw new SemanticException(expressionNode,
                            "Variable " + expressionNode.jjtGetValue() + " not found");

                return idType;
            case ParserTreeConstants.JJTINTEGER:
                return Type.INT();
            case ParserTreeConstants.JJTNOT:
                final Type expressionType = analyseExpression(expressionNode.jjtGetChild(0), typeList, Type.BOOLEAN());

                if (!expressionType.isBoolean())
                    throw new SemanticException(expressionNode.jjtGetChild(0),
                            "Invalid expression of type " + expressionType + ". Must be a boolean to apply negate op.");
                return Type.BOOLEAN();
            case ParserTreeConstants.JJTTRUE:
            case ParserTreeConstants.JJTFALSE:
                return Type.BOOLEAN();
            case ParserTreeConstants.JJTTHIS:
                if (this.returnType == null)
                    throw new SemanticException(expressionNode,
                            "Invalid attempt of getting current instance from a static method");

                return Type.ID(classTable.getClassIdentifier());
            case ParserTreeConstants.JJTNEWARRAY:
                final Type lengthType = analyseExpression(expressionNode.jjtGetChild(0), typeList, Type.INT());

                if (!lengthType.isInt())
                    throw new SemanticException(expressionNode,
                            "Invalid array size expression, expected int instead of " + lengthType);

                return Type.INTARRAY();
            case ParserTreeConstants.JJTNEWOBJ:
                return new Type(expressionNode.jjtGetChild(0));
            default:
                throw new SemanticException(expressionNode, "Invalid expression");
        }
    }

    /**
     * Retrieves the Type, from a Symbol Table, of a certain variable being used
     *
     * @param idNode Node containing the Symbol Identifier
     *
     * @return Type of the Symbol, null if it does not exist in this class
     */
    Type getIdType(Node idNode) {
        if (variables.containsId(idNode))           return variables.getId(idNode);
        else if (parameters.containsId(idNode))     return parameters.getId(idNode);
        else if (isClassField(idNode))              return classTable.getAttributes().getId(idNode);

        return null;
    }

    boolean isClassField(Node idNode) {
        return classTable.getAttributes().containsId(idNode);
    }

    public LinkedSymbolTable getParameters() {
        return parameters;
    }

    Type getReturnType() {
        return returnType;
    }

    String getClassIdentifier() {
        return classTable.getClassIdentifier();
    }

    /**
     * @return Human readable format of the Function Table to be printed on the CLI
     */
    @Override
    public String toString() {
        return "  PARAMETERS:" + System.lineSeparator()
                + parameters + System.lineSeparator()
                + "  LOCAL VARIABLES:" + System.lineSeparator()
                + variables + System.lineSeparator()
                + "  INTERMEDIATE CODE:" + System.lineSeparator()
                + intermediateCode;
    }

    public LinkedList<IntermediateInstruction> getIntermediateInstructions() {
        return intermediateCode.getInstructions();
    }

    public String methodCode() {
        return intermediateCode.toString();
    }
}
