package semantic;

import parser.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing Symbols, mapping their Identifier to their corresponding Type
 *
 * @see Type
 */
class SymbolTable {
    private final HashMap<String, Type> symbols = new HashMap<>();

    /**
     * Adds a Symbol to the table from a given Declaration Node
     *
     * @param declarationNode Declaration Node of the AST
     *
     * @throws SemanticException on Semantic Error (Conflicting Identifiers)
     */
    void addDeclaration(Node declarationNode) throws SemanticException {
        final String symbolId = String.valueOf(declarationNode.jjtGetChild(1).jjtGetValue());

        // TODO Complete Semantic Error (Variable/Attribute already exists)
        if (symbols.containsKey(symbolId))  throw new SemanticException();

        symbols.put(symbolId, new Type(declarationNode.jjtGetChild(0)));
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

        // TODO Complete Semantic Error (Variable/Attribute already exists)
        if (symbols.containsKey(symbolId))  throw new SemanticException();

        symbols.put(symbolId, type);
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
    Type getId(Node idNode) {
        return symbols.get(String.valueOf(idNode.jjtGetValue()));
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
