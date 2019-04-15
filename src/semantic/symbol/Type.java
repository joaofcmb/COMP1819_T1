package semantic.symbol;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Type {
    private final static Map<Integer, String> stringMap;
    static {
        Map<Integer, String> tempMap = new HashMap<>();

        tempMap.put(ParserTreeConstants.JJTINT, "INT");
        tempMap.put(ParserTreeConstants.JJTINTARRAY, "INTARRAY");
        tempMap.put(ParserTreeConstants.JJTBOOLEAN, "BOOLEAN");
        tempMap.put(ParserTreeConstants.JJTID, "CLASS");

        stringMap = Collections.unmodifiableMap(tempMap);
    }

    private final int typeId;

    Type(Node typeNode) {
        typeId = typeNode.getId();
    }

    @Override
    public String toString() {
        return stringMap.get(typeId);
    }
}
