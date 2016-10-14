package scanner.dbEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created by Chris on 10/13/2016.
 * jUnit Test for DatabaseInput class
 */
public class DatabaseInputTest {
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Before
    public void setStream(){
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void closeStream(){
        System.setOut(null);
    }

    @Test
    public void insertWords() throws Exception {
        DatabaseInput.insertWords(BCrypt.hashpw("test", BCrypt.gensalt(10)), 10);
        assertTrue(outputStream.toString().equals("insert completed\r\n"));
    }

    @Test
    public void insertPhrases() throws Exception {
        DatabaseInput.insertPhrases(BCrypt.hashpw("testphrase", BCrypt.gensalt(10)), 10, 2);
        assertTrue(outputStream.toString().equals("insert completed\r\n"));
    }

    @Test
    public void getWords() throws Exception {
        DatabaseInput.getWords();
        assertTrue(outputStream.toString().equals("select words completed\r\n"));
    }

    @Test
    public void getPhrases() throws Exception {
        DatabaseInput.getPhrases();
        assertTrue(outputStream.toString().equals("select phrases completed\r\n"));
    }

}