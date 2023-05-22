package lader;

import sudoku.Sudoku;

import static data.SudokuZustand.Geladen;

/**
 * BeispielLader erweitert SokudoLader und implementiert die abstrakte Methode laden().
 * Läd das Beispiel-Sudoku aus der Aufgabenstellung in das Sudoku-Objekt: 's'.
 *
 * @author Thomas Wagner
 */
public class BeispielLader extends SudokuLader {
    /**
     * Läd das Beispiel-Sudoku aus der Aufgabenstellung in das Sudoku-Objekt: 's'.
     *
     * @param s das Sudoku-Objekt, in das das Beispiel-Sudoku geladen werden soll.
     */
    @Override
    public void laden(Sudoku s) {
        s.reset();
        // Zeile 0
        s.setWert(0, 1, 3);
        s.fixiere(0, 1);
        // Zeile 1
        s.setWert(1, 3, 1);
        s.fixiere(1, 3);
        s.setWert(1, 4, 9);
        s.fixiere(1, 4);
        s.setWert(1, 5, 5);
        s.fixiere(1, 5);
        // Zeile 2
        s.setWert(2, 2, 8);
        s.fixiere(2, 2);
        s.setWert(2, 7, 6);
        s.fixiere(2, 7);
        // Zeile 3
        s.setWert(3, 0, 8);
        s.fixiere(3, 0);
        s.setWert(3, 4, 6);
        s.fixiere(3, 4);
        // Zeile 4
        s.setWert(4, 0, 4);
        s.fixiere(4, 0);
        s.setWert(4, 3, 8);
        s.fixiere(4, 3);
        s.setWert(4, 8, 1);
        s.fixiere(4, 8);
        // Zeile 5
        s.setWert(5, 4, 2);
        s.fixiere(5, 4);
        // Zeile 6
        s.setWert(6, 1, 6);
        s.fixiere(6, 1);
        s.setWert(6, 6, 2);
        s.fixiere(6, 6);
        s.setWert(6, 7, 8);
        s.fixiere(6, 7);
        // Zeile 7
        s.setWert(7, 3, 4);
        s.fixiere(7, 3);
        s.setWert(7, 4, 1);
        s.fixiere(7, 4);
        s.setWert(7, 5, 9);
        s.fixiere(7, 5);
        s.setWert(7, 8, 5);
        s.fixiere(7, 8);
        // Zeile 8
        s.setWert(8, 7, 7);
        s.fixiere(8, 7);
        s.zustand = Geladen;
    }
}
