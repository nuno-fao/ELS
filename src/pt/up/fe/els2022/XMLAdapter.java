package pt.up.fe.els2022;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMLAdapter{
    public static ArrayList<HashMap<String, String>> parseFile(String filename, List<String> headers, List<String> elements, String parentElement) throws IOException, SAXException, ParserConfigurationException {
        ArrayList<HashMap<String, String>> entry = new ArrayList<>();

        File file = new File(filename);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        NodeList list = doc.getElementsByTagName(parentElement);


        for (int i = 0; i < list.getLength(); i++) {
            Element parentNode = (Element) list.item(i);
            entry.add(new HashMap<>());
            for (int ii = 0; ii < headers.size(); ii++) {
                String value = parentNode.getElementsByTagName(headers.get(ii)).item(0).getTextContent();
                entry.get(i).put(elements.get(ii), value);
            }
        }

        return entry;
    }
}
