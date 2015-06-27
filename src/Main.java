import org.xml.sax.SAXException;
import pojo.Unit;
import util.Parser;
import util.Serializer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by Ruben on 27.06.2015.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You must specify a file.");
        } else {
            Path path = Paths.get(args[0]);

            try {
                HashMap<String, Unit> units = Parser.parse(path);
                String pathString = path.toFile().getAbsolutePath();
                pathString = pathString.substring(0, pathString.lastIndexOf(File.separator)) + "/convertedBackup.xml";

                Serializer.serialize(units, Paths.get(pathString));

                System.out.println("Success!");
            } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
