import parser.Parser;
import parser.SimpleNode;
import semantic.SemanticException;
import semantic.IntermediateRepresentation;

/**
 * Main class which goes through all the steps of the compiler
 */
public class JMMCompiler {
    /**
     * Main method which goes through all the steps of the compiler
     *
     * @param args Contains one command line argument, corresponding to the path of the file to compile
     */
    public static void main(String[] args) {
        // Lexical and Syntactical Analysis
        SimpleNode root = Parser.parse(args);
        if (root == null)   return;
        //root.dump("");

        // Semantic Analysis and generation of HIR (Symbol Table + Intermediate HL Code)
        try {
            IntermediateRepresentation ir = new IntermediateRepresentation(root.jjtGetChild(0));
            System.out.println(ir);
        } catch (SemanticException e) {
            e.printStackTrace();
        }

        // TODO Register Alocation (Naive for now) and Code Generation (Just functions and arithmetic for now)
    }
}
