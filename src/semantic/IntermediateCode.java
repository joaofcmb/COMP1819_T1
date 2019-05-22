package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.Collections;
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

    private int labelId = 1;

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
                        if (functionTable.isClassField(assignNode)) {
                            instructions.addLast(new IntermediateInstruction(ParserTreeConstants.JJTTHIS));
                            generateExpressionCode(statementNode.jjtGetChild(1), typeList, methodList);
                            instructions.addLast(new IntermediateInstruction(id,
                                    functionTable.getClassIdentifier() +  "/" + assignNode.jjtGetValue(),
                                    typeList.remove()));
                        }
                        else {
                            generateExpressionCode(statementNode.jjtGetChild(1), typeList, methodList);
                            instructions.addLast(new IntermediateInstruction(id,
                                    String.valueOf(assignNode.jjtGetValue()), typeList.remove()));
                        }
                    }
                    else {
                        generateExpressionCode(statementNode.jjtGetChild(0).jjtGetChild(0), typeList, methodList);
                        generateExpressionCode(statementNode.jjtGetChild(0).jjtGetChild(1), typeList, methodList);
                        generateExpressionCode(statementNode.jjtGetChild(1), typeList, methodList);
                        instructions.addLast(new IntermediateInstruction(id, typeList.remove()));
                    }
                    break;
                case ParserTreeConstants.JJTIF:
                    final int resume = labelId++, target = labelId++;
                    generateConditionCode(statementNode.jjtGetChild(0).jjtGetChild(0), instructions,
                            typeList, methodList, target, true);

                    generateFunctionCode(statementNode.jjtGetChild(1), 0, typeList, methodList);
                    instructions.addLast(IntermediateInstruction.GOTO(resume));
                    instructions.addLast(IntermediateInstruction.LABEL(target));

                    generateFunctionCode(statementNode.jjtGetChild(2), 0, typeList, methodList);
                    instructions.addLast(IntermediateInstruction.LABEL(resume));
                    break;
                case ParserTreeConstants.JJTWHILE:
                    final int condition = labelId++, body = labelId++;

                    instructions.addLast(IntermediateInstruction.GOTO(condition));
                    instructions.addLast(IntermediateInstruction.LABEL(body));

                    generateFunctionCode(statementNode.jjtGetChild(1), 0, typeList, methodList);

                    instructions.addLast(IntermediateInstruction.LABEL(condition));
                    generateConditionCode(statementNode.jjtGetChild(0).jjtGetChild(0), instructions,
                            typeList, methodList, body, false);
                    break;
                case ParserTreeConstants.JJTFCALL:
                    generateExpressionCode(statementNode.jjtGetChild(0), typeList, methodList);

                    Node parameterNode = statementNode.jjtGetChild(2);
                    for (int j = 0; j < parameterNode.jjtGetNumChildren(); j++)
                        generateExpressionCode(parameterNode.jjtGetChild(j), typeList, methodList);

                    final Type classType = typeList.remove();
                    final MethodSignature methodSignature = methodList.remove();
                    instructions.addLast(new IntermediateInstruction(classType,
                            methodSignature.toDescriptor(classType.toString())));

                    if (!(methodSignature.getReturnType().equals(Type.VOID())))
                        instructions.addLast(IntermediateInstruction.POP());
                    break;
                case ParserTreeConstants.JJTRETURN:
                    generateExpressionCode(statementNode.jjtGetChild(0), typeList, methodList);
                    instructions.addLast(new IntermediateInstruction(id, typeList.remove()));
                    break;
            }
        }
    }

    /**
     * Generates the Intermediate Code of a condition
     *
     * @param conditionNode AST Root containing the condition
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     * @param methodList List of Method Signatures kept during the Semantic Analysis
     * @param eval Whether the condition must be evaluated true or false
     */
    private void generateConditionCode(Node conditionNode, LinkedList<IntermediateInstruction> condInstructions,
                                       LinkedList<Type> typeList, LinkedList<MethodSignature> methodList,
                                       int target, boolean eval) {
        final int id = conditionNode.getId();

        switch(id) {
            case ParserTreeConstants.JJTAND:
                if (!eval) {
                    final int newTarget = labelId++;
                    generateConditionCode(conditionNode.jjtGetChild(0), condInstructions, typeList, methodList,
                            newTarget, true);
                    generateConditionCode(conditionNode.jjtGetChild(1), condInstructions, typeList, methodList,
                            newTarget, true);
                    condInstructions.addLast(IntermediateInstruction.GOTO(target));
                    condInstructions.addLast(IntermediateInstruction.LABEL(newTarget));
                }
                else {
                    generateConditionCode(conditionNode.jjtGetChild(0), condInstructions, typeList, methodList,
                            target, true);
                    generateConditionCode(conditionNode.jjtGetChild(1), condInstructions, typeList, methodList,
                            target, true);
                }
                break;
            case ParserTreeConstants.JJTLOWER:
                getExpressionCode(conditionNode.jjtGetChild(0), condInstructions, typeList, methodList);
                getExpressionCode(conditionNode.jjtGetChild(1), condInstructions, typeList, methodList);
                condInstructions.addLast(new IntermediateInstruction(id, eval, target));
                break;
            case ParserTreeConstants.JJTNOT:
                generateConditionCode(conditionNode.jjtGetChild(0), condInstructions, typeList, methodList,
                        target, !eval);
                break;
            default:
                getExpressionCode(conditionNode, condInstructions, typeList, methodList);
                condInstructions.addLast(new IntermediateInstruction(id, eval, target));
                break;
        }
    }

    private void generateExpressionCode(Node expressionNode, LinkedList<Type> typeList,
                                        LinkedList<MethodSignature> methodList) {
        getExpressionCode(expressionNode, instructions, typeList, methodList);
    }

    /**
     * Generates the Intermediate Code of a full expression
     *
     * The tree is traversed using DFS and the resulting instruction list is inverted, resulting in a correct order of
     * instructions, as mentioned in the classes.
     * @param expressionNode AST Root containing the expression
     * @param instructions List to store the generated Intermediate Code
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     * @param methodList List of Method Signatures kept during the Semantic Analysis
     */
    private void getExpressionCode(Node expressionNode, LinkedList<IntermediateInstruction> instructions,
                                   LinkedList<Type> typeList, LinkedList<MethodSignature> methodList) {
        LinkedList<IntermediateInstruction> expInstructions = new LinkedList<>();

        generateExpressionCode(expressionNode, expInstructions, typeList, methodList);

        expInstructions.descendingIterator().forEachRemaining(instructions::addLast);
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
                final Type idType = functionTable.getIdType(expressionNode);

                // Single case where this can happen is when it's a class reference for static method invocation
                if (idType == null)  break;

                if (functionTable.isClassField(expressionNode)) {
                    expInstructions.addLast(new IntermediateInstruction(id,
                            functionTable.getClassIdentifier() +  "/" + expressionNode.jjtGetValue(), idType));
                    expInstructions.addLast(new IntermediateInstruction(ParserTreeConstants.JJTTHIS));
                }
                else {
                    expInstructions.addLast(new IntermediateInstruction(id,
                            String.valueOf(expressionNode.jjtGetValue()), idType));
                }
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
            case ParserTreeConstants.JJTAND:
            case ParserTreeConstants.JJTLOWER:
            case ParserTreeConstants.JJTNOT:
                final int resume = labelId++, target = labelId++, expIndex = expInstructions.size();

                generateConditionCode(expressionNode, expInstructions, typeList, methodList, target, true);
                expInstructions.addLast(new IntermediateInstruction(ParserTreeConstants.JJTTRUE));
                expInstructions.addLast(IntermediateInstruction.GOTO(resume));
                expInstructions.addLast(IntermediateInstruction.LABEL(target));
                expInstructions.addLast(new IntermediateInstruction(ParserTreeConstants.JJTFALSE));
                expInstructions.addLast(IntermediateInstruction.LABEL(resume));

                Collections.reverse(expInstructions.subList(expIndex, expInstructions.size()));
                break;
            case ParserTreeConstants.JJTTHIS:
            case ParserTreeConstants.JJTTRUE:
            case ParserTreeConstants.JJTFALSE:
                expInstructions.addLast(new IntermediateInstruction(id));
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
