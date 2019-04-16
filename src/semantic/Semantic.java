package semantic;

import parser.SimpleNode;
import semantic.symbol.ClassTable;

public class Semantic {
    private static ClassTable classTable;

    public static StackIR analysis(SimpleNode root) {
        try {
            classTable = new ClassTable(root.jjtGetChild(0));
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void printTable() {
        System.out.println(classTable);
    }
}
