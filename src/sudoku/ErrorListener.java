package sudoku;

/**
 * Ein Interface, das das Auftreten von Fehlern beschreibt.
 */
public interface ErrorListener {
    void exceptionOccurred(Exception x);
}
