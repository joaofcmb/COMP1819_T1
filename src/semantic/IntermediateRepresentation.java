package semantic;

import parser.Node;
import parser.ParserTreeConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing an Intermediate Representation of the file(class) being compiled
 *
 * Upon creation, it creates a symbol table and performs semantic analysis and Intermediate Code generation
 * for each method.
 */
public class IntermediateRepresentation {
    /**
     *  The IR contains the id of the class and its parent (if it exists), as well as
     *  A SymbolTable for the class attributes and FunctionTables to its methods
     *
     * @see SymbolTable
     * @see MethodSignature
     * @see FunctionTable
     */
    private String classIdentifier;
    private String extendIdentifier;

    private final SymbolTable attributes = new SymbolTable();
    private final HashMap<MethodSignature, FunctionTable> methods = new HashMap<>();
    private FunctionTable mainMethod;

    /**
     * Constructor of the class, responsible for initializing the Tables and then perform Semantic analysis and
     * generate the Intermediate Code
     *
     * @param classRoot AST Node representing the root of the IR class
     *
     * @throws SemanticException on Semantic Error (Conflicting Symbols / Methods)
     */
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

        // Semantic Analysis and Intermediate Code generation
        if (mainMethod != null)     mainMethod.analyseAndGenerateBody();

        for (FunctionTable method : methods.values())
            method.analyseAndGenerateBody();
    }

    /**
     * Utility method during Semantic Analysis and ICode generation to check if a method belongs to the class itself
     *
     * @param classType Type of the object used to call the Method (class)
     * @param methodId Method Identifier (Not to be confused with its signature)
     * @param parameterTypes Types of the Method parameters
     *
     * @return Return Type of the method
     *
     * @throws SemanticException on Semantic Error (Invalid Class Type or Method not found)
     */
    Type checkMethod(Type classType, String methodId, Type[] parameterTypes) throws SemanticException {
        if (classType.equals(Type.ID(classIdentifier))) {
            MethodSignature methodSignature = MethodSignature.from(methodId, parameterTypes);
            if (methods.containsKey(methodSignature))
                return methods.get(methodSignature).getReturnType();
            else
                throw new SemanticException(); //TODO Complete Semantic Error (Method Signature not found)
        }

        if (!classType.isId())  throw new SemanticException(); //TODO Complete Semantic Error (Invalid Type for calling method)

        return Type.UNKNOWN(); // Null type means it's not meant to be considered for the analysis.
    }

    /**
     * @return Human readable format of the IR to be printed on the CLI
     */
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

    public String getClassIdentifier() {
        return classIdentifier;
    }

    public String getExtendIdentifier() {
        return extendIdentifier;
    }

    SymbolTable getAttributes() {
        return attributes;
    }

    public Map<MethodSignature, FunctionTable> getMethods() {
        return methods;
    }

    public FunctionTable getMain() {
        return mainMethod;
    }
}
