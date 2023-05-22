package sudoku;

import data.Feld;
import data.Feldgruppe;
import lader.SudokuLader;

import java.util.ArrayList;

import static data.SudokuZustand.*;

/**
 * StrategieSudoku erweitert Sudoku und implementiert die methode loesen().
 *
 * @author Thomas Wagner
 */
public class StrategieSudoku extends Sudoku {
    /**
     * Erstellt ein StrategieSudoku Objekt und initialisiert den Lösungsalgorithmus als 'lader'.
     *
     * @param lader
     */
    public StrategieSudoku(SudokuLader lader) {
        super(lader);
    }

    /**
     * Loest das Sudoku mit einem Strategiealgorithmus.
     */
    @Override
    public void loesen() {
        zustand = Loesungsversuch;
        for (Feldgruppe fg : zeilen) {
            for (Feld f : fg.felder) {
                if (f.getWert() != 0) {
                    f.moeglicheWerte.clear();
                    entferneMoeglicheWerte(f);
                }
            }
        }
        anzeige.ausgeben();
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
        ArrayList<Feld> gesetzte = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Feld feld = zeilen[i].getFeld(j);
                if (feld.getWert() == 0) { // data.Feld ist noch nicht gefüllt
                    for (int k = 1; k <= 9; k++) {
                        boolean erfolgreich = feld.setWert(k);
                        if (erfolgreich) {
                            feld.moeglicheWerte.clear();
                            gesetzte.addAll(entferneMoeglicheWerteMitSetzen(feld));
                        }
                        if (erfolgreich && loesenRec()) { // Rekursiver Aufruf, um das nächste data.Feld zu füllen
                            return true; // Lösung gefunden
                        }
                    }
                    gesetzte.forEach(Feld::resetStrat);
                    feld.resetStrat();
                    return false; // Keine Zahl passt in dieses data.Feld
                }
            }
        }
        return true; // Alle Felder sind gefüllt
    }

    private void entferneMoeglicheWerte(Feld ausgangsfeld) {
        for (int i = 0; i < 9; i++) {
            Feld feld = ausgangsfeld.zeile.getFeld(i);
            if (feld != ausgangsfeld) {
                feld.moeglicheWerte.remove((Integer) ausgangsfeld.getWert());
            }
        }

        for (int j = 0; j < 9; j++) {
            Feld feld = ausgangsfeld.spalte.getFeld(j);
            if (feld != ausgangsfeld) {
                feld.moeglicheWerte.remove((Integer) ausgangsfeld.getWert());
            }
        }


        for (int k = 0; k < 9; k++) {
            Feld feld = ausgangsfeld.quadrant.getFeld(k);
            if (feld != ausgangsfeld) {
                feld.moeglicheWerte.remove((Integer) ausgangsfeld.getWert());
            }
        }
    }

    private ArrayList<Feld> entferneMoeglicheWerteMitSetzen(Feld ausgangsfeld) {
        ArrayList<Feld> gesetzteFelder = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Feld feld = ausgangsfeld.zeile.getFeld(i);
            entferneFelder(ausgangsfeld, gesetzteFelder, feld);
        }

        for (int j = 0; j < 9; j++) {
            Feld feld = ausgangsfeld.spalte.getFeld(j);
            entferneFelder(ausgangsfeld, gesetzteFelder, feld);
        }


        for (int k = 0; k < 9; k++) {
            Feld feld = ausgangsfeld.quadrant.getFeld(k);
            entferneFelder(ausgangsfeld, gesetzteFelder, feld);
        }
        return gesetzteFelder;
    }

    private void entferneFelder(Feld ausgangsfeld, ArrayList<Feld> gesetzteFelder, Feld feld) {
        if (feld != ausgangsfeld) {
            feld.moeglicheWerte.remove((Integer) ausgangsfeld.getWert());
            if (feld.moeglicheWerte.size() == 1) {
                feld.setWert(feld.moeglicheWerte.get(0));
                feld.moeglicheWerte.clear();
                gesetzteFelder.add(feld);
                entferneMoeglicheWerte(feld);
            }
        }
    }
}
