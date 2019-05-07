package semantic;

import parser.Node;

/**
 * Class containing all the relevant information of Main
 *
 * @see FunctionTable
 */
class MainTable extends FunctionTable {
    /**
     * Creates a Table for the Main function, initializing its Base Attributes and filling its Symbol Tables
     *
     * @param mainNode AST Root containing Main
     * @param ir IR of the class this method belongs to
     *
     * @throws SemanticException on Semantic Error (Conflicting Symbols)
     */
    MainTable(Node mainNode, IntermediateRepresentation ir) throws SemanticException {
        super(mainNode.jjtGetChild(1), ir);

        final Node parameterId = mainNode.jjtGetChild(0);

        getParameters().addParameter(parameterId);
    }
}
