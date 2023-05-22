package anzeige;

import sudoku.Sudoku;

public interface ISudokuAnzeige {
    Sudoku sudoku = null;

    void anzeigen();

    void setSudoku(Sudoku s);
}
