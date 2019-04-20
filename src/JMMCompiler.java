import parser.Parser;
import parser.SimpleNode;
import semantic.SemanticException;
import semantic.IntermediateRepresentation;

public class JMMCompiler {
    public static void main(String[] args) {
        // Lexical and Syntactical Analysis
        SimpleNode root = Parser.parse(args);
        if (root == null)   return;
        // root.dump("");

        // Semantic Analysis and generation of HIR (Symbol Table + Intermediate HL Code)
        try {
            IntermediateRepresentation ir = new IntermediateRepresentation(root.jjtGetChild(0));
            System.out.println(ir);
        } catch (SemanticException e) {
            e.printStackTrace();
        }


    }
}
