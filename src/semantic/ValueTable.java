package semantic;

import parser.Node;

import java.util.HashMap;

class ValueTable extends SymbolTable {
    private final HashMap<String, Integer> values = new HashMap<>();

    /**
     * Checks if a given symbol contains a value
     *
     * @param idNode AST Node containing the Identifier
     *
     * @return Boolean representing whether the given symbol has a value or not
     */
    boolean hasValue(Node idNode) {
        return values.get(String.valueOf(idNode.jjtGetValue())) != null;
    }

    /**
     * Sets the value of a given symbol
     *
     * @param idNode AST Node containing the Identifier
     * @param value Integer value of that symbol
     */
    void putValue(Node idNode, Integer value) {
        values.put(String.valueOf(idNode.jjtGetValue()), value);
    }

    /**
     * Retrieves the value of a given symbol
     *
     * @param idNode AST Node containing the Identifier
     *
     * @return Integer value of that symbol
     */
    Integer getValue(Node idNode) {
        return values.get(String.valueOf(idNode.jjtGetValue()));
    }

}
