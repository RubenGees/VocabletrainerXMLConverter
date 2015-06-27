package util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import pojo.Meaning;
import pojo.Unit;
import pojo.Vocable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Created by Ruben on 27.06.2015.
 */
public class Parser {

    public static HashMap<String, Unit> parse(Path path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        VocableHandler handler = new VocableHandler();

        parser.parse(path.toString(), handler);

        return handler.getUnits();
    }

    private static class VocableHandler extends DefaultHandler {

        private HashMap<String, Unit> units;

        private Vocable vocable;
        private String unitTitle;
        private Meaning first;
        private Meaning second;

        private ContentType current;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();

            units = new HashMap<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            switch (qName) {
                case "vocable":
                    vocable = new Vocable();
                    break;
                case "values1":
                    first = new Meaning();
                    current = ContentType.FIRST_MEANING;
                    break;
                case "values2":
                    second = new Meaning();
                    current = ContentType.SECOND_MEANING;
                    break;
                case "unit":
                    current = ContentType.UNIT_TITLE;
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            if (qName.equals("vocable")) {
                Unit unit;

                vocable.setFirstMeaning(first);
                vocable.setSecondMeaning(second);

                if (units.containsKey(unitTitle)) {
                    unit = units.get(unitTitle);
                } else {
                    unit = new Unit();

                    unit.setTitle(unitTitle);
                    units.put(unitTitle, unit);
                }

                unit.addVocable(vocable);
            }
        }

        @Override
        public void warning(SAXParseException e) throws SAXException {
            super.warning(e);

            System.out.println("Warning: " + e.getMessage());
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            super.error(e);

            System.out.println("Error: " + e.getMessage());
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            super.fatalError(e);

            System.out.println("Fatal Error: " + e.getMessage());
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            StringBuilder builder = new StringBuilder();

            for (int i = start; i < (start + length); i++) {
                builder.append(ch[i]);
            }

            String string = builder.toString();

            switch (current) {
                case UNIT_TITLE:
                    unitTitle = string;
                    break;
                case FIRST_MEANING:
                    first.addMeaning(string);
                    break;
                case SECOND_MEANING:
                    second.addMeaning(string);
                    break;
            }
        }

        public HashMap<String, Unit> getUnits() {
            return units;
        }

        private enum ContentType {
            UNIT_TITLE, FIRST_MEANING, SECOND_MEANING;
        }
    }

}
