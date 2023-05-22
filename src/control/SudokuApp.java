package control;

import anzeige.SudokuTerminalAnzeige;
import lader.BeispielLader;
import lader.SudokuLader;
import lader.TerminalLader;
import lader.ZufallLader;
import sudoku.ProbierSudoku;
import sudoku.StrategieSudoku;
import sudoku.Sudoku;
import sudoku.ZufallSudoku;

import java.util.Scanner;

/**
 * Die Klasse SudokuApp ist die Hauptklasse des Programms.
 * Sie enthält die main-Methode und ist für die Interaktion mit dem Benutzer zuständig.
 *
 * @author Thomas Wagner
 */
public class SudokuApp {
    /**
     * Die Methode main ist die Hauptmethode des Programms.
     *
     * @param args Kommandozeilenparameter
     */
    public static void main(String[] args) {
        // Menü
        while (true) {
            menu();
        }

    }

    /**
     * Die Methode menu zeigt das Menü an und verarbeitet die Eingaben des Benutzers. Um aus Ihnen ein passendes
     * Sudoku mit Löseart und Ladeart zu erstellen.
     */
    public static void menu() {
        int auswahlStrategie = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("sudoku.Sudoku v.3");
        System.out.println("Bitte wählen Sie die Lösungsstrategie:");
        System.out.println("1. Zufall");
        System.out.println("2. Strategie");
        System.out.println("3. Probier");
        System.out.print("Auswahl: ");
        try {
            auswahlStrategie = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Falsche Eingabe");
        }

        int auswahlLader = 0;
        System.out.println("Bitte wählen Sie die Einlesestrategie:");
        System.out.println("1. Eingabe");
        System.out.println("2. Beispiel");
        System.out.println("3. Zufall");
        System.out.print("Auswahl: ");
        try {
            auswahlLader = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Falsche Eingabe");
        }

        SudokuLader lader;
        switch (auswahlLader) {
            case 1 -> lader = new TerminalLader();
            case 2 -> lader = new BeispielLader();
            case 3 -> {
                System.out.print("Bitte geben Sie die Anzahl der zu generierenden Felder ein: ");
                System.out.print("Auswahl: ");
                try {
                    int anzahl = Integer.parseInt(sc.nextLine());
                    lader = new ZufallLader(anzahl);
                } catch (Exception e) {
                    System.out.println("Falsche Eingabe");
                    return;
                }
            }
            default -> {
                System.out.println("Falsche Eingabe");
                return;
            }
        }
        SudokuTerminalAnzeige anzeige = new SudokuTerminalAnzeige();
        switch (auswahlStrategie) {
            case 1 -> {
                Sudoku s = new ZufallSudoku(lader, anzeige);
                anzeige.setSudoku(s);
                lader.laden(s);
                s.loesen();
            }
            case 2 -> {
                Sudoku s = new StrategieSudoku(lader, anzeige);
                anzeige.setSudoku(s);
                lader.laden(s);
                s.loesen();
            }
            case 3 -> {
                Sudoku s = new ProbierSudoku(lader, anzeige);
                anzeige.setSudoku(s);
                lader.laden(s);
                s.loesen();
            }
            default -> System.out.println("Falsche Eingabe");
        }
        System.out.println();
    }
}