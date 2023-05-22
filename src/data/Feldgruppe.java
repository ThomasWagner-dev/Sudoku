package data;

/**
 * Eine Feldgruppe ist eine Gruppe von 9 Feldern, welche zusammengehören.
 *
 * @author Thomas Wagner
 */
public class Feldgruppe {
    /**
     * Die Felder, welche sich in der Feldgruppe befinden.
     */
    public Feld[] felder;
    private int nr;

    /**
     * Erstellt eine neue Feldgruppe und initialisiert das Array felder.
     */
    public Feldgruppe() {
        felder = new Feld[9];
    }

    /**
     * Gibt die Nummer der Feldgruppe zurück.
     *
     * @return Die Nummer der Feldgruppe.
     */
    public int getNr() {
        return this.nr;
    }

    /**
     * Setzt die Nummer der Feldgruppe.
     *
     * @param neueNr Die neue Nummer der Feldgruppe.
     */
    public void setNr(int neueNr) {
        this.nr = neueNr;
    }

    /**
     * Gibt das Feld mit dem Index 'index' zurück.
     *
     * @param index Index des Feldes.
     * @return Das Feld mit dem Index 'index'.
     */
    public Feld getFeld(int index) {
        return this.felder[index];
    }

    /**
     * Setzt das Feld mit dem Index 'index' auf feld.
     *
     * @param index Index des Feldes.
     * @param feld  Neues Feld.
     */
    public void setFeld(int index, Feld feld) {
        this.felder[index] = feld;
    }

    /**
     * Gibt an, ob die Feldgruppe den Wert 'wert' enthält.
     *
     * @param wert Der Wert, welcher gesucht wird.
     * @return true, wenn der Wert in der Feldgruppe vorhanden ist, sonst false.
     */
    public boolean istVorhanden(int wert) {
        for (int i = 0; i < 9; i++) {
            if (felder[i].getWert() == wert) {
                return true;
            }
        }
        return false;
    }

}
