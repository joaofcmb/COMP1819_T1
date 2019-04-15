package semantic.symbol;

import parser.Node;
import parser.ParserTreeConstants;
import semantic.SemanticException;

import java.util.HashMap;
import java.util.Map;

public class ClassTable {
    private String classIdentifier;
    private String extendIdentifier;

    private final SymbolTable attributes = new SymbolTable();
    private final HashMap<MethodSignature, MethodTable> methods = new HashMap<>();
    private MethodTable mainMethod;

    public ClassTable(Node root) throws SemanticException {
        int iterator = 0;

        this.classIdentifier = String.valueOf(root.jjtGetChild(iterator++).jjtGetValue());

        if (root.jjtGetChild(iterator).getId() == ParserTreeConstants.JJTID)
            this.extendIdentifier = String.valueOf(root.jjtGetChild(iterator++).jjtGetValue());

        while (iterator < root.jjtGetNumChildren()) {
            Node node = root.jjtGetChild(iterator++);

            switch(node.getId()) {
                case ParserTreeConstants.JJTVAR:
                    attributes.addSymbol(node);
                    break;
                case ParserTreeConstants.JJTMAIN:
                    if (mainMethod != null) throw new SemanticException(); //TODO Complete Semantic Error (Duplicate main)

                    mainMethod = new MethodTable(node);
                    break;
                case ParserTreeConstants.JJTMETHOD:
                    MethodSignature methodSignature = new MethodSignature(node);

                    if (methods.containsKey(methodSignature)) throw new SemanticException(); //TODO Complete Semantic Error (Method already exists)

                    methods.put(methodSignature, new MethodTable(node));
                    break;
            }
        }
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
}
