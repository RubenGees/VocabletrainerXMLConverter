package util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pojo.Unit;
import pojo.Vocable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Created by Ruben on 27.06.2015.
 */
public class Serializer {

    public static void serialize(HashMap<String, Unit> units, Path location) throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.newDocument();
        Element root = doc.createElement("units");
        doc.appendChild(root);

        for (String title : units.keySet()) {
            Unit current = units.get(title);

            Element unit = doc.createElement("unit");
            root.appendChild(unit);

            Element titleElement = doc.createElement("title");
            titleElement.appendChild(doc.createTextNode(title));
            unit.appendChild(titleElement);

            Element vocables = doc.createElement("vocables");
            unit.appendChild(vocables);

            for (Vocable vocable : current) {
                Element vocableElement = doc.createElement("vocable");
                vocables.appendChild(vocableElement);

                Element firstMeaning = doc.createElement("first_meaning");
                vocableElement.appendChild(firstMeaning);

                Element secondMeaning = doc.createElement("second_meaning");
                vocableElement.appendChild(secondMeaning);

                for (String meaning : vocable.getFirstMeaning()) {
                    Element value = doc.createElement("value");
                    value.appendChild(doc.createTextNode(meaning));
                    firstMeaning.appendChild(value);
                }

                for (String meaning : vocable.getSecondMeaning()) {
                    Element value = doc.createElement("value");
                    value.appendChild(doc.createTextNode(meaning));
                    secondMeaning.appendChild(value);
                }
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(Files.newOutputStream(location));

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        transformer.transform(source, result);
    }
}
