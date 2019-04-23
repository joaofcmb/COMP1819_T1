package semantic;

import parser.ParserTreeConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an Intermediate Instruction. It is Identified using the Constants defined in the Parser, as well
 * as by some extra Identifiers where needed.
 *
 * @see IntermediateRepresentation
 * @see ParserTreeConstants
 */
class IntermediateInstruction {
    private static final int ISTORE = -1;
    private static final int ASTORE = -2;

    private static final int ILOAD = -3;
    private static final int ALOAD = -4;

    private static final int IRETURN = -5;
    private static final int ARETURN = -6;

    private final static Map<Integer, String> stringMap;

    static {
        Map<Integer, String> tempMap = new HashMap<>();

        // LOAD
        tempMap.put(ILOAD,                              "iload");
        tempMap.put(ALOAD,                              "aload");
        tempMap.put(ParserTreeConstants.JJTINDEX,       "ARRAYLOAD");
        tempMap.put(ParserTreeConstants.JJTINTEGER,     "ldc");

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

    /**
     * Creates an Intermediate Instruction without any explicit parameter
     *
     * @param instructionId Instruction Identifier
     */
    IntermediateInstruction(int instructionId) {
        this.instructionId = instructionId;
        this.value = null;
    }

    /**
     * Creates an Intermediate Instruction with an explicit parameter
     *
     * @param instructionId Instruction Identifier
     * @param value Instruction Parameter
     */
    IntermediateInstruction(int instructionId, String value) {
        this.instructionId = instructionId;
        this.value = value;
    }

    /**
     * Creates a Type specific Intermediate Instruction without any explicit paramter
     *
     * @param instructionId Instruction Identifier
     * @param type Desired Type
     */
    IntermediateInstruction(int instructionId, Type type) {
        this(instructionId, null, type);
    }

    /**
     * Creates a Type specific Intermediate Instruction with an explicit paramter
     *
     * @param instructionId Instruction Identifier
     * @param type Desired Type
     */
    IntermediateInstruction(int instructionId, String value, Type type) {
        switch (instructionId) {
            case ParserTreeConstants.JJTASSIGN:
                if (type.isInt() || type.isBoolean())   this.instructionId = ISTORE;
                else                                    this.instructionId = ASTORE;
                break;
            case ParserTreeConstants.JJTID:
                if (type.isInt() || type.isBoolean())   this.instructionId = ILOAD;
                else                                    this.instructionId = ALOAD;
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
