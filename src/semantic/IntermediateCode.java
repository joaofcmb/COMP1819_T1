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
     *
     * @param bodyNode AST Root of the Function's body
     * @param i Index of the first statement child in the provided node
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     */
    void generateFunctionCode(Node bodyNode, int i, LinkedList<Type> typeList) {
        Node statementNode;
        while (i < bodyNode.jjtGetNumChildren()) {
            statementNode = bodyNode.jjtGetChild(i++);

            final int id = statementNode.getId();
            switch(id) {
                case ParserTreeConstants.JJTASSIGN:
                    // TODO Array Assignment Code (Make sure to remove the restraint on the typeList in semantic analysis)
                    Node assignNode = statementNode.jjtGetChild(0);
                    if (assignNode.getId() == ParserTreeConstants.JJTID) {
                        generateExpressionCode(statementNode.jjtGetChild(1), typeList);
                        instructions.addLast(new IntermediateInstruction(id,
                                String.valueOf(assignNode.jjtGetValue()), typeList.remove()));
                    }
                    break;
                case ParserTreeConstants.JJTIF:
                    // TODO Generate ICode for IF construct
                    break;
                case ParserTreeConstants.JJTWHILE:
                    // TODO Generate ICode for WHILE construct
                    break;
                case ParserTreeConstants.JJTFCALL:
                    generateExpressionCode(statementNode.jjtGetChild(0), typeList);

                    Node parameterNode = statementNode.jjtGetChild(2);
                    for (int j = 0; j < parameterNode.jjtGetNumChildren(); j++)
                        generateExpressionCode(parameterNode.jjtGetChild(j), typeList);

                    final String methodId = String.join("/", typeList.remove().toString(),
                            String.valueOf(statementNode.jjtGetChild(1).jjtGetValue()));

                    instructions.addLast(new IntermediateInstruction(id, methodId));
                    break;
                case ParserTreeConstants.JJTRETURN:
                    generateExpressionCode(statementNode.jjtGetChild(0), typeList);
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
     *
     * @param expressionNode AST Root containing the expression
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     */
    private void generateExpressionCode(Node expressionNode, LinkedList<Type> typeList) {
        LinkedList<IntermediateInstruction> expInstructions = new LinkedList<>();

        generateExpressionCode(expressionNode, expInstructions, typeList);

        expInstructions.descendingIterator().forEachRemaining((inst) -> instructions.addLast(inst));
    }

    /**
     * Generates the Intermediate Code of a nested-expression (Recursion is done here, since the list is only inverted
     * once after the whole expression is generated)
     *
     * @param expressionNode AST Root containing the expression
     * @param expInstructions List of the already generated instructions of the whole expression
     * @param typeList List of Types kept during the Semantic Analysis that are useful for the Intermediate Code Gen
     */
    private void generateExpressionCode(Node expressionNode, LinkedList<IntermediateInstruction> expInstructions,
                                        LinkedList<Type> typeList) {
        final int id = expressionNode.getId();
        switch(id) {
            case ParserTreeConstants.JJTFCALL:
                final String methodId = String.join("/", typeList.remove().toString(),
                        String.valueOf(expressionNode.jjtGetChild(1).jjtGetValue()));

                expInstructions.addLast(new IntermediateInstruction(id, methodId));

                generateExpressionCode(expressionNode.jjtGetChild(0), expInstructions, typeList);

                Node parameterNode = expressionNode.jjtGetChild(2);
                for (int i = 0; i < parameterNode.jjtGetNumChildren(); i++)
                    generateExpressionCode(parameterNode.jjtGetChild(i), expInstructions, typeList);
                break;
            case ParserTreeConstants.JJTINDEX:
            case ParserTreeConstants.JJTPLUS:
            case ParserTreeConstants.JJTMINUS:
            case ParserTreeConstants.JJTTIMES:
            case ParserTreeConstants.JJTDIVIDE:
                expInstructions.addLast(new IntermediateInstruction(id));
                generateExpressionCode(expressionNode.jjtGetChild(1), expInstructions, typeList);
                generateExpressionCode(expressionNode.jjtGetChild(0), expInstructions, typeList);
                break;
            case ParserTreeConstants.JJTID:
                expInstructions.addLast(new IntermediateInstruction(id,
                        String.valueOf(expressionNode.jjtGetValue()), functionTable.getIdType(expressionNode)));
                break;
            case ParserTreeConstants.JJTINTEGER:
                expInstructions.addLast(new IntermediateInstruction(id, String.valueOf(expressionNode.jjtGetValue())));
                break;
            case ParserTreeConstants.JJTLENGTH:
            case ParserTreeConstants.JJTNEWARRAY:
                expInstructions.addLast(new IntermediateInstruction(id));
                generateExpressionCode(expressionNode.jjtGetChild(0), expInstructions, typeList);
                break;
            case ParserTreeConstants.JJTNEWOBJ:
                expInstructions.addLast(new IntermediateInstruction(id,
                        String.valueOf(expressionNode.jjtGetChild(0).jjtGetValue())));
                break;
            case ParserTreeConstants.JJTTHIS:
                //TODO figure out how to do dis
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (IntermediateInstruction instruction : instructions) {
            sb.append("   - ").append(instruction).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
