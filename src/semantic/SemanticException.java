package semantic;

import parser.Node;

/**
 * Exception thrown when a Semantic Error occurs.
 *
 * TODO Still under construction, it'll be extended to allow for localizing errors
 */
public class SemanticException extends Exception {
    SemanticException() {}
    SemanticException(Node node, String message) {
        super("Semantic error: " + message + " at line " + node.jjtGetFirstToken().beginLine
                + ", column " + node.jjtGetFirstToken().beginColumn);
    }
}
