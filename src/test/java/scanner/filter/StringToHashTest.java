package scanner.filter;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by Chris on 10/11/2016.
 */
public class StringToHashTest {

    /**
     * Checks the getHashes() method with only a single word as the input
     */
    @Test
    public void testGetHashesSingle() throws Exception {
        String[] input = {"dog"};
        boolean phrases = false;
        StringToHash sth = new StringToHash();
        ArrayList<String> output = sth.getHashes(input,phrases);
        assertEquals("Hashed dog", output.get(0));
    }

    /**
     * Checks the getHashes() method with multiple words as the input
     */
    @Test
    public void testGetHashesPhrases() throws Exception {
        String[] input = {"the", "dog", "barked"};
        boolean phrases = true;
        StringToHash sth = new StringToHash();
        ArrayList<String> output = sth.getHashes(input, phrases);
        assertEquals("Hashed the", output.get(0));
        assertEquals("Hashed dog", output.get(1));
        assertEquals("Hashed barked", output.get(2));
    }
}