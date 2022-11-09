package pt.up.fe.els2022;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class XMLAdapter{
    public static ArrayList<HashMap<String, String>> parseFile(String filename, List<String> headers, List<String> elements, List<String> parentElements) throws IOException, SAXException, ParserConfigurationException {
        ArrayList<HashMap<String, String>> entry = new ArrayList<>();

        parentElements = List.of(parentElements.get(0).split("/"));

        File file = new File(filename);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        Element elem = doc.getDocumentElement();

        List<Element> elems = new ArrayList<>(Collections.singletonList(elem));
        List<Element> auxElems = new ArrayList<>();
        for (String elementName : parentElements) {
            auxElems = new ArrayList<>();
            for (Element e :elems){
                NodeList list = e.getElementsByTagName(elementName);
                for(int i = 0; i < list.getLength();i++){
                    auxElems.add((Element) list.item(i));
                }
            }
            elems = auxElems;
        }

        List<Element> list = elems;

        for (int i = 0; i < list.size(); i++) {
            Element parentNode = list.get(i);
            entry.add(new HashMap<>());

            if(headers.size() > 0) {
                for (int ii = 0; ii < headers.size(); ii++) {
                    String value = parentNode.getElementsByTagName(headers.get(ii)).item(0).getTextContent();
                    entry.get(i).put(elements.get(ii), value);
                }
            }else{
                for(int ii = 0; ii < parentNode.getChildNodes().getLength(); ii++){
                    Node value = parentNode.getChildNodes().item(ii);
                    if(!value.getNodeName().equals("#text")) {
                        entry.get(i).put(value.getNodeName(), value.getTextContent());
                    }
                }
            }
        }

        return entry;
    }
}
