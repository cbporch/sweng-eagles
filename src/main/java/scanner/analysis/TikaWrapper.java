package scanner.analysis;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Chris on 12/9/2016.
 */
public class TikaWrapper {

    public FileInputStream stream;
    private String pdfExample = "C:\\Users\\Chris\\IdeaProjects\\sweng-eagles\\docs\\Dan-Smith-ProjectInitiationDocument.pdf";

    public TikaWrapper(){

    }

    public String parseToTXT(String filename) throws IOException, TikaException, SAXException {
        try {
            stream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            System.err.print(e);
        }

        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata meta = new Metadata();
        try {
            parser.parse(stream, handler, meta);
            //System.out.println(handler.toString());
            return handler.toString();
        } catch (Exception e){
            System.err.print(e);
        }
        return "";
    }
}
