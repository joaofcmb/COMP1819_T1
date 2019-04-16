package semantic.symbol;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Type {
    private final static int STRINGARRAY = -1;

    private final static Map<Integer, String> stringMap;
    static {
        Map<Integer, String> tempMap = new HashMap<>();

        tempMap.put(ParserTreeConstants.JJTINT, "INT");
        tempMap.put(ParserTreeConstants.JJTINTARRAY, "INTARRAY");
        tempMap.put(ParserTreeConstants.JJTBOOLEAN, "BOOLEAN");
        tempMap.put(ParserTreeConstants.JJTID, "CLASS");
        tempMap.put(STRINGARRAY, "STRINGARRAY");

        stringMap = Collections.unmodifiableMap(tempMap);
    }

    private final int typeId;

    Type() {
        typeId = STRINGARRAY;
    }
    Type(Node typeNode) {
        typeId = typeNode.getId();
    }

    @Override
    public String toString() {
        return stringMap.get(typeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;
        return typeId == type.typeId;
    }
}
