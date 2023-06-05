package data;

import exception.*;

import java.util.ArrayList;

/**
 * Ein Feld eines Sudokus.
 * Speichert den Wert des Feldes, ob es fixiert ist, und die Zeile, Spalte und den Quadranten, in welchem sich das Feld befindet.
 * Außerdem speichert es die möglichen Werte, welche noch für das Feld infrage kommen.
 *
 * @author Thomas Wagner
 */
public class Feld {
    /**
     * Die Zeile, in welcher sich das Feld befindet.
     */
    public Feldgruppe zeile;
    /**
     * Die Spalte, in welcher sich das Feld befindet.
     */
    public Feldgruppe spalte;
    /**
     * Der Quadrant, in welchem sich das Feld befindet.
     */
    public Feldgruppe quadrant;
    /**
     * Die möglichen Werte, welche noch für das Feld infrage kommen.
     */
    public ArrayList<Integer> moeglicheWerte;
    private int wert;
    private boolean fixiert;
    private FeldBelegtException feldBelegtException = new FeldBelegtException("Das Feld ist bereits belegt.");
    private WertInZeileVorhandenException wertInZeileVorhandenException = new WertInZeileVorhandenException("Der Wert ist bereits in der Zeile vorhanden.");
    private WertInSpalteVorhandenException wertInSpalteVorhandenException = new WertInSpalteVorhandenException("Der Wert ist bereits in der Spalte vorhanden.");
    private WertInQuadrantVorhandenException wertInQuadrantVorhandenException = new WertInQuadrantVorhandenException("Der Wert ist bereits in dem Quadrant vorhanden.");
    private WerteBereichUngueltigException werteBereichUngueltigException = new WerteBereichUngueltigException("Der Wert ist nicht im Bereich von 1 bis 9.");


    /**
     * Erstellt ein neues Feld.
     *
     * @param neueZeile     Zeile, in welcher sich das Feld befindet.
     * @param neueSpalte    Spalte, in welcher sich das Feld befindet.
     * @param neuerQuadrant Quadrant, in welchem sich das Feld befindet.
     */
    public Feld(Feldgruppe neueZeile, Feldgruppe neueSpalte, Feldgruppe neuerQuadrant) {
        this.zeile = neueZeile;
        this.spalte = neueSpalte;
        this.quadrant = neuerQuadrant;
        this.moeglicheWerte = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            this.moeglicheWerte.add(i);
        }
    }

    /**
     * Gibt den Wert des Feldes zurück.
     *
     * @return Wert des Feldes.
     */
    public int getWert() {
        return this.wert;
    }

    /**
     * Setzt den Wert des Feldes.
     *
     * @param fixiert Neuer Wert des Feldes.
     */
    public void setFixiert(boolean fixiert) {
        this.fixiert = fixiert;
    }

    /**
     * Gibt zurück, ob das Feld fixiert ist.
     *
     * @return true, wenn das Feld fixiert ist, sonst false.
     */
    public boolean istFixiert() {
        return this.fixiert;
    }

    /**
     * Setzt den Wert des Feldes.
     * Wirft eine Exception, wenn der Wert nicht gültig ist, oder bereits in der Zeile, Spalte oder im Quadranten vorhanden ist.
     * @throws WerteBereichUngueltigException Wenn der Wert nicht im Bereich von 1 bis 9 liegt.
     * @throws WertInZeileVorhandenException Wenn der Wert bereits in der Zeile vorhanden ist.
     * @throws WertInSpalteVorhandenException Wenn der Wert bereits in der Spalte vorhanden ist.
     * @throws WertInQuadrantVorhandenException Wenn der Wert bereits in dem Quadranten vorhanden ist.
     * @throws FeldBelegtException Wenn das Feld bereits belegt ist.
     *
     */
    public void setWert(int wert) throws WerteBereichUngueltigException, WertInZeileVorhandenException, WertInSpalteVorhandenException, WertInQuadrantVorhandenException, FeldBelegtException {
        if (this.wert != 0) {
            throw feldBelegtException;
        }
        // Prüfe, ob der Wert gültig ist.
        if (wert < 1 || wert > 9) {
            throw werteBereichUngueltigException;
        }
        //Prüfe, ob der Wert eindeutig ist.
        if (zeile.istVorhanden(wert)) {
            throw wertInZeileVorhandenException;
        }
        if (spalte.istVorhanden(wert)) {
            throw wertInSpalteVorhandenException;
        }
        if (quadrant.istVorhanden(wert)) {
            throw wertInQuadrantVorhandenException;
        }
        this.wert = wert;
    }

    /**
     * Gibt zurück, ob der Wert gültig ist.
     *
     * @param wert Wert, welcher geprüft werden soll.
     * @return true, wenn der Wert gültig ist, sonst false.
     */
    public boolean istGueltig(int wert) {
        return
                !zeile.istVorhanden(wert) &&
                        !spalte.istVorhanden(wert) &&
                        !quadrant.istVorhanden(wert) &&
                        wert >= 1 &&
                        wert <= 9;
    }

    /**
     * Setzt den Wert des Feldes auf 0(leer) und setzt fixiert auf false.
     */
    public void reset() {
        this.wert = 0;
        this.fixiert = false;
    }

    /**
     * Setzt den Wert des Feldes auf 0(leer) und setzt fixiert auf false.
     * Fügt den Wert, welcher vorher im Feld war, wieder zu den möglichen Werten anderer Felder in der gleichen Spalte,
     * Reihe und im gleichen Quadranten hinzu, wenn dieser Wert gültig ist. Erneuert dann die Liste der möglichen Werte.
     */
    public void resetStrat() {
        int vorherigerWert = this.wert;
        this.fixiert = false;
        this.wert = 0;
        for (Feld f : zeile.felder) {
            if (f.istGueltig(vorherigerWert)) {
                f.moeglicheWerte.add(vorherigerWert);
            }
        }
        for (Feld f : spalte.felder) {
            if (f.istGueltig(vorherigerWert)) {
                f.moeglicheWerte.add(vorherigerWert);
            }
        }
        for (Feld f : quadrant.felder) {
            if (f.istGueltig(vorherigerWert)) {
                f.moeglicheWerte.add(vorherigerWert);
            }
        }
        for (int i = 1; i <= 9; i++) {
            if (istGueltig(i)) {
                this.moeglicheWerte.add(i);
            }
        }
    }
}
