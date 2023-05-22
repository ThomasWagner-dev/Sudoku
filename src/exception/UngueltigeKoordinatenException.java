package exception;

/**
 * EineException, die geworfen wird, wenn die Koordinaten eines Feldes ungültig sind.
 */
public class UngueltigeKoordinatenException extends Exception {
    public UngueltigeKoordinatenException(String message) {
        super(message);
    }
}
