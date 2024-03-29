package semantic;

import parser.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class containing Symbols, mapping their Identifier to their corresponding Type
 *
 * @see Type
 */
public class SymbolTable {
    final HashMap<String, Type> symbols;

    SymbolTable() {
        symbols = new HashMap<>();
    }

    SymbolTable(HashMap<String, Type> symbols) {
        this.symbols = symbols;
    }

    /**
     * Adds a Symbol to the table from a given Declaration Node
     *
     * @param declarationNode Declaration Node of the AST
     *
     * @throws SemanticException on Semantic Error (Conflicting Identifiers)
     */
    void addDeclaration(Node declarationNode) throws SemanticException {
        final String symbolId = String.valueOf(declarationNode.jjtGetChild(1).jjtGetValue());

        if (symbols.containsKey(symbolId))
            throw new SemanticException(declarationNode.jjtGetChild(1), "Invalid Identifier (Conflicting Identifiers)");

        symbols.put(symbolId, new Type(declarationNode.jjtGetChild(0)));
    }

    /**
     * Checks if the table contains a Symbol with the given Identifier
     *
     * @param idNode AST Node containing the Identifier
     *
     * @return Boolean reprsenting whether the Identifier already exists or not on this table
     */
    boolean containsId(Node idNode) {
        return symbols.containsKey(String.valueOf(idNode.jjtGetValue()));
    }

    /**
     * Retrieves a Symbol Type given its Identifier
     *
     * @param idNode AST Node containing the Identifier
     *
     * @return Type of that Symbol
     */
    Type getId(Node idNode) {
        return symbols.get(String.valueOf(idNode.jjtGetValue()));
    }

    /**
     * Sets the Type of a given Symbol
     *
     * @param idNode AST Node containing the Identifier
     * @param newType New Type of that Symbol
     */
    void setId(Node idNode, Type newType) {
        symbols.put(String.valueOf(idNode.jjtGetValue()), newType);
    }

    public Set<Map.Entry<String, Type>> entrySet() {
        return symbols.entrySet();
    }

    /**
     * @return Human readable format of the Symbol Table to be printed on the CLI
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Type> symbol : symbols.entrySet()) {
            sb.append("   - ")
                    .append(symbol.getValue().toString())
                    .append(" ")
                    .append(symbol.getKey())
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
