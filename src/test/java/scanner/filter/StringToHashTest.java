package scanner.filter;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by Chris on 10/11/2016.
 *
 */
public class StringToHashTest {

    /**
     * Checks the getHashes() method with only a single word as the input
     */
    @Test
    public void testGetHashesSingle() throws Exception {
        ArrayList<String> input = new ArrayList<>();
        input.add("the");
        input.add("dogs");
        boolean phrases = false;
        ArrayList<String> output = StringToHash.getHashes(input);
        assertTrue(Hasher.checkHashBCrypt("dog", output.get(0)));
    }

    /**
     * Checks the getHashes() method with multiple words as the input
     */
    @Test
    public void testGetHashesPhrases() throws Exception {
        ArrayList<Phrase> input = new ArrayList<>();
        ArrayList<Phrase> output = new ArrayList<>();
        input.add(new Phrase("the dog barked"));
        input.add(new Phrase("He runs home quickly"));
        boolean phrases = true;
        for(Phrase p : input) {
            output = StringToHash.getPhraseHashes(input);
        }
        assertTrue(Hasher.checkHashBCrypt("dogbarked", output.get(0).getPhrase()));
        assertTrue(Hasher.checkHashBCrypt("herunhomequickly", output.get(1).getPhrase()));
    }
}