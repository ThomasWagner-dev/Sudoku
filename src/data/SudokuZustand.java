package data;

/**
 * Ein Enum, das die verschiedenen Zustände eines Sudokus darstellt.
 * Leer: Das Sudoku wurde noch nicht geladen.
 * Geladen: Das Sudoku wurde geladen, aber noch nicht gelöst.
 * Loesungsversuch: Das Sudoku wird gerade gelöst.
 * Geloest: Das Sudoku wurde gelöst.
 * Unloesbar: Das Sudoku kann nicht gelöst werden.
 *
 * @author Thomas Wagner
 */
public enum SudokuZustand {
    Leer, Geladen, Loesungsversuch, Geloest, Unloesbar
}
