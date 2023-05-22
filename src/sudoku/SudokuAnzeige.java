package sudoku;

/**
 * SudokuAnzeige übernimmt die Ausgabe des Sudokus auf der Konsole.
 *
 * @author Thomas Wagner
 */
public class SudokuAnzeige {
    /**
     * Referenz auf das Sudoku, welches ausgegeben werden soll.
     */
    private final Sudoku sudoku;

    /**
     * Konstruktor, der die Referenz auf das Sudoku übernimmt.
     *
     * @param sudoku Referenz auf das Sudoku, welches ausgegeben werden soll.
     */
    public SudokuAnzeige(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Gibt das Sudoku auf der Konsole aus.
     */
    public void ausgeben() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku.zeilen[i].getFeld(j).getWert() + " ");
            }
            System.out.println();
        }
    }
}
