package sudoku;

import anzeige.ISudokuAnzeige;
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
     * @param lader  der lader, der das Sudoku-Objekt: 's' lädt.
     */
    public StrategieSudoku(SudokuLader lader, ISudokuAnzeige anzeige) {
        super(lader, anzeige);
    }

    /**
     * Loest das Sudoku mit einem Strategiealgorithmus.
     */
    @Override
    public void loesen() {
        setZustand(Loesungsversuch);
        for (Feldgruppe fg : zeilen) {
            for (Feld f : fg.felder) {
                if (f.getWert() != 0) {
                    f.moeglicheWerte.clear();
                    entferneMoeglicheWerte(f);
                }
            }
        }
        anzeige.anzeigen();
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
        ArrayList<Feld> gesetzte = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Feld feld = zeilen[i].getFeld(j);
                if (feld.getWert() == 0 && !feld.istFixiert()) { // data.Feld ist noch nicht gefüllt
                    for (int k = 1; k <= 9; k++) {
                        boolean erfolgreich = true;
                        try {
                            setWert(feld.zeile.getNr(), feld.spalte.getNr(), k);
                        } catch (Exception e) {
                            erfolgreich = false;
                        }
                        if (erfolgreich) {
                            feld.moeglicheWerte.clear();
                            gesetzte.addAll(entferneMoeglicheWerteMitSetzen(feld));
                        }
                        try {
                            if (erfolgreich && loesenRec()) { // Rekursiver Aufruf, um das nächste data.Feld zu füllen
                                return true; // Lösung gefunden
                            }
                        } catch (StackOverflowError e) {
                            return false;
                        }
                        feld.resetStrat();
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
        if (feld != ausgangsfeld && feld.getWert() == 0) {
            feld.moeglicheWerte.remove((Integer) ausgangsfeld.getWert());
            if (feld.moeglicheWerte.size() == 1) {
                try {
                    setWert(feld.zeile.getNr(), feld.spalte.getNr(), feld.moeglicheWerte.get(0));
                } catch (Exception ignored) {
                }
                feld.moeglicheWerte.clear();
                gesetzteFelder.add(feld);
                entferneMoeglicheWerte(feld);
            }
        }
    }
}
