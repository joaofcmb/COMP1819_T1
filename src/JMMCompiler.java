import parser.Parser;
import parser.SimpleNode;
import semantic.Semantic;
import semantic.StackIR;

public class JMMCompiler {
    public static void main(String[] args) {
        // Lexical and Syntactical Analysis
        SimpleNode root = Parser.parse(args);
        if (root == null)   return;
        // root.dump("");

        // Symbol Table and Semantic Analysis
        StackIR ir = Semantic.analysis(root);
        Semantic.printTable();
    }
}
