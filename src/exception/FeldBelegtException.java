package exception;

/**
 * Eine Exception, die geworfen wird, wenn ein Feld bereits belegt ist.
 */
public class FeldBelegtException extends Exception {
    public FeldBelegtException(String message) {
        super(message);
    }
}
