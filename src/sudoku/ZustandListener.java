package sudoku;

import data.SudokuZustand;

/**
 * Ein Interface, für alle, die am Zustand des Sudokus interessiert sind.
 */
public interface ZustandListener {
    void zustandChanged(SudokuZustand zustand);
}
