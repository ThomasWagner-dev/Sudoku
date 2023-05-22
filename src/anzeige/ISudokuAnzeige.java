package anzeige;

import sudoku.Sudoku;

public interface ISudokuAnzeige {
    Sudoku sudoku = null;

    /**
     * Zeigt das Sudoku an.
     */
    void anzeigen();

    /**
     * Setzt das Sudoku.
     * @param s das Sudoku.
     */
    void setSudoku(Sudoku s);
}
