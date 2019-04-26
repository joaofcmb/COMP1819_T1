package semantic;

import parser.Node;

import java.util.Arrays;

/**
 * Class containing a Method Signature, used to uniquely identify methods in a class
 *
 * The Method signature is, like in Java, composed of its Identifier and the Types of its Parameters, allowing,
 * therefore, for Method Overloading
 */
public class MethodSignature {
    private Type returnType;
    private final String methodId;
    private final Type[] parameterTypes;

    /**
     * Method Factory, creating a Method Signature from explicitly given Id and Parameter Types
     *
     * @param methodId Method Identifier
     * @param parameterTypes Method parameter Types
     *
     * @return Method Signature with the given parameters
     */
    static MethodSignature from(String methodId, Type[] parameterTypes) {
        return new MethodSignature(methodId, parameterTypes);
    }

    private MethodSignature(String methodId, Type[] parameterTypes) {
        this.returnType = null;
        this.methodId = methodId;
        this.parameterTypes = parameterTypes;
    }

    /**
     * Create a Method Signature of a Method
     *
     * @param methodNode AST Root containing a Method
     */
    MethodSignature(Node methodNode) {
        this.returnType = new Type(methodNode.jjtGetChild(0));

        this.methodId = String.valueOf(methodNode.jjtGetChild(1).jjtGetValue());

        Node parameterNode = methodNode.jjtGetChild(2);
        int parameterNum = parameterNode.jjtGetNumChildren() / 2;

        this.parameterTypes = new Type[parameterNum];
        for (int i = 0; i < parameterNum; i++)
            parameterTypes[i] = new Type(parameterNode.jjtGetChild(i*2));
    }

    // The Method Signature doesn't include the return type, therefore it is excluded from toString, hashCode and equals
    @Override
    public String toString() {
        return methodId + Arrays.toString(parameterTypes);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodSignature that = (MethodSignature) o;
        return methodId.equals(that.methodId) && Arrays.equals(parameterTypes, that.parameterTypes);
    }

    void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    Type getReturnType() {
        return returnType;
    }

    /**
     * @param classId Id of the class to which this method signature belongs to
     *
     * @return The Descriptor of the method with this method signature
     */
    public String toDescriptor(String classId) {
        StringBuilder sb = new StringBuilder((classId == null ? "" : classId + "/") + methodId + "(");

        for (Type parameter : parameterTypes)
            sb.append(parameter.toDescriptor());

        sb.append(")");

        if (returnType != null) sb.append(returnType.toDescriptor());

        return sb.toString();
    }
}
