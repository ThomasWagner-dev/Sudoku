package sudoku;

import data.Feld;
import lader.SudokuLader;

import static data.SudokuZustand.*;

/**
 * ProbierSudoku erwitert Sudoku und implementiert die Methode loesen().
 * Backtracking algorithmus gelöst wird.
 *
 * @author Thomas Wagner
 */
public class ProbierSudoku extends Sudoku {
    /**
     * Erstellt ein ProbierSudoku Objekt und initialisiert den Lösungsalgorithmus als 'lader'.
     *
     * @param lader der Lösungsalgorithmus
     */
    public ProbierSudoku(SudokuLader lader) {
        super(lader);
    }

    /**
     * Löst das Sudoku mit einem Backtracking algorithmus.
     */
    @Override
    public void loesen() {
        zustand = Loesungsversuch;
        if (loesenRec()) { // Lösung gefunden?
            zustand = Geloest;
            System.out.println("Lösung in " + schritte + " Schritten" + ":");
            anzeige.ausgeben();
        } else {
            zustand = Unloesbar;
            System.out.println("Keine Lösung gefunden.");
        }
    }

    private boolean loesenRec() {
        schritte++;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Feld feld = zeilen[i].getFeld(j);
                if (feld.getWert() == 0) { // data.Feld ist noch nicht gefüllt
                    for (int k = 1; k <= 9; k++) {
                        boolean erfolgreich = feld.setWert(k);
                        if (erfolgreich && loesenRec()) { // Rekursiver Aufruf, um das nächste data.Feld zu füllen
                            return true; // Lösung gefunden
                        }
                    }
                    feld.reset();
                    return false; // Keine Zahl passt in dieses data.Feld
                }
            }
        }
        return true; // Alle Felder sind gefüllt
    }
}
