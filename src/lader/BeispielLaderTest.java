package lader;

import anzeige.SudokuTerminalAnzeige;
import data.SudokuZustand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.ProbierSudoku;

class BeispielLaderTest {
    ProbierSudoku probierSudoku;
    BeispielLader lader;

    @BeforeEach
    void setUp() {
        lader = new BeispielLader();
        SudokuTerminalAnzeige anzeige = new SudokuTerminalAnzeige();
        probierSudoku = new ProbierSudoku(lader, anzeige);
    }

    @Test
    void laden() {
        System.out.println("Sudoku vor Laden des Beispiel-Sudokus:");
        probierSudoku.anzeige.anzeigen();
        lader.laden(probierSudoku);
        System.out.println("Danach:");
        probierSudoku.anzeige.anzeigen();
        assert probierSudoku.zustand == SudokuZustand.Geladen;
        assert probierSudoku.zeilen[0].getFeld(1).getWert() == 3;

        assert probierSudoku.zeilen[1].getFeld(3).getWert() == 1;
        assert probierSudoku.zeilen[1].getFeld(4).getWert() == 9;
        assert probierSudoku.zeilen[1].getFeld(5).getWert() == 5;
        assert probierSudoku.zeilen[2].getFeld(2).getWert() == 8;
        assert probierSudoku.zeilen[2].getFeld(7).getWert() == 6;
        assert probierSudoku.zeilen[3].getFeld(0).getWert() == 8;
        assert probierSudoku.zeilen[3].getFeld(4).getWert() == 6;
        assert probierSudoku.zeilen[4].getFeld(0).getWert() == 4;
        assert probierSudoku.zeilen[4].getFeld(3).getWert() == 8;
        assert probierSudoku.zeilen[4].getFeld(8).getWert() == 1;
        assert probierSudoku.zeilen[5].getFeld(4).getWert() == 2;
        assert probierSudoku.zeilen[6].getFeld(1).getWert() == 6;
        assert probierSudoku.zeilen[6].getFeld(6).getWert() == 2;
        assert probierSudoku.zeilen[6].getFeld(7).getWert() == 8;
        assert probierSudoku.zeilen[7].getFeld(3).getWert() == 4;
        assert probierSudoku.zeilen[7].getFeld(4).getWert() == 1;
        assert probierSudoku.zeilen[7].getFeld(5).getWert() == 9;
        assert probierSudoku.zeilen[7].getFeld(8).getWert() == 5;
        assert probierSudoku.zeilen[8].getFeld(7).getWert() == 7;
    }
}