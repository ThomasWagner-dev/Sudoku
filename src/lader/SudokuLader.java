package lader;

import sudoku.Sudoku;

/**
 * Abstrakte Klasse, die das Laden eines Sudokus in ein Sudoku-Objekt beschreibt.
 *
 * @author Thomas Wagner
 */
public abstract class SudokuLader {

    /**
     * Beschreibt das Laden eines Sudokus in ein Sudoku-Objekt.
     *
     * @param s das Sudoku-Objekt, in das das Sudoku geladen werden soll.
     */
    public abstract void laden(Sudoku s);
}
