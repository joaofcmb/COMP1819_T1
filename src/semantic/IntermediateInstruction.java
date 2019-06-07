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
public class IntermediateInstruction {
    private static final int ISTORE = -1;
    private static final int ASTORE = -2;

    private static final int IASTORE = -3;
    private static final int AASTORE = -4;

    private static final int GETFIELD = -5;
    private static final int PUTFIELD = -6;

    private static final int ILOAD = -7;
    private static final int ALOAD = -8;

    private static final int IALOAD = -9;
    private static final int AALOAD = -10;

    private static final int INVOKEVIRTUAL = -11;
    private static final int INVOKESTATIC  = -12;
    private static final int POP = -13;

    private static final int IRETURN = -14;
    private static final int ARETURN = -15;

    private static final int IFEQ = -16;
    private static final int IFGT = -17;
    private static final int IFCMPGE = -18;
    private static final int IFCMPLT = -19;
    private static final int GOTO = -20;

    private static final int BIPUSH = -21;
    private static final int SIPUSH = -22;
    private static final int LDC = -23;


    private final static Map<Integer, String> stringMap;

    static {
        Map<Integer, String> tempMap = new HashMap<>();

        // LOAD
        tempMap.put(ILOAD,                              "iload");
        tempMap.put(ALOAD,                              "aload");
        tempMap.put(IALOAD,                             "iaload");
        tempMap.put(AALOAD,                             "aaload");
        tempMap.put(ParserTreeConstants.JJTTHIS,        "aload_0");

        // CONSTANT LOADS
        tempMap.put(BIPUSH,     "bipush");
        tempMap.put(SIPUSH,     "sipush");
        tempMap.put(LDC,     "ldc");
        tempMap.put(ParserTreeConstants.JJTTRUE,        "iconst_1");
        tempMap.put(ParserTreeConstants.JJTFALSE,       "iconst_0");

        // STORE and RETURN
        tempMap.put(ISTORE,                             "istore");
        tempMap.put(ASTORE,                             "astore");
        tempMap.put(IASTORE,                            "iastore");
        tempMap.put(AASTORE,                            "aastore");
        tempMap.put(IRETURN,                            "ireturn");
        tempMap.put(ARETURN,                            "areturn");

        // FIELDS and FCALL
        tempMap.put(GETFIELD,                           "getfield");
        tempMap.put(PUTFIELD,                           "putfield");
        tempMap.put(INVOKEVIRTUAL,                      "invokevirtual");
        tempMap.put(INVOKESTATIC,                       "invokestatic");
        tempMap.put(POP,                                "pop");

        // NEW and LENGTH
        tempMap.put(ParserTreeConstants.JJTNEWARRAY,    "newarray int");
        tempMap.put(ParserTreeConstants.JJTNEWOBJ,      "new");
        tempMap.put(ParserTreeConstants.JJTLENGTH,      "arraylength");

        // ARITHMETIC
        tempMap.put(ParserTreeConstants.JJTPLUS,        "iadd");
        tempMap.put(ParserTreeConstants.JJTMINUS,       "isub");
        tempMap.put(ParserTreeConstants.JJTTIMES,       "imul");
        tempMap.put(ParserTreeConstants.JJTDIVIDE,      "idiv");

        // CONDITIONS
        tempMap.put(IFEQ,                               "ifeq");
        tempMap.put(IFGT,                               "ifgt");
        tempMap.put(IFCMPGE,                            "if_icmpge");
        tempMap.put(IFCMPLT,                            "if_icmplt");
        tempMap.put(GOTO,                               "goto");

        stringMap = Collections.unmodifiableMap(tempMap);
    }

    private final int instructionId;
    private String value;

    static IntermediateInstruction GOTO(int labelNum) {
        return new IntermediateInstruction(GOTO, "L" + labelNum);
    }

    static IntermediateInstruction LABEL(int labelNum) {
        return new IntermediateInstruction(0,"L" + labelNum + ":");
    }

    static IntermediateInstruction POP() {
        return new IntermediateInstruction(POP);
    }

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
        if (instructionId == ParserTreeConstants.JJTINTEGER) {
            final int intValue = Integer.parseInt(value);

            if (intValue >= -128 && intValue <= 127)            this.instructionId = BIPUSH;
            else if (intValue >= -32768 && intValue <= 32767)   this.instructionId = SIPUSH;
            else                                                this.instructionId = LDC;
        }
        else {
            this.instructionId = instructionId;
        }
        this.value = value;
    }

    /**
     * Creates a If Instruction
     * @param instructionId Instruction Identifier
     * @param eval Evaluation being performed (Condition must be true or false)
     * @param labelNum Label Identifier Number
     */
    IntermediateInstruction(int instructionId, boolean eval, int labelNum) {
        if (instructionId == ParserTreeConstants.JJTLOWER) {
            this.instructionId = eval ? IFCMPGE : IFCMPLT;
        } else {
            this.instructionId = eval ? IFEQ : IFGT;
        }
        this.value = "L" + labelNum;
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
     * Creates a Type specific Intermediate Instruction with an explicit parameter
     *
     * @param instructionId Instruction Identifier
     * @param value Custom value (parameters) for the instruction
     * @param type Desired Type
     */
    IntermediateInstruction(int instructionId, String value, Type type) {
        switch (instructionId) {
            case ParserTreeConstants.JJTASSIGN:
                if (value == null) {
                    if (type.isInt())                           this.instructionId = IASTORE;
                    else                                        this.instructionId = AASTORE;
                }
                else {
                    if (value.contains("/")) {
                        this.instructionId = PUTFIELD;
                        value += " " + type.toDescriptor();
                    }
                    else if (type.isInt() || type.isBoolean())  this.instructionId = ISTORE;
                    else                                        this.instructionId = ASTORE;
                }
                break;
            case ParserTreeConstants.JJTID:
                if (value.contains("/")) {
                    this.instructionId = GETFIELD;
                    value += " " + type.toDescriptor();
                }
                else if (type.isInt() || type.isBoolean())      this.instructionId = ILOAD;
                else                                            this.instructionId = ALOAD;
                break;
            case ParserTreeConstants.JJTINDEX:
                if (type.isInt())                               this.instructionId = IALOAD;
                else                                            this.instructionId = AALOAD;
                break;
            case ParserTreeConstants.JJTRETURN:
                if (type.isInt() || type.isBoolean())           this.instructionId = IRETURN;
                else                                            this.instructionId = ARETURN;
                break;
            default:
                this.instructionId = instructionId;
        }

        this.value = value;
    }

    /**
     * Creates a Function Call Instruction
     *
     * @param classType Type of the class
     * @param value Instruction Parameter
     */
    IntermediateInstruction(Type classType, String value) {
        if (classType.isId())   this.instructionId = INVOKEVIRTUAL;
        else                    this.instructionId = INVOKESTATIC;

        this.value = value;
    }

    /**
     * @return Whether the Intermediate Instruction is using a local variable or not (Loads and Stores)
     */
    public boolean isLocal() {
        return isLoad() || isStore();
    }

    public boolean isLoad() {
        return instructionId == ILOAD || instructionId == ALOAD;
    }

    public boolean isStore() {
        return instructionId == ISTORE || instructionId == ASTORE;
    }

    public boolean isGoto() {
        return instructionId == GOTO;
    }

    public boolean isBranch() {
        return instructionId == IFCMPGE || instructionId == IFCMPLT || instructionId == IFEQ || instructionId == IFGT;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return this.instructionId == 0 ? this.value.substring(0, this.value.length() - 1) : null;
    }

    public boolean getRegisterType() {
        return (instructionId == ILOAD || instructionId == ISTORE);
    }

    public void setVarNum(int value) {
        this.value = String.valueOf(value);
    }

    @Override
    public String toString() {
        return (stringMap.containsKey(instructionId) ? stringMap.get(instructionId) + " " : "")
                + (value == null ? "" : value);
    }

    public int stackSlots() {
        switch(instructionId) {
            case ISTORE:
            case ASTORE:
            case IRETURN:
            case ARETURN:
            case PUTFIELD:
            case IALOAD:
            case AALOAD:
            case IFEQ:
            case IFGT:
            case ParserTreeConstants.JJTINDEX:
            case ParserTreeConstants.JJTPLUS:
            case ParserTreeConstants.JJTMINUS:
            case ParserTreeConstants.JJTTIMES:
            case ParserTreeConstants.JJTDIVIDE:
                return -1;
            case ILOAD:
            case ALOAD:
            case GETFIELD:
            case BIPUSH:
            case SIPUSH:
            case LDC:
            case ParserTreeConstants.JJTTRUE:
            case ParserTreeConstants.JJTFALSE:
            case ParserTreeConstants.JJTTHIS:
            case ParserTreeConstants.JJTNEWOBJ:
                return 1;
            case IFCMPLT:
            case IFCMPGE:
                return -2;
            case IASTORE:
            case AASTORE:
                return -3;
            case INVOKESTATIC:
            case INVOKEVIRTUAL:
                int stackSlots = 1;
                if (value.endsWith("V"))    stackSlots--;

                String[] splitValue = value.split("[()]");

                if (splitValue.length > 1) {
                    final String parameters = splitValue[1];
                    for (int i = 0; i < parameters.length(); i++) {
                        final char c = parameters.charAt(i);

                        if (c == '[') continue;
                        else if (c == 'L') {
                            while (parameters.charAt(++i) != ';') ;
                        }

                        stackSlots--;
                    }
                }

                return stackSlots;
            default:
                return 0;
        }
    }
}
