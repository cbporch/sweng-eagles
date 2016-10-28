package scanner.dbEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Chris on 10/13/2016.
 * jUnit Test for DatabaseInput class
 */
public class DatabaseInputTest {
/*
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void closeStream() {
        System.setOut(null);
    }

    /*@Test
    public void insertWords() throws Exception {
        Database.insertWords(BCrypt.hashpw("test", BCrypt.gensalt(10)), 10);
        assertEquals(outputStream.toString().substring(0, 7), "\ninsert");
    }*/

   /* @Test
    public void insertPhrases() throws Exception {
        Database.insertPhrases(BCrypt.hashpw("testphrase", BCrypt.gensalt(10)), 10, 2);
        assertEquals(outputStream.toString().substring(0, 7), "\ninsert");
    }*/
/*
    @Test
    public void getWords() throws Exception {
        Database.getWords();
        assertTrue(outputStream.toString().equals("select words completed" + System.lineSeparator()));
    }

    @Test
    public void getPhrases() throws Exception {
        Database.getPhrases();
        assertTrue(outputStream.toString().equals("select phrases completed" + System.lineSeparator()));
    }
*/
//    @Test
//    public void interpretCSVFile() throws Exception {
//        String fileLocation = "src"+ File.separatorChar
//                +"test"+ File.separatorChar
//                +"java" + File.separator
//                +"scanner"+ File.separatorChar
//                +"dbEntry"+ File.separatorChar
//                +"test0.csv"; //First entry = dog,4,0
//        ArrayList<scanner.Word> test = DatabaseInput.interpretCSVFile(fileLocation);
//        if (test.size() > 0) {
//            assertEquals("dog", test.get(0).getWord());
//            assertEquals(4.0, test.get(0).getRarity(),0);
//            assertFalse(test.get(0).isNum());
//        }
//    }

//    @Test
//    public void interpretCSVPhraseFile() throws Exception {
//        String fileLocation = "src"+ File.separatorChar
//                +"test"+ File.separatorChar
//                +"java" + File.separator
//                +"scanner"+ File.separatorChar
//                +"dbEntry"+ File.separatorChar
//                +"test1.csv"; // First entry = the dog is cool,4,2,1
//        ArrayList<scanner.Phrase> test = DatabaseInput.interpretCSVPhraseFile(fileLocation);
//        if (test.size() > 0) {
//            assertEquals("the dog is cool", test.get(0).getPhrase());
//            assertEquals(4, test.get(0).getWordcount());
//            assertEquals(2.0, test.get(0).getRarity(),0);
//            assertTrue(test.get(0).isNum());
//        }
//    }
}