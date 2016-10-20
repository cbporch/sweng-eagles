package scanner.dbEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Chris on 10/13/2016.
 * jUnit Test for DatabaseInput class
 */
public class DatabaseInputTest {

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void closeStream() {
        System.setOut(null);
    }

    @Test
    public void insertWords() throws Exception {
        Database.insertWords(BCrypt.hashpw("test", BCrypt.gensalt(10)), 10);
        assertEquals(outputStream.toString().substring(0, 7), "\ninsert");
    }

    @Test
    public void insertPhrases() throws Exception {
        Database.insertPhrases(BCrypt.hashpw("testphrase", BCrypt.gensalt(10)), 10, 2);
        assertEquals(outputStream.toString().substring(0, 7), "\ninsert");
    }

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

    @Test
    public void interpretCSVFileWord() throws Exception {
        String fileLocation = "/Users/Tom/Desktop/test.csv";    //First Line in it was "dog,is,the,greatest"
        DatabaseInput dbi = new DatabaseInput();
        ArrayList<String> test = dbi.interpretCSVFile(fileLocation); //Wasn't sure where to call this from.
        assertEquals(test.get(0), "dog");
        assertEquals(test.get(1), "is");
        assertEquals(test.get(2), "the");
        assertEquals(test.get(3), "greatest");
    }

    @Test
    public void interpretCSVFilePhrase() throws Exception {
        String fileLocation = "/Users/Tom/Desktop/testPhrase.csv";  //First Line in it was "the dog is cool, but tom is cooler"
        DatabaseInput dbi = new DatabaseInput();
        ArrayList<String> test = dbi.interpretCSVFile(fileLocation); //Wasn't sure where to call this from.
        assertEquals(test.get(0), "the dog is cool");
        // assertEquals();
    }
}