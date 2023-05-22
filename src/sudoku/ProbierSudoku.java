package sudoku;

import anzeige.ISudokuAnzeige;
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
    public ProbierSudoku(SudokuLader lader, ISudokuAnzeige anzeige) {
        super(lader, anzeige);
    }

    /**
     * Löst das Sudoku mit einem Backtracking algorithmus.
     */
    @Override
    public void loesen() {
        setZustand(Loesungsversuch);
        if (loesenRec()) { // Lösung gefunden?
            setZustand(Geloest);
            System.out.println("Lösung in " + schritte + " Schritten" + ":");
            anzeige.anzeigen();
        } else {
            setZustand(Unloesbar);
            if (schritte > 50000000)
                System.out.println("Zu viele Schritte.");
            else {
                System.out.println("Keine Lösung gefunden nach: " + schritte + " Schritten.");
            }
        }
    }

    private boolean loesenRec() {
        schritte++;
        if (schritte > 50000000) {
            return false;
        }
        if (schritte % 1000000 == 0) {
            System.out.println("Schritt: " + schritte);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Feld feld = zeilen[i].getFeld(j);
                if (feld.getWert() == 0) { // data.Feld ist noch nicht gefüllt
                    for (int k = 1; k <= 9; k++) {
                        boolean erfolgreich = true;
                        try {
                            setWert(feld.zeile.getNr(), feld.spalte.getNr(), k);
                        } catch (Exception e) {
                            erfolgreich = false;
                        }
                        if (erfolgreich && loesenRec()) { // Rekursiver Aufruf, um das nächste data.Feld zu füllen
                            return true; // Lösung gefunden
                        }
                        feld.reset();
                    }
                    return false; // Keine Zahl passt in dieses data.Feld
                }
            }
        }
        return true; // Alle Felder sind gefüllt
    }
}
