package exception;

/**
 * Eine Exception, die geworfen wird, wenn ein Wert in einer Spalte bereits vorhanden ist.
 */
public class WertInSpalteVorhandenException extends Exception {
    public WertInSpalteVorhandenException(String message) {
        super(message);
    }
}
