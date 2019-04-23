import parser.Parser;
import parser.SimpleNode;
import semantic.SemanticException;
import semantic.IntermediateRepresentation;

/*
 As cenas do parser (1º checkpoint), estao todas no package parser. O main foi alterado para aqui.

 No main são feitos vários steps, cada uma dentro dum package. O primeiro é o tal parser, a seguir vem o package
 semantic, q foi o q foi desenvolvido entretanto até agora. Neste step, a AST gerada é convertida numa representação
 intermédia (IR) que contém as Symbol Tables e o código intermédio para cada Método.

 Essencialmente, a estrutura da IR é a apresentada de seguida. A IR tem metódos toString para imprimir a sua estrutura,
 logo poderão ver esta estrutura ao rodar o programa usando o script jmm ou o q quiserem aplicado a um ficheiro a
 compilar.

     CLASSE X EXTENDE Y
        - LISTA DE ATRIBUTOS
     LISTA DE METODOS


     Cada metodo é algo deste género:

     ASSINATURA DO METODO (nome + tipo dos parametros)
        - TIPO DE RETORNO
        - LISTA DE PARAMETROS
        - LISTA DE INSTRUÇOES INTERMEDIAS (Codigo Intermedio)

 O código intermédio contém já na maioria instruções da JVM (Tudo o q e a minusculas e instrução da JVM).
 Antes da geração de código é preciso trocar as variáveis pelas variáveis locais da JVM, representadas por números.

 Quanto ao código, ele está documentado e usa da parte do código gerado o SimpleNode e o ParserTreeConstants.
 Recomendo que começem a olhar para a IntermediateRepresentation e vão daí para baixo (SymbolTable, MethodSignature,
 FunctionTable, IntermediateCode). A análise semântica é feita na FunctionTable, a geração de codigo intermedio no
 IntermediateTable. O Type e usado em ambos e serve como uma representacao dum tipo.
 */

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
