package sudoku;

import data.Feld;
import lader.SudokuLader;

import java.util.ArrayList;

import static data.SudokuZustand.*;

/**
 * ZufallSudoku erweitert Sudoku und implementiert die abstrakte Methode loesen().
 * Löst das Sudoku-Objekt: 's' mit einem Zufallsalgorithmus.
 *
 * @author Thomas Wagner
 */
public class ZufallSudoku extends Sudoku {
    /**
     * Konstruktor, der ein Sudoku-Objekt: 's' erzeugt und mit dem lader 'lader' lädt.
     *
     * @param lader der lader, der das Sudoku-Objekt: 's' lädt.
     */
    public ZufallSudoku(SudokuLader lader) {
        super(lader);
    }

    /**
     * Löst das Sudoku-Objekt: 's' mit einem Zufallsalgorithmus.
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
            anzeige.ausgeben();
            System.out.println("Keine Lösung gefunden.");
        }
    }

    private boolean loesenRec() {
        schritte++;
        if (istGeloest()) {
            return true;
        }

        int i, j;
        do {
            i = (int) (Math.random() * 9);
            j = (int) (Math.random() * 9);
        } while (zeilen[i].getFeld(j).getWert() != 0);
        Feld feld = zeilen[i].getFeld(j);
        boolean erfolgreich;
        int k;
        ArrayList<Integer> ausProbierteZahlen = new ArrayList<>();
        do {
            do {
                k = (int) (Math.random() * 9 + 1);
            } while (ausProbierteZahlen.contains(k));
            erfolgreich = setWert(i, j, k);
            ausProbierteZahlen.add(k);
            if (erfolgreich && loesenRec()) { // Rekursiver Aufruf, um das nächste data.Feld zu füllen
                return true; // Lösung gefunden
            }
        } while (ausProbierteZahlen.size() < 9);
        feld.reset();
        return false;
    }
}

