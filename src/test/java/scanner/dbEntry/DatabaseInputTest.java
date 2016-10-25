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
    }

    @Test
    public void insertPhrases() throws Exception {
        Database.insertPhrases(BCrypt.hashpw("testphrase", BCrypt.gensalt(10)), 10, 2);
        assertEquals(outputStream.toString().substring(0, 7), "\ninsert");
    }
*/
    @Test
    public void getWords() throws Exception {
        Database.getWords();
        assertTrue(outputStream.toString().equals("select words completed" + System.lineSeparator()));
    }

   /* @Test
    public void getWord() throws Exception {
        Database.insertWords("Spring", .6, 1);
        System.out.println(Database.getWord("Spring"));
        assertTrue(outputStream.toString().equals("select words completed" + System.lineSeparator()));
    }*/

    @Test
    public void getPhrases() throws Exception {
        Database.getPhrases();
        assertTrue(outputStream.toString().equals("select phrases completed" + System.lineSeparator()));
    }

    @Test
    public void interpretCSVFileWord() throws Exception {
        String fileLocation = "src"+ File.separatorChar
                            +"test"+ File.separatorChar
                            +"java" + File.separator
                            +"scanner"+ File.separatorChar
                            +"dbEntry"+ File.separatorChar
                            +"test0.csv";    //First Line in it was "dog,is,the,greatest"
//        DatabaseInput dbi = new DatabaseInput();
        ArrayList<String> test = DatabaseInput.interpretCSVFile(fileLocation);

            assertEquals("dog", test.get(0));
            assertEquals("is", test.get(1));
            assertEquals("the", test.get(2));
            assertEquals("greatest", test.get(3));

    }

    @Test
    public void interpretCSVFilePhrase() throws Exception {
        String fileLocation = "src"+ File.separatorChar
                +"test"+ File.separatorChar
                +"java" + File.separator
                +"scanner"+ File.separatorChar
                +"dbEntry"+ File.separatorChar
                +"test1.csv";  //First Line in it was ""
//        DatabaseInput dbi = new DatabaseInput();
        ArrayList<String> test = DatabaseInput.interpretCSVFile(fileLocation);
        if (test.size() > 0) {
            assertEquals("the dog is cool", test.get(0));
            // assertEquals();
        }
    }
}