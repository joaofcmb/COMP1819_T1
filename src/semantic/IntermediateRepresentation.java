package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IntermediateRepresentation {
    private String classIdentifier;
    private String extendIdentifier;

    private final SymbolTable attributes = new SymbolTable();
    private final HashMap<MethodSignature, FunctionTable> methods = new HashMap<>();
    private FunctionTable mainMethod;

    public IntermediateRepresentation(Node classRoot) throws SemanticException {
        int iterator = 0;

        this.classIdentifier = String.valueOf(classRoot.jjtGetChild(iterator++).jjtGetValue());

        if (classRoot.jjtGetChild(iterator).getId() == ParserTreeConstants.JJTID)
            this.extendIdentifier = String.valueOf(classRoot.jjtGetChild(iterator++).jjtGetValue());

        while (iterator < classRoot.jjtGetNumChildren()) {
            Node node = classRoot.jjtGetChild(iterator++);

            switch(node.getId()) {
                case ParserTreeConstants.JJTVAR:
                    attributes.addDeclaration(node);
                    break;
                case ParserTreeConstants.JJTMAIN:
                    //TODO Complete Semantic Error (Duplicate main)
                    if (mainMethod != null) throw new SemanticException();

                    mainMethod = new MainTable(node, this);
                    break;
                case ParserTreeConstants.JJTMETHOD:
                    MethodSignature methodSignature = new MethodSignature(node);

                    //TODO Complete Semantic Error (Method Signature already exists)
                    if (methods.containsKey(methodSignature)) throw new SemanticException();

                    methods.put(methodSignature, new MethodTable(node, this));
                    break;
            }
        }

        if (mainMethod != null)     mainMethod.analyseBody();

        for (FunctionTable method : methods.values())
            method.analyseBody();
    }

    Type checkMethod(Type classType, String methodId, Type[] parameterTypes) throws SemanticException {
        if (classType.equals(Type.ID(classIdentifier))) {
            MethodSignature methodSignature = MethodSignature.from(methodId, parameterTypes);
            if (methods.containsKey(methodSignature))
                return methods.get(methodSignature).getReturnType();
            else
                throw new SemanticException(); //TODO Complete Semantic Error (Method Signature not found)
        }

        System.out.println("Fudeu: " + methodId);
        if (!classType.isId())  throw new SemanticException(); //TODO Complete Semantic Error (Invalid Type for calling method)

        return null; // Null type means it's not meant to be considered for the analysis.
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(classIdentifier);

        if (extendIdentifier != null)
            sb.append(" extends ").append(extendIdentifier);

        sb.append(System.lineSeparator())
                .append(attributes.toString());

        if (mainMethod != null)
            sb.append(System.lineSeparator())
                    .append("main[STRINGARRAY]:")
                    .append(System.lineSeparator())
                    .append(mainMethod.toString());

        for (Map.Entry method : methods.entrySet()) {
            sb.append(System.lineSeparator())
                    .append(method.getKey().toString()).append(":")
                    .append(System.lineSeparator())
                    .append(method.getValue().toString());
        }

        return sb.toString();
    }

    String getIdentifier() {
        return classIdentifier;
    }

    SymbolTable getAttributes() {
        return attributes;
    }
}
