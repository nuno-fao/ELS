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
import java.util.HashMap;
import java.util.List;

public class XMLAdapter{
    public static HashMap<String, String> parseFile(String filename, List<String> headers, List<String> elements, String parentElement) {
        HashMap<String, String> entry = new HashMap<>();

        entry.put("File", filename);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("files/" + filename));

            NodeList list = doc.getElementsByTagName(parentElement);
            Element parentNode = (Element) list.item(0);

            for (int i = 0; i < headers.size(); i++) {
                String value = parentNode.getElementsByTagName(elements.get(i)).item(0).getTextContent();
                entry.put(headers.get(i), value);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return entry;
    }
}
