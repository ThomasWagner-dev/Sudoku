package sudoku;

import anzeige.ISudokuAnzeige;
import data.Feld;
import data.Feldgruppe;
import data.SudokuZustand;
import exception.*;
import lader.SudokuLader;

import java.util.ArrayList;

import static data.SudokuZustand.Leer;

/**
 * Abstrakte Klasse, welche das Grundverhalten eines Sudokus definiert wird und von seinen Unterklassen mit der
 * loesen() Methode erweitert wird.
 *
 * @author Thomas Wagner
 */
public abstract class Sudoku{
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
    public ISudokuAnzeige anzeige;
    /**
     * Speichert Referenz auf das Laderobjekt, welches das Laden des Sudokus übernimmt.
     */
    public SudokuLader lader;
    /**
     * Enum, welches den aktuellen Zustand des Sudokus speichert.
     */
    public SudokuZustand zustand;
    /**
     * Speichert Klassen, welche dem Zustand zuhören und bei Änderung benachrichtigt werden.
     */
    public ArrayList<ZustandListener> zustandListeners = new ArrayList<>();
    /**
     * Speicher die Klassen, welche den Exceptions zuhören und bei einer Exception benachrichtigt werden.
     */
    public ArrayList<ErrorListener> errorListeners = new ArrayList<>();
    /**
     * Die Anzahl der benötigten Lösungsschritte.
     */
    protected int schritte = 0;
    /**
     * Erstellt ein Sudokuobjekt und initialisiert Feldgruppen, Felder, anzeige und speichert die Referenz auf das lader
     * Objekt 'lader'.
     * Achtung, da die Klasse abstract ist, wird dieser Konstruktor nur von seinen Unterklassen aufgerufen.
     *
     * @param lader Der Lader, welcher das Laden des Sudokus übernimmt.
     * @param anzeige Die Anzeige, welche die Ausgabe des Sudokus übernimmt.
     */
    public Sudoku(SudokuLader lader, ISudokuAnzeige anzeige) {
        this.anzeige = anzeige;
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

        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                int quadrantenIndex = (j / 3) * 3 + k / 3;
                Feld feld = new Feld(zeilen[j], spalten[k], quadranten[quadrantenIndex]);
                zeilen[j].setFeld(k, feld);
                spalten[k].setFeld(j, feld);
                // (i/3) * 3, da i die Zeilennummer ist und eine Zeile 3 Felder hat
                int feldIndex = (j % 3) * 3 + k % 3;
                quadranten[quadrantenIndex].setFeld(feldIndex, feld);
            }
        }
        setZustand(Leer);
    }

    /**
     * Setzt den Wert des Felds in Zeile 'zeile' und Spalte 'spalte auf den Wert 'wert', wenn diese gültig sind.
     *
     * @param zeile  die Zeile des Feldes.
     * @param spalte die Spalte des Feldes.
     * @param wert   der neue Wert des Feldes
     */
    public void setWert(int zeile, int spalte, int wert) throws
            UngueltigeKoordinatenException,
            WerteBereichUngueltigException,
            WertInZeileVorhandenException,
            WertInSpalteVorhandenException,
            WertInQuadrantVorhandenException, FeldBelegtException {
        if (!istKoordinateGueltig(zeile, spalte)) {
            throw new UngueltigeKoordinatenException("Die Koordinaten" + zeile + ", " + spalte + " sind ungültig.");
        }
        zeilen[zeile].getFeld(spalte).setWert(wert);
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

    /**
     * Fügt einen Zustandslistener hinzu.
     */
    public void addZustandListener(ZustandListener listener) {
        zustandListeners.add(listener);
    }

    /**
     * Benachrichtigt alle Zustandslistener über eine Zustandsänderung und ändert ihn.
     */
    public void setZustand(SudokuZustand zustand) {
        this.zustand = zustand;
        for (ZustandListener listener : zustandListeners) {
            listener.zustandChanged(zustand);
        }
    }

    /**
     * Fügt einen Error listener hinzu.
     */
    public void addErrorListener(ErrorListener listener) {
        errorListeners.add(listener);
    }

    /**
     * Benachrichtigt alle Errorlistener über einen Fehler.
     */
    public void fireError(Exception e) {
        for (ErrorListener listener : errorListeners) {
            listener.exceptionOccurred(e);
        }
    }
}
