import anzeige.ISudokuAnzeige;
import data.SudokuZustand;
import lader.BeispielLader;
import lader.SudokuLader;
import lader.TerminalLader;
import lader.ZufallLader;
import sudoku.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.ExceptionListener;
import java.util.Scanner;


public class SudokuFrame extends Frame implements ISudokuAnzeige, ZustandListener {
    private final Button[][] buttons = new Button[9][9];
    Label choiceLadenLabel;
    Label choiceLoesenLabel;
    Choice choiceLaden;
    Choice choiceLoesen;
    Button loesen;
    Button laden;
    Sudoku sudoku;
    Label statusLabel;
    Label status;
    Label exceptionLabel;
    Label exception;
    TextField input;
    public static void main(String[] args) {
        SudokuFrame frame = new SudokuFrame();

    }

    public SudokuFrame() {
        super("Sudoku");
        this.setSize(740, 500);
        this.setLayout(null);
        this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        } );
        // Erstelle SudokuFelder
        createButtons();
        // Erstelle Menü
        createMenu();
        // Erstelle StatusLabel
        statusLabel = new Label("Status: ");
        statusLabel.setBounds(500, 450, 200, 25);
        this.add(statusLabel);
        status = new Label("");
        status.setBounds(500, 475, 200, 25);
        this.add(status);

        // Erstelle ExceptionLabel
        exceptionLabel = new Label("Exception: ");
        exceptionLabel.setBounds(500, 400, 200, 25);
        this.add(exceptionLabel);
        exception = new Label("");
        exception.setBounds(500, 425, 200, 25);
        this.add(exception);
        ExceptionListener exceptionListener = e -> exception.setText(e.getMessage());
        // Erstelle Eingabefeld
        input = new TextField();
        input.setBounds(500, 350, 200, 25);
        this.add(input);
        init();
        this.setVisible(true);
    }

    private void init() {
        SudokuLader lader = new BeispielLader();
        sudoku = new ProbierSudoku(lader, this);
        sudoku.addZustandListener(this);
        anzeigen();
    }

    private void createMenu() {
        // Erstelle Menü
        choiceLadenLabel = new Label("Lader:");
        choiceLadenLabel.setBounds(500, 40, 50, 25);
        this.add(choiceLadenLabel);

        choiceLaden = new Choice();
        choiceLaden.setBounds(500, 75, 200, 25);
        choiceLaden.add("Beispiel");
        choiceLaden.add("Zufall");
        choiceLaden.add("Terminal");
        this.add(choiceLaden);

        choiceLoesenLabel = new Label("Löser:");
        choiceLoesenLabel.setBounds(500, 110, 50, 25);
        this.add(choiceLoesenLabel);

        choiceLoesen = new Choice();
        choiceLoesen.setBounds(500, 145, 200, 25);
        choiceLoesen.add("Ausprobieren");
        choiceLoesen.add("Zufall");
        choiceLoesen.add("Strategie");
        this.add(choiceLoesen);
        // Erstelle Schaltfläche
        laden = new Button();
        laden.setLabel("Laden");
        laden.setBounds(555, 180, 100, 20);
        laden.addActionListener(e -> {
            SudokuLader lader;
            switch (choiceLaden.getSelectedItem()) {
                case "Beispiel" -> lader = new BeispielLader();
                case "Zufall" -> {System.out.print("Bitte geben Sie die Anzahl der zu generierenden Felder ein: ");
                System.out.print("Auswahl: ");
                Scanner sc = new Scanner(System.in);
                try {
                    int anzahl = Integer.parseInt(sc.nextLine());
                    lader = new ZufallLader(anzahl);
                } catch (Exception ignored) {
                    System.out.println("Falsche Eingabe");
                    return;
                }}
                case "Terminal" -> lader = new TerminalLader();
                default -> lader = null;
            }
            switch (choiceLoesen.getSelectedItem()) {
                case "Ausprobieren" -> sudoku = new ProbierSudoku(lader, this);
                case "Zufall" -> sudoku = new ZufallSudoku(lader, this);
                case "Strategie" -> sudoku = new StrategieSudoku(lader, this);
            }
            sudoku.addZustandListener(this);
            sudoku.lader.laden(sudoku);
            anzeigen();
        });
        this.add(laden);

        loesen = new Button();
        loesen.setLabel("Lösen");
        loesen.setBounds(555, 210, 100, 20);
        loesen.addActionListener((ignored) -> {
            if (sudoku != null) {
                sudoku.loesen();
            }
        });
        anzeigen();
        this.add(loesen);
    }

    @Override
    public void anzeigen() {
        if (sudoku != null) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j<9; j++) {
                    if (sudoku.zeilen[i].getFeld(j).getWert() != 0) {
                        buttons[i][j].setLabel(String.valueOf(sudoku.zeilen[i].getFeld(j).getWert()));
                    } else {
                        buttons[i][j].setLabel("");
                    }
                }
            }
        }
    }

    @Override
    public void setSudoku(Sudoku sudoku) {
    }

    private void createButtons() {
        for(int i=0; i<9; i++) {
            for (int j = 0; j < 9; j++) {
                // Neue Schaltfläche instanziieren
                Button b = new Button();
                b.setLabel("Feld " + (i+1));
                // Position auf dem Elternelement (x, y, breite, hoehe)
                b.setBounds(i*50 + 10, j * 50 + 40, 50, 50);
                b.addActionListener(e -> {
                    if (sudoku == null) {
                        return;
                    }

                    int[] koordinaten = getButtonKoordinaten((Button) e.getSource());
                    if (koordinaten == null) {
                        return;
                    }
                    if (input.getText().equals("")) {
                        return;
                    }
                    try {
                        sudoku.zeilen[koordinaten[0]].getFeld(koordinaten[1]).setWert(Integer.parseInt(input.getText()));
                    } catch (NumberFormatException ignored) {
                        return;
                    }
                    anzeigen();

                }
                );
                this.add(b);
                buttons[i][j] = b;
            }
        }
    }

    private int[] getButtonKoordinaten(Button e) {
        for (int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if (buttons[i][j] == e) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    @Override
    public void zustandChanged(SudokuZustand zustand) {
        status.setText(zustand.toString());
    }
}
