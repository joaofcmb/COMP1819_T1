import parser.Parser;
import parser.SimpleNode;
import semantic.SemanticException;
import semantic.ClassTable;

public class JMMCompiler {
    public static void main(String[] args) {
        // Lexical and Syntactical Analysis
        SimpleNode root = Parser.parse(args);
        if (root == null)   return;
        // root.dump("");

        // Semantic Analysis and generation of IR (Symbol Table + Intermediate Code)
        try {
            ClassTable ir = new ClassTable(root.jjtGetChild(0));
            System.out.println(ir);
        } catch (SemanticException e) {
            e.printStackTrace();
        }
    }
}
