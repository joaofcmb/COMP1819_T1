package semantic.symbol;

import parser.Node;
import semantic.SemanticException;

import java.util.HashMap;
import java.util.Map;

class SymbolTable {
    private final HashMap<String, Type> symbols = new HashMap<>();

    void addSymbol(Node symbolNode) throws SemanticException {
        final String symbolId = String.valueOf(symbolNode.jjtGetChild(1).jjtGetValue());

        if (symbols.containsKey(symbolId))  throw new SemanticException(); //TODO Complete Semantic Error (Variable/Attribute already exists)

        symbols.put(symbolId, new Type(symbolNode.jjtGetChild(0)));
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
