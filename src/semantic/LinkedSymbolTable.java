package semantic;

import parser.Node;

import java.util.LinkedHashMap;
import java.util.Set;


/**
 * Class representing a SymbolTable, with the guarantee of insertion order preservation during iteration
 *
 * @see SymbolTable
 */
public class LinkedSymbolTable extends SymbolTable {
    LinkedSymbolTable() {
        super(new LinkedHashMap<>());
    }

    // Uses default Type (String[], used only on main)
    void addParameter(Node idNode) throws SemanticException {
        addParameter(Type.STRINGARRAY(), idNode);
    }

    // Usual format for adding parameters, creating a type from the given Type Node from the AST
    void addParameter(Node typeNode, Node idNode) throws SemanticException {
        addParameter(new Type(typeNode), idNode);
    }


    /**
     * Adds a Symbol to the table given a pair of Type and Id (Parameter Format on the AST)
     *
     * @param type Type of the Parameter
     * @param idNode AST Node containing the Parameter Identifier
     *
     * @throws SemanticException on Semantic Error
     */
    private void addParameter(Type type, Node idNode) throws SemanticException {
        final String symbolId = String.valueOf(idNode.jjtGetValue());

        if (symbols.containsKey(symbolId))
            throw new SemanticException(idNode, "Invalid parameter identifier (Conflicting Identifiers)");

        symbols.put(symbolId, type);
    }

    public Set<String> keySet() {
        return symbols.keySet();
    }
}
