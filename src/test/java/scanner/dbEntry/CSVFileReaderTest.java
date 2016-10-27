package scanner.dbEntry;

import org.junit.Test;
import scanner.Word;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tom on 10/27/16.
 */
public class CSVFileReaderTest {

    @Test
    public void interpretCSVFile() throws Exception {
        String fileLocation = "src"+ File.separatorChar
                +"test"+ File.separatorChar
                +"java" + File.separator
                +"scanner"+ File.separatorChar
                +"dbEntry"+ File.separatorChar
                +"test0.csv"; //First entry = dog,4,0
        ArrayList<Word> test = DatabaseInput.interpretCSVFile(fileLocation);
        if (test.size() > 0) {
            assertEquals("dog", test.get(0).getWord());
            assertEquals(4.0, test.get(0).getRarity(),0);
            assertFalse(test.get(0).isNum());
        }
    }

    @Test
    public void interpretCSVPhraseFile() throws Exception {
        CSVFileReader csvfr = new CSVFileReader();
        String fileLocation = "src"+ File.separatorChar
                +"test"+ File.separatorChar
                +"java" + File.separator
                +"scanner"+ File.separatorChar
                +"dbEntry"+ File.separatorChar
                +"test1.csv"; //First entry = the dog is cool,4,2,1
        ArrayList<scanner.Phrase> test = DatabaseInput.interpretCSVPhraseFile(fileLocation);
        if (test.size() > 0) {
            assertEquals("the dog is cool", test.get(0).getPhrase());
            assertEquals(4, test.get(0).getWordcount());
            assertEquals(2.0, test.get(0).getRarity(),0);
            assertTrue(test.get(0).isNum());
        }
    }

}
