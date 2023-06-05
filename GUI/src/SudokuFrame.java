import anzeige.ISudokuAnzeige;
import data.SudokuZustand;
import lader.*;
import sudoku.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * Die Klasse SudokuFrame ist eine grafische Oberfläche für das Sudoku.
 * @author Thomas Wagner
 */
public class SudokuFrame extends Frame implements ISudokuAnzeige, ZustandListener, ErrorListener {
    private final Button[][] buttons = new Button[9][9];
    private Choice choiceLaden;
    private Choice choiceLoesen;
    private Sudoku sudoku;
    private final Label status;
    private final TextField exception;
    private final TextField input;
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
        Label statusLabel = new Label("Status: ");
        statusLabel.setBounds(500, 295, 200, 25);
        this.add(statusLabel);
        status = new Label("");
        status.setBounds(500, 320, 200, 25);
        this.add(status);

        // Erstelle ExceptionLabel
        Label exceptionLabel = new Label("Exception: ");
        exceptionLabel.setBounds(500, 345, 200, 25);
        this.add(exceptionLabel);
        exception = new TextField("");
        exception.setEditable(false);
        exception.setBounds(500, 370, 200, 25);
        this.add(exception);
        // Erstelle Eingabefeld
        input = new TextField();
        input.setBounds(500, 270, 200, 25);
        this.add(input);
        init();
        this.setVisible(true);
    }

    private void init() {
        SudokuLader lader = new BeispielLader();
        sudoku = new ProbierSudoku(lader, this);
        sudoku.addZustandListener(this);
        sudoku.addErrorListener(this);
        anzeigen();
    }

    private void createMenu() {
        // Erstelle Menü
        Label choiceLadenLabel = new Label("Lader:");
        choiceLadenLabel.setBounds(500, 40, 50, 25);
        this.add(choiceLadenLabel);

        choiceLaden = new Choice();
        choiceLaden.setBounds(500, 75, 200, 25);
        choiceLaden.add("Beispiel");
        choiceLaden.add("Zufall");
        choiceLaden.add("Terminal");
        choiceLaden.add("XML");
        this.add(choiceLaden);

        Label choiceLoesenLabel = new Label("Löser:");
        choiceLoesenLabel.setBounds(500, 110, 50, 25);
        this.add(choiceLoesenLabel);

        choiceLoesen = new Choice();
        choiceLoesen.setBounds(500, 145, 200, 25);
        choiceLoesen.add("Ausprobieren");
        choiceLoesen.add("Zufall");
        choiceLoesen.add("Strategie");
        this.add(choiceLoesen);
        // Erstelle Schaltfläche
        Button laden = new Button();
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
                case "XML" -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new java.io.File("./data"));
                    chooser.setDialogTitle("Wähle XML-Datei");
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        lader = new XmlLader(chooser.getSelectedFile().getAbsolutePath());
                    } else {
                        return;
                    }
                }
                default -> lader = null;
            }
            switch (choiceLoesen.getSelectedItem()) {
                case "Ausprobieren" -> sudoku = new ProbierSudoku(lader, this);
                case "Zufall" -> sudoku = new ZufallSudoku(lader, this);
                case "Strategie" -> sudoku = new StrategieSudoku(lader, this);
            }
            sudoku.addZustandListener(this);
            sudoku.addErrorListener(this);
            sudoku.lader.laden(sudoku);
            anzeigen();
        });
        this.add(laden);

        Button loesen = new Button();
        loesen.setLabel("Lösen");
        loesen.setBounds(555, 210, 100, 20);
        loesen.addActionListener((ignored) -> {
            if (sudoku != null) {
                sudoku.loesen();
            }
        });
        this.add(loesen);

        Button speichern = new Button();
        speichern.setLabel("Speichern");
        speichern.setBounds(555, 240, 100, 20);
        speichern.addActionListener((ignored) -> {
            if (sudoku != null) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("./data"));
                chooser.setDialogTitle("Wähle Speicherort");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    XmlSaver saver = new XmlSaver();
                    saver.speichern(sudoku, chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        this.add(speichern);
        anzeigen();
    }

    /**
     * Zeigt das Sudoku in der grafischen Oberfläche an.
     */
    @Override
    public void anzeigen() {
        if (sudoku != null) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j<9; j++) {
                    if (sudoku.zeilen[i].getFeld(j).getWert() != 0) {
                        buttons[j][i].setLabel(String.valueOf(sudoku.zeilen[i].getFeld(j).getWert()));
                    } else {
                        buttons[j][i].setLabel("");
                    }
                }
            }
        }
    }

    /**
     * Setzt das Sudoku, das in der grafischen Oberfläche angezeigt werden soll.
     * @param sudoku Sudoku, das angezeigt werden soll.
     */
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
                    if(sudoku.istGeloest()) {
                        sudoku.fireError(new Exception("Sudoku ist bereits gelöst"));
                        return;
                    }
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
                    int zahl;
                    try {
                        zahl = Integer.parseInt(input.getText());
                    } catch (NumberFormatException ignored) {
                        return;
                    }
                    try {
                        sudoku.zeilen[koordinaten[0]].getFeld(koordinaten[1]).setWert(zahl);
                    } catch (Exception ex) {
                        sudoku.fireError(ex);
                    }
                    anzeigen();
                    if (sudoku.istGeloest()) {
                        sudoku.setZustand(SudokuZustand.Geloest);
                    }
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
                    return new int[]{j, i};
                }
            }
        }
        return null;
    }

    /**
     * Wird aufgerufen, wenn sich der Zustand des Sudokus ändert.
     * @param zustand Neuer Zustand des Sudokus.
     */
    @Override
    public void zustandChanged(SudokuZustand zustand) {
        status.setText(zustand.toString());
    }

    /**
     * Wird aufgerufen, wenn ein Fehler auftritt.
     * @param e Fehler, der aufgetreten ist.
     */
    @Override
    public void exceptionOccurred(Exception e) {
        exception.setText(e.getMessage());
    }
}
