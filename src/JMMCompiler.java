import parser.SimpleNode;
import semantic.Semantic;
import semantic.StackIR;

public class JMMCompiler {
    public static void main(String[] args) {
        // Lexical and Syntactical Analysis
        SimpleNode root = parser.Parser.main(args);
        if (root == null)   return;

        // Symbol Table and Semantic Analysis
        StackIR ir = Semantic.analysis(root);

    }
}
