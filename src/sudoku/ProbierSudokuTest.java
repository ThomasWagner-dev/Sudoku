package sudoku;

import anzeige.SudokuTerminalAnzeige;
import data.SudokuZustand;
import exception.*;
import lader.BeispielLader;
import lader.TerminalLader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ProbierSudokuTest {
    BeispielLader lader;
    TerminalLader terminalLader;
    ProbierSudoku probierSudoku;

    @BeforeEach
    void setUp() {
      lader = new BeispielLader();
      SudokuTerminalAnzeige anzeige = new SudokuTerminalAnzeige();
      probierSudoku = new ProbierSudoku(lader, anzeige);
      anzeige.setSudoku(probierSudoku);
      terminalLader = new TerminalLader();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Testet, ob das Beispiel-Sudoku geloest werden kann.
     */
    @Test
    void loesen() {
        probierSudoku.lader.laden(probierSudoku);
        probierSudoku.loesen();
        assert probierSudoku.zustand == SudokuZustand.Geloest;
    }

    /**
     * Testet, ob ein unmögliches Sudoku als unloesbar erkannt wird.
     */
    @Test
    void loesenUnmoeglich() {
        try {
            probierSudoku.setWert(0, 3, 1);
            probierSudoku.setWert(1, 3, 2);
            probierSudoku.setWert(3, 0, 1);
            probierSudoku.setWert(3, 1, 2);
            probierSudoku.setWert(5, 6, 1);
            probierSudoku.setWert(5, 7, 2);
            probierSudoku.setWert(6, 5, 1);
            probierSudoku.setWert(7, 5, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        probierSudoku.anzeige.anzeigen();
        probierSudoku.loesen();
        assert probierSudoku.zustand == SudokuZustand.Unloesbar;
    }

    /**
     * Testet, ob ein Wert erfolgreich in ein leeres Sudoku gesetzt werden kann.
     */
    @Test
    void gueltigerWertLeer() {
        System.out.println("Sudoku vor Setzen des Wertes 1 in Zeile 0, Spalte 0");
        probierSudoku.anzeige.anzeigen();
        try {
            probierSudoku.setWert(0, 0, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Danach:");
        probierSudoku.anzeige.anzeigen();
        assert probierSudoku.zeilen[0].getFeld(0).getWert() == 1;
    }

    /**
     * Testet, ob eine passende Exception geworfen wird, wenn ein Feld bereits einen Wert hat.
     */
    @Test
    void bereitsEinWertVorhanden() {
        try {
            probierSudoku.setWert(0, 0, 1);
            probierSudoku.setWert(0, 0, 2);
        } catch (Exception e) {
            assert e instanceof FeldBelegtException;
        }
    }

    /**
     * Testet, ob eine passende Exception geworfen wird, wenn ein Wert außerhalb des Wertebereiches liegt.
     */
    @Test
    void ausserhalbDesWerteBereiches() {
        try {
            probierSudoku.setWert(0, 0, 10);
        } catch (Exception e) {
            assert e instanceof WerteBereichUngueltigException;
        }
    }

    /**
     * Testet, ob eine passende Exception geworfen wird, wenn ein Wert bereits in der Zeile vorhanden ist.
     */
    @Test
    void bereitsInDerZeilVorhanden() {
        try {
            probierSudoku.setWert(0, 0, 1);
            probierSudoku.setWert(0, 1, 1);
        } catch (Exception e) {
            assert e instanceof WertInZeileVorhandenException;
        }
    }

    /**
     * Testet, ob eine passende Exception geworfen wird, wenn ein Wert bereits in der Spalte vorhanden ist.
     */
    @Test
    void bereitsInDerSpalteVorhanden() {
        try {
            probierSudoku.setWert(0, 0, 1);
            probierSudoku.setWert(1, 0, 1);
        } catch (Exception e) {
            assert e instanceof WertInSpalteVorhandenException;
        }
    }

    /**
     * Testet, ob eine passende Exception geworfen wird, wenn ein Wert bereits im Quadranten vorhanden ist.
     */
    @Test
    void bereitsImQuadrantVorhanden() {
        try {
            probierSudoku.setWert(0, 0, 1);
            probierSudoku.setWert(1, 1, 1);
        } catch (Exception e) {
            assert e instanceof WertInQuadrantVorhandenException;
        }
    }

    /**
     * Testet, ob eine passende Exception geworfen wird, wenn die Koordinaten außerhalb des Spielfeldes liegen.
     */
    @Test
    void falscheKoordinaten() {
        try {
            probierSudoku.setWert(9, 9, 1);
        } catch (Exception e) {
            assert e instanceof UngueltigeKoordinatenException;
        }
    }
}