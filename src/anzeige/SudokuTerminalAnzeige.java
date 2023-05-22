package anzeige;

import sudoku.Sudoku;

/**
 * SudokuAnzeige übernimmt die Ausgabe des Sudokus auf der Konsole.
 *
 * @author Thomas Wagner
 */
public class SudokuTerminalAnzeige implements ISudokuAnzeige {
    /**
     * Referenz auf das Sudoku, welches ausgegeben werden soll.
     */
    private Sudoku sudoku;
    /**
     * Gibt das Sudoku auf der Konsole aus.
     * Die Ausgabe funktioniert nur, wenn das Sudoku vorher mit setSudoku(Sudoku s) gesetzt wurde.
     */
    public void anzeigen() {
        if (sudoku == null) {
            System.out.println("Kein Sudoku gesetzt!");
            return;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku.zeilen[i].getFeld(j).getWert() + " ");
            }
            System.out.println();
        }
    }

    @Override
    public void setSudoku(Sudoku s) {
        this.sudoku = s;
    }
}
