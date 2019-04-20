package semantic;

import parser.ParserTreeConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class IntermediateInstruction {
    private static final int ISTORE = -1;
    private static final int ASTORE = -2;

    private static final int IRETURN = -3;
    private static final int ARETURN = -4;

    private final static Map<Integer, String> stringMap;

    static {
        Map<Integer, String> tempMap = new HashMap<>();

        // LOAD
        tempMap.put(ParserTreeConstants.JJTINTEGER,     "ldc");
        tempMap.put(ParserTreeConstants.JJTID,          "LOAD");
        tempMap.put(ParserTreeConstants.JJTINDEX,       "ARRAYLOAD");

        // STORE AND RETURN
        tempMap.put(ISTORE,                             "istore");
        tempMap.put(ASTORE,                             "astore");
        tempMap.put(IRETURN,                            "ireturn");
        tempMap.put(ARETURN,                            "areturn");

        // NEW, FCALL and LENGTH
        tempMap.put(ParserTreeConstants.JJTNEWARRAY,    "newarray int");
        tempMap.put(ParserTreeConstants.JJTNEWOBJ,      "new");
        tempMap.put(ParserTreeConstants.JJTFCALL,       "invokevirtual");
        tempMap.put(ParserTreeConstants.JJTLENGTH,      "arraylength");

        // ARITHMETIC
        tempMap.put(ParserTreeConstants.JJTPLUS,        "iadd");
        tempMap.put(ParserTreeConstants.JJTMINUS,       "isub");
        tempMap.put(ParserTreeConstants.JJTTIMES,       "imul");
        tempMap.put(ParserTreeConstants.JJTDIVIDE,      "idiv");

        stringMap = Collections.unmodifiableMap(tempMap);
    }

    private final int instructionId;
    private final String value;

    IntermediateInstruction(int instructionId) {
        this.instructionId = instructionId;
        this.value = null;
    }

    IntermediateInstruction(int instructionId, String value) {
        this.instructionId = instructionId;
        this.value = value;
    }

    IntermediateInstruction(int instructionId, String[] values) {
        this.instructionId = instructionId;
        this.value = String.join("|", values);
    }

    IntermediateInstruction(int instructionId, Type type) {
        this(instructionId, null, type);
    }

    IntermediateInstruction(int instructionId, String value, Type type) {
        switch (instructionId) {
            case ParserTreeConstants.JJTASSIGN:
                if (type.isInt() || type.isBoolean())   this.instructionId = ISTORE;
                else                                    this.instructionId = ASTORE;
                break;
            case ParserTreeConstants.JJTRETURN:
                if (type.isInt() || type.isBoolean())   this.instructionId = IRETURN;
                else                                    this.instructionId = ARETURN;
                break;
            default:
                this.instructionId = instructionId;
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return stringMap.get(instructionId) + " " + (value == null ? "" : value);
    }
}
