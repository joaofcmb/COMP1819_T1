package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.LinkedList;

class IntermediateCode {
    private LinkedList<IntermediateInstruction> instructions = new LinkedList<>();

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

    private void generateExpressionCode(Node expressionNode, LinkedList<Type> typeList) {
        LinkedList<IntermediateInstruction> expInstructions = new LinkedList<>();

        generateExpressionCode(expressionNode, expInstructions, typeList);

        expInstructions.descendingIterator().forEachRemaining((inst) -> instructions.addLast(inst));
    }

    private void generateExpressionCode(Node expressionNode, LinkedList<IntermediateInstruction> expInstructions,
                                        LinkedList<Type> typeList) {
        final int id = expressionNode.getId();
        switch(id) {
            case ParserTreeConstants.JJTFCALL:
                expInstructions.addLast(new IntermediateInstruction(id,
                        String.valueOf(expressionNode.jjtGetChild(1).jjtGetValue()), typeList.remove()));

                generateExpressionCode(expressionNode.jjtGetChild(0), typeList);

                Node parameterNode = expressionNode.jjtGetChild(2);
                for (int i = 0; i < parameterNode.jjtGetNumChildren(); i++)
                    generateExpressionCode(parameterNode.jjtGetChild(i), typeList);
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
            sb.append("   -").append(instruction).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
