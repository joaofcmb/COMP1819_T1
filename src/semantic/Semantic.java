package semantic;

import parser.SimpleNode;
import semantic.symbol.ClassTable;

public class Semantic {
    private static ClassTable classTable;

    public static StackIR analysis(SimpleNode root) {
        try {
            classTable = new ClassTable(root.jjtGetChild(0));
            System.out.println(classTable);
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        return null;
    }
}
