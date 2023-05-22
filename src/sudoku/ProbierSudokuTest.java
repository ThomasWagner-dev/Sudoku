package sudoku;

import anzeige.SudokuTerminalAnzeige;
import data.SudokuZustand;
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

    @Test
    void loesen() {
        probierSudoku.lader.laden(probierSudoku);
        probierSudoku.loesen();
        assert probierSudoku.zustand == SudokuZustand.Geloest;
    }

    @Test
    void loesenUnmoeglich() {
        probierSudoku.setWert(0, 3, 1);
        probierSudoku.setWert(1, 3, 2);
        probierSudoku.setWert(3, 0, 1);
        probierSudoku.setWert(3, 1, 2);
        probierSudoku.setWert(5, 6, 1);
        probierSudoku.setWert(5, 7, 2);
        probierSudoku.setWert(6, 5, 1);
        probierSudoku.setWert(7, 5, 2);
        probierSudoku.anzeige.anzeigen();
        probierSudoku.loesen();
        assert probierSudoku.zustand == SudokuZustand.Unloesbar;
    }

    @Test
    void setWert() {
        System.out.println("Sudoku vor Setzen des Wertes 1 in Zeile 0, Spalte 0");
        probierSudoku.anzeige.anzeigen();
        probierSudoku.setWert(0, 0, 1);
        System.out.println("Danach:");
        probierSudoku.anzeige.anzeigen();
        assert probierSudoku.zeilen[0].getFeld(0).getWert() == 1;
    }
}