package scanner.filter;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Chris on 10/11/2016.
 *
 */
public class LuceneStemmerTest {
    @Test
    public void testStemWords() throws IOException {
        ArrayList<String> input = new ArrayList<>();
        input.add("runs");

        ArrayList<String> output = LuceneStemmer.stemWords(input);
        assertEquals("run", output.get(0));
    }

    @Test
    public void testStemPhrase() throws IOException {
        String input = "The dog runs";

        String output = LuceneStemmer.stemPhrase(input);
        assertEquals("dogrun", output);
    }
}