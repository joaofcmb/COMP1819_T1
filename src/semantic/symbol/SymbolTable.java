package semantic.symbol;

import parser.Node;
import semantic.SemanticException;

import java.util.HashMap;
import java.util.Map;

class SymbolTable {
    private final HashMap<String, Type> symbols = new HashMap<>();

    void addDeclaration(Node declarationNode) throws SemanticException {
        final String symbolId = String.valueOf(declarationNode.jjtGetChild(1).jjtGetValue());

        // TODO Complete Semantic Error (Variable/Attribute already exists)
        if (symbols.containsKey(symbolId))  throw new SemanticException();

        symbols.put(symbolId, new Type(declarationNode.jjtGetChild(0)));
    }

    void addParameter(Node idNode) throws SemanticException {
        addParameter(new Type(), idNode);
    }

    void addParameter(Node typeNode, Node idNode) throws SemanticException {
        addParameter(new Type(typeNode), idNode);
    }

    private void addParameter(Type type, Node idNode) throws SemanticException {
        final String symbolId = String.valueOf(idNode.jjtGetValue());

        // TODO Complete Semantic Error (Variable/Attribute already exists)
        if (symbols.containsKey(symbolId))  throw new SemanticException();

        symbols.put(symbolId, type);
    }

    boolean containsId(Node idNode) {
        return symbols.containsKey(String.valueOf(idNode.jjtGetValue()));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Type> symbol : symbols.entrySet()) {
            sb.append("  ")
                    .append(symbol.getValue().toString())
                    .append(" ")
                    .append(symbol.getKey())
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
