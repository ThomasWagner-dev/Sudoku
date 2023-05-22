package lader;

import sudoku.Sudoku;

import static data.SudokuZustand.Geladen;

/**
 * ZufallLader erweitert SudokuLader und implementiert die abstrakte Methode laden().
 * Füllt das Sudoku mit 'anzahl' zufällig gesetzten Zahlen, an zufälligen Positionen.
 *
 * @author Thomas Wagner
 */
public class ZufallLader extends SudokuLader {
    private final int anzahl;

    /**
     * Erstellt ein ZufallLader-Objekt und setzt die Anzahl der zufällig zu setzenden Zahlen.
     *
     * @param anzahl Die Anzahl der zufällig zu setzenden Zahlen.
     */
    public ZufallLader(int anzahl) {
        this.anzahl = anzahl;
    }

    /**
     * Füllt 'anzahl' Felder des Sudoku-Objekts 's' mit zufälligen Zahlen.
     *
     * @param s das Sudoku-Objekt, in das das Sudoku geladen werden soll.
     */
    @Override
    public void laden(Sudoku s) {
        s.reset();
        int gesezteZahlen = 0;
        while (gesezteZahlen < anzahl) {
            int i = (int) (Math.random() * 9);
            int j = (int) (Math.random() * 9);
            if (s.zeilen[i].getFeld(j).getWert() == 0) {
                int wert = (int) (Math.random() * 9) + 1;
                while (!s.setWert(i, j, wert)) {
                    wert = (int) (Math.random() * 9) + 1;
                }
                s.fixiere(i, j);
                gesezteZahlen++;
                System.out.println(gesezteZahlen);
            }
        }
        s.setZustand(Geladen);
    }
}
