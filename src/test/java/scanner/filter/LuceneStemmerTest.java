package scanner.filter;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Chris on 10/11/2016.
 */
public class LuceneStemmerTest {
    @Test
    public void testStemWords() throws IOException {
        String[] input = new String[]{"runs"};
        LuceneStemmer stemmer = new LuceneStemmer();
        ArrayList<String> output = stemmer.stemWords(input);
        assertEquals("run", output.get(0));
    }

    @Test
    public void testStemPhrase() throws IOException {
        String input = "The dog runs";
        LuceneStemmer stemmer = new LuceneStemmer();
        String output = stemmer.stemPhrase(input);
        assertEquals("dog run", output);
    }
}