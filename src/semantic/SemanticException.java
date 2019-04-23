package semantic;

/**
 * Exception thrown when a Semantic Error occurs.
 *
 * TODO Still under construction, it'll be extended to allow for printing specific errors
 */
public class SemanticException extends Exception {
    SemanticException() {}
    SemanticException(String message) {
        super(message);
    }
}
