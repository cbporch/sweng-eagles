package scanner.filtering;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Chris on 10/11/2016.
 *
 */
public class LuceneStemmerTest {
    LuceneStemmer ls;
    @Before
    public void setUp() throws Exception{
         ls = new LuceneStemmer();
    }

    @Test
    public void testStemWords() throws IOException {
        ArrayList<String> input = new ArrayList<>();
        input.add("runs");
        ArrayList<String> output = ls.stemWords(input);
        assertEquals("run", output.get(0));
    }

    @Test
    public void testStemPhrase() throws IOException {
        String input = "The dog runs";

        String output = ls.stemPhrase(input);
        assertEquals("dogrun", output);
    }
}