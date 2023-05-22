package exception;

/**
 * Eine Exception, die geworfen wird, wenn ein Wert in einem Quadranten bereits vorhanden ist.
 */
public class WertInQuadrantVorhandenException extends Exception {
    public WertInQuadrantVorhandenException(String message) {
        super(message);
    }
}
