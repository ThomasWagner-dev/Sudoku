package exception;

/**
 * Eine Exception, die geworfen wird, wenn ein Wert außerhalb des gültigen Bereichs liegt.
 *
 */
public class WerteBereichUngueltigException extends Exception {

    public WerteBereichUngueltigException(String message) {
        super(message);
    }
}
