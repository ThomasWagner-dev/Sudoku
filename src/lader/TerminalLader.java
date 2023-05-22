package lader;

import sudoku.Sudoku;

import java.util.Scanner;

import static data.SudokuZustand.Geladen;

/**
 * TerminalLader erweitert SokudoLader und implementiert die abstrakte Methode laden().
 * Läd ein Sudoku, aus der Nutzereingabe in das Sudoku-Objekt: 's'.
 *
 * @author Thomas Wagner
 */
public class TerminalLader extends SudokuLader {
    /**
     * Läd ein Sudoku, aus der Nutzereingabe in das Sudoku-Objekt: 's'.
     *
     * @param s das Sudoku-Objekt, in das das Sudoku geladen werden soll.
     */
    @Override
    public void laden(Sudoku s) {
        s.reset();
        System.out.println("Beispieleingabe: 1 2 3 4 _ 6 7 8 9");
        System.out.println("Leere Felder mit _ markieren.");
        System.out.println("Zahlen mit einem Leerzeichen trennen.");

        String[] zeilenStrings = new String[9];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 9; i++) {
            System.out.print("Zeile " + i + ": ");
            zeilenStrings[i] = scanner.nextLine(); // Lese eine Zeile ein
        }

        int ungueltigeEingaben = 0;
        for (int i = 0; i < 9; i++) {
            String[] werte = zeilenStrings[i].split(" "); // Trenne die Zeile an den Leerzeichen um die einzelnen Werte zu erhalten
            if (werte.length != 9) {
                System.out.println("Ungültige Eingabe.");
                s.reset();
                return; // Beende die Methode
            }
            for (int j = 0; j < 9; j++) {
                if (!werte[j].equals("_")) {
                    try {
                        if (!s.setWert(i, j, Integer.parseInt(werte[j]))) { // schlägt fehl, wenn der Wert keine Zahl ist
                            ungueltigeEingaben++;
                        } else {
                            s.fixiere(i, j);
                        }
                    } catch (NumberFormatException e) {
                        s.reset();
                        System.out.println("Ungültige Eingabe.");
                        return; // Beende die Methode
                    }
                } else {
                    s.setWert(i, j, 0);
                }
            }
        }
        if (ungueltigeEingaben > 0) {
            System.out.println("Es wurden " + ungueltigeEingaben + " ungültige Eingaben ignoriert.");
        }
        s.setZustand(Geladen);
    }
}
