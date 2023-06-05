import org.w3c.dom.Element;
import sudoku.Sudoku;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * Klasse zum Speichern eines Sudokus in eine XML-Datei.
 */
public class XmlSaver {
    DocumentBuilder builder;

    /**
     * Konstruktor der Klasse XmlSaver.
     */
    public XmlSaver() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (Exception e) {
            System.out.println("Fehler beim Erstellen des DocumentBuilders: " + e.getMessage());
        }

    }

    /**
     * Beschreibt das Speichern eines Sudokus in eine XML-Datei.
     * @param s das Sudoku-Objekt, das gespeichert werden soll.
     * @param dateiname der Name der Datei, in die das Sudoku gespeichert werden soll.
     */
    public void speichern(Sudoku s, String dateiname) {
        Document doc = builder.newDocument();
        Element root = doc.createElement("sudoku");
        root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xmlns", "https://namespace.fh-muenster.de/studium");
        root.setAttribute("xsi:schemaLocation", "https://namespace.fh-muenster.de/studium ../sudoku.xsd");
        doc.appendChild(root);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Element zelle = doc.createElement("feld");
                Element wert = doc.createElement("wert");
                wert.setTextContent(String.valueOf(s.zeilen[i].getFeld(j).getWert()));
                zelle.appendChild(wert);
                root.appendChild(zelle);
            }
        }

        try {
            OutputStream os = new FileOutputStream(dateiname);
            StreamResult result = new StreamResult(os);
            DOMSource source = new DOMSource(doc);
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.transform(source, result);
        } catch (Exception e) {
            System.out.println("Fehler beim Speichern des Dokuments: " + e.getMessage());
        }


    }
}
