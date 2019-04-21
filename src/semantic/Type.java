package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Type {
    private static final int STRINGARRAY = -1;
    private static final int UNKNOWN = -2;

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

    static Type INT() {
        return new Type(ParserTreeConstants.JJTINT);
    }

    static Type BOOLEAN() {
        return new Type(ParserTreeConstants.JJTBOOLEAN);
    }

    static Type INTARRAY() {
        return new Type(ParserTreeConstants.JJTINTARRAY);
    }

    static Type ID(String classIdentifier) {
        return new Type(ParserTreeConstants.JJTID, classIdentifier);
    }

    static Type UNKNOWN() {
        return new Type(UNKNOWN, "unknown");
    }

    private Type(int typeId) {
        this.typeId = typeId;
        this.typeName = stringMap.get(typeId);
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

    boolean isInt() { return typeId == UNKNOWN || typeId == ParserTreeConstants.JJTINT; }

    boolean isIntArray() {
        return typeId == UNKNOWN || typeId == ParserTreeConstants.JJTINTARRAY;
    }

    boolean isStringArray() {
        return typeId == UNKNOWN || typeId == STRINGARRAY;
    }

    public boolean isBoolean() {
        return typeId == UNKNOWN || typeId == ParserTreeConstants.JJTBOOLEAN;
    }

    public boolean isId() {
        return typeId == UNKNOWN || typeId == ParserTreeConstants.JJTID;
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
