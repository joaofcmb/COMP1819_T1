package semantic;

import parser.Node;

import java.util.Arrays;

class MethodSignature {
    private final String methodId;
    private final Type[] parameterTypes;

    static MethodSignature from(String methodId, Type[] parameterTypes) {
        return new MethodSignature(methodId, parameterTypes);
    }

    private MethodSignature(String methodId, Type[] parameterTypes) {
        this.methodId = methodId;
        this.parameterTypes = parameterTypes;
    }

    MethodSignature(Node methodNode) {
        this.methodId = String.valueOf(methodNode.jjtGetChild(1).jjtGetValue());

        Node parameterNode = methodNode.jjtGetChild(2);
        int parameterNum = parameterNode.jjtGetNumChildren() / 2;

        this.parameterTypes = new Type[parameterNum];
        for (int i = 0; i < parameterNum; i++)
            parameterTypes[i] = new Type(parameterNode.jjtGetChild(i*2));
    }

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
}
