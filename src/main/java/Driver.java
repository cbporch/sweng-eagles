import opennlp.tools.coref.mention.Parse;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.strings.FileConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.mindrot.jbcrypt.BCrypt;
import org.xml.sax.SAXException;
import scanner.LoginGUI;
import scanner.analysis.EmailTextGUI;
import scanner.dbEntry.DatabaseInput;
import scanner.filtering.Hasher;
import org.apache.tika.parser.AutoDetectParser;
import scanner.filtering.TikaWrapper;

import java.io.*;

/**
 * Created by Chris on 10/28/2016.
 */
public class Driver {

    public static void main(String[] args){
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.main(null);
    }

}
