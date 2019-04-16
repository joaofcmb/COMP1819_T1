package semantic;

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

        stringMap = Collections.unmodifiableMap(tempMap);
    }

    private final int typeId;
    private final String typeName;

    static Type idType(String classIdentfier) {
        return new Type(ParserTreeConstants.JJTID, classIdentfier);
    }

    private Type(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    Type() {
        this(STRINGARRAY,"STRINGARRAY");
    }

    Type(Node typeNode) {
        typeId = typeNode.getId();
        typeName = stringMap.containsKey(typeId) ? stringMap.get(typeId) : String.valueOf(typeNode.jjtGetValue());
    }

    @Override
    public String toString() {
        return typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;
        return typeId == type.typeId && typeName.equals(type.typeName);
    }
}
