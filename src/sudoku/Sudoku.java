package sudoku;

import data.Feld;
import data.Feldgruppe;
import data.SudokuZustand;
import lader.SudokuLader;

import static data.SudokuZustand.Leer;

/**
 * Abstrakte Klasse, welche das Grundverhalten eines Sudokus definiert wird und von seinen Unterklassen mit der
 * loesen() Methode erweitert wird.
 *
 * @author Thomas Wagner
 */
public abstract class Sudoku {
    /**
     * Array, welches alle Quadranten des Sudokus speichert.
     */
    public Feldgruppe[] quadranten;
    /**
     * Array, welches alle Zeilen des Sudokus speichert.
     */
    public Feldgruppe[] zeilen;
    /**
     * Array, welches alle Spalten des Sudokus speichert.
     */
    public Feldgruppe[] spalten;
    /**
     * Speichert Referenz auf das Anzeigeobjekt, welches die Ausgabe des Sudokus übernimmt.
     */
    public SudokuAnzeige anzeige;
    /**
     * Speichert Referenz auf das Laderobjekt, welches das Laden des Sudokus übernimmt.
     */
    public SudokuLader lader;
    /**
     * Enum, welches den aktuellen Zustand des Sudokus speichert.
     */
    public SudokuZustand zustand;
    /**
     * Die Anzahl der benötigten Lösungsschritte.
     */
    protected int schritte = 0;

    /**
     * Erstellt ein Sudokuobjekt und initialisiert Feldgruppen, Felder, anzeige und speichert die Referenz auf das lader
     * Objekt 'lader'.
     * Achtung, da die Klasse abstract ist, wird dieser Konstruktor nur von seinen Unterklassen aufgerufen.
     *
     * @param lader
     */
    public Sudoku(SudokuLader lader) {
        anzeige = new SudokuAnzeige(this);
        this.lader = lader;

        quadranten = new Feldgruppe[9];
        zeilen = new Feldgruppe[9];
        spalten = new Feldgruppe[9];

        for (int i = 0; i < 9; i++) {
            // Instanziiere eine neue data.Feldgruppe und speichere sie in quadranten[i]
            Feldgruppe quadrant = new Feldgruppe();
            quadrant.setNr(i);
            quadranten[i] = quadrant;
            // Instanziiere eine neue data.Feldgruppe und speichere sie in zeilen[i]
            Feldgruppe zeile = new Feldgruppe();
            zeile.setNr(i);
            zeilen[i] = zeile;
            // Instanziiere eine neue data.Feldgruppe und speichere sie in spalten[i]
            Feldgruppe spalte = new Feldgruppe();
            spalte.setNr(i);
            spalten[i] = spalte;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int quadrantenIndex = (i / 3) * 3 + j / 3;
                Feld feld = new Feld(zeilen[i], spalten[j], quadranten[quadrantenIndex]);
                zeilen[i].setFeld(j, feld);
                spalten[j].setFeld(i, feld);
                // (i/3) * 3, da i die Zeilennummer ist und eine Zeile 3 Felder hat
                int feldIndex = (i % 3) * 3 + j % 3;
                quadranten[quadrantenIndex].setFeld(feldIndex, feld);
            }
        }
        zustand = Leer;
    }

    /**
     * Setzt den Wert des Felds in Zeile 'zeile' und Spalte 'spalte auf den Wert 'wert', wenn diese gültig sind.
     *
     * @param zeile  die Zeile des Feldes.
     * @param spalte die Spalte des Feldes.
     * @param wert   der neue Wert des Feldes
     * @return true, wenn das setzten erfolgreich war, false wenn nicht.
     */
    public boolean setWert(int zeile, int spalte, int wert) {
        if (istKoordinateGueltig(zeile, spalte)) {
            return zeilen[zeile].getFeld(spalte).setWert(wert);
        }
        return false;
    }

    private boolean istKoordinateGueltig(int zeile, int spalte) {
        return zeile >= 0 && zeile < 9 && spalte >= 0 && spalte < 9;
    }

    /**
     * Setzt alle Felder des Sudokus mit Hilfe der reset() Methode der Felder zurück.
     */
    public void reset() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                zeilen[i].getFeld(j).reset();
            }
        }
    }

    /**
     * Beschreibt den Loesungsalgorithmus des Sudokus.
     */
    public abstract void loesen();

    /**
     * Überprüft, ob das gesamte Sudoku mit Werten gefüllt ist.
     *
     * @return true, wenn es gefüllt ist, false wenn nicht.
     */
    public boolean istGeloest() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Feld feld = zeilen[i].getFeld(j);
                if (feld.getWert() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Fixiert das Feld in Zeile 'i' und Spalte 'j'.
     *
     * @param i die Zeile des Feldes.
     * @param j die Spalte des Feldes.
     */
    public void fixiere(int i, int j) {
        zeilen[i].getFeld(j).setFixiert(true);
    }
}
