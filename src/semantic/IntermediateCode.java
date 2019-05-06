package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.LinkedList;

/**
 * Class contaning a Function Body's Intermediate Code.
 *
 * It is a stack-based Intermediate Code, with most instructions being the same as JVM's.
 *
 * @see FunctionTable
 */
class IntermediateCode {
    private final FunctionTable functionTable;
    private LinkedList<IntermediateInstruction> instructions = new LinkedList<>();

    /**
     * Creates an Intermediate Code class for a Function
     * @param functionTable Function Table, where the Intermediate Code belongs to
     */
    IntermediateCode(FunctionTable functionTable) {
        this.functionTable = functionTable;
    }

    /**
     * Generates the Intermediate Code for the Function
     *  @param bodyNode AST Root of the Function's body
     * @param i Index of the first statement child in the provided node
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     * @param methodList List of Method Signatures kept during the Semantic Analysis
     */
    void generateFunctionCode(Node bodyNode, int i, LinkedList<Type> typeList, LinkedList<MethodSignature> methodList) {
        Node statementNode;
        while (i < bodyNode.jjtGetNumChildren()) {
            statementNode = bodyNode.jjtGetChild(i++);

            final int id = statementNode.getId();
            switch(id) {
                case ParserTreeConstants.JJTASSIGN:
                    Node assignNode = statementNode.jjtGetChild(0);

                    if (assignNode.getId() == ParserTreeConstants.JJTID) {
                        generateExpressionCode(statementNode.jjtGetChild(1), typeList, methodList);
                        instructions.addLast(new IntermediateInstruction(id,
                                String.valueOf(assignNode.jjtGetValue()), typeList.remove()));
                    }
                    else {
                        generateExpressionCode(statementNode.jjtGetChild(0).jjtGetChild(0), typeList, methodList);
                        generateExpressionCode(statementNode.jjtGetChild(0).jjtGetChild(1), typeList, methodList);
                        generateExpressionCode(statementNode.jjtGetChild(1), typeList, methodList);
                        instructions.addLast(new IntermediateInstruction(id, typeList.remove()));
                    }
                    break;
                case ParserTreeConstants.JJTIF:
                    // TODO Generate ICode for IF construct
                    break;
                case ParserTreeConstants.JJTWHILE:
                    // TODO Generate ICode for WHILE construct
                    break;
                case ParserTreeConstants.JJTFCALL:
                    generateExpressionCode(statementNode.jjtGetChild(0), typeList, methodList);

                    Node parameterNode = statementNode.jjtGetChild(2);
                    for (int j = 0; j < parameterNode.jjtGetNumChildren(); j++)
                        generateExpressionCode(parameterNode.jjtGetChild(j), typeList, methodList);

                    final Type classType = typeList.remove();
                    instructions.addLast(new IntermediateInstruction(classType,
                            methodList.remove().toDescriptor(classType.toString())));
                    break;
                case ParserTreeConstants.JJTRETURN:
                    generateExpressionCode(statementNode.jjtGetChild(0), typeList, methodList);
                    instructions.addLast(new IntermediateInstruction(id, typeList.remove()));
                    break;
            }
        }
    }

    /**
     * Generates the Intermediate Code of a full expression
     *
     * The tree is traversed using DFS and the resulting instruction list is inverted, resulting in a correct order of
     * instructions, as mentioned in the classes.
     * @param expressionNode AST Root containing the expression
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     * @param methodList List of Method Signatures kept during the Semantic Analysis
     *
     */
    private void generateExpressionCode(Node expressionNode, LinkedList<Type> typeList,
                                        LinkedList<MethodSignature> methodList) {
        LinkedList<IntermediateInstruction> expInstructions = new LinkedList<>();

        generateExpressionCode(expressionNode, expInstructions, typeList, methodList);

        expInstructions.descendingIterator().forEachRemaining((inst) -> instructions.addLast(inst));
    }

    /**
     * Generates the Intermediate Code of a nested-expression (Recursion is done here, since the list is only inverted
     * once after the whole expression is generated)
     * @param expressionNode AST Root containing the expression
     * @param expInstructions List of the already generated instructions of the whole expression
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     * @param methodList List of Method Signatures kept during the Semantic Analysis
     */
    private void generateExpressionCode(Node expressionNode, LinkedList<IntermediateInstruction> expInstructions,
                                        LinkedList<Type> typeList, LinkedList<MethodSignature> methodList) {
        final int id = expressionNode.getId();
        switch(id) {
            case ParserTreeConstants.JJTFCALL:
                final Type classType = typeList.remove();
                expInstructions.addLast(new IntermediateInstruction(classType,
                        methodList.remove().toDescriptor(classType.toString())));

                Node parameterNode = expressionNode.jjtGetChild(2);
                for (int i = parameterNode.jjtGetNumChildren() - 1; i >= 0; i--)
                    generateExpressionCode(parameterNode.jjtGetChild(i), expInstructions, typeList, methodList);

                generateExpressionCode(expressionNode.jjtGetChild(0), expInstructions, typeList, methodList);
                break;
            case ParserTreeConstants.JJTINDEX:
                expInstructions.add(new IntermediateInstruction(id, (Type) expressionNode.jjtGetValue()));
                generateExpressionCode(expressionNode.jjtGetChild(1), expInstructions, typeList, methodList);
                generateExpressionCode(expressionNode.jjtGetChild(0), expInstructions, typeList, methodList);
                break;
            case ParserTreeConstants.JJTPLUS:
            case ParserTreeConstants.JJTMINUS:
            case ParserTreeConstants.JJTTIMES:
            case ParserTreeConstants.JJTDIVIDE:
                expInstructions.addLast(new IntermediateInstruction(id));
                generateExpressionCode(expressionNode.jjtGetChild(1), expInstructions, typeList, methodList);
                generateExpressionCode(expressionNode.jjtGetChild(0), expInstructions, typeList, methodList);
                break;
            case ParserTreeConstants.JJTID:
                // TODO Determine if it's a field or not

                final Type idType = functionTable.getIdType(expressionNode);

                // Single case where this can happen is when it's a class reference for static method invocation
                if (idType == null)  break;

                expInstructions.addLast(new IntermediateInstruction(id,
                        String.valueOf(expressionNode.jjtGetValue()), idType));
                break;
            case ParserTreeConstants.JJTINTEGER:
                expInstructions.addLast(new IntermediateInstruction(id, String.valueOf(expressionNode.jjtGetValue())));
                break;
            case ParserTreeConstants.JJTLENGTH:
            case ParserTreeConstants.JJTNEWARRAY:
                expInstructions.addLast(new IntermediateInstruction(id));
                generateExpressionCode(expressionNode.jjtGetChild(0), expInstructions, typeList, methodList);
                break;
            case ParserTreeConstants.JJTNEWOBJ:
                String value = String.valueOf(expressionNode.jjtGetChild(0).jjtGetValue());
                expInstructions.addLast(new IntermediateInstruction(id,
                        value + System.lineSeparator()
                                + "\tdup" + System.lineSeparator()
                                + "\tinvokespecial " + value + "/<init>()V"));
                break;
            case ParserTreeConstants.JJTTHIS:
                expInstructions.addLast(new IntermediateInstruction(id));
                break;

            // TODO Generation of ICode for conditions
            case ParserTreeConstants.JJTAND:
                break;
            case ParserTreeConstants.JJTLOWER:
                break;
            case ParserTreeConstants.JJTNOT:
                break;
            case ParserTreeConstants.JJTTRUE:
                break;
            case ParserTreeConstants.JJTFALSE:
                break;
        }
    }

    LinkedList<IntermediateInstruction> getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (IntermediateInstruction instruction : instructions) {
            sb.append("\t").append(instruction).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
