package lader;

import exception.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sudoku.Sudoku;

import org.w3c.dom.Document.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class XmlLader extends SudokuLader{
    private String dateiname;
    private DocumentBuilder builder;

    public XmlLader(String dateiname) {
        this.dateiname = dateiname;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (Exception e) {
            System.out.println("Fehler beim Erstellen des DocumentBuilders: " + e.getMessage());
        }
    }

    /**
     * Beschreibt das Laden eines Sudokus in ein Sudoku-Objekt.
     *
     * @param s das Sudoku-Objekt, in das das Sudoku geladen werden soll.
     */
    @Override
    public void laden(Sudoku s) {
        //Überprüfen, ob das Sudoku-Objekt nicht null ist.
        if (s == null) {
            return;
        }
        Document doc = null;
        if (!validateXMLSchema()) {
            return;
        }

        try {
            doc = builder.parse(dateiname);
        } catch (Exception e) {
            System.out.println("Fehler beim Laden des Dokuments!");
            e.printStackTrace();
            return;
        }

        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        s.reset();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9 ; j++) {
                Node node = nodes.item(i * 9 + j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    String wert = e.getElementsByTagName("wert").item(0).getTextContent();
                    try {
                        int w = Integer.parseInt(wert);
                        if (w == 0) {
                            continue;
                        }
                        s.setWert(i, j, w);
                    } catch (Exception ex) {
                        System.out.println("Das Sudoku ist nicht gültig: " + ex.getMessage());
                    }
                }
            }
        }


    }

    private boolean validateXMLSchema(){
        try (InputStream instanceFileStream = new FileInputStream(dateiname);
             InputStream schemaFileStream = new FileInputStream("./sudoku.xsd")){
            Source schemaSource = new StreamSource(schemaFileStream);
            Source instanceSource = new StreamSource(instanceFileStream);

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaSource);

            Validator validator = schema.newValidator();
            validator.validate(instanceSource);
            System.out.println("Datei ist valide.");
            return true;
        } catch (Exception e) {
            System.out.println("Datei ist nicht valide: " + e.getMessage());
            return false;
        }
    }

}
