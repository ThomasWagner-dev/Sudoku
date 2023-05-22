package exception;

/**
 * Eine Exception, die geworfen wird, wenn ein Wert in einem Quadranten bereits vorhanden ist.
 */
public class WertInZeileVorhandenException extends Exception {
    public WertInZeileVorhandenException(String message) {
        super(message);
    }
}
