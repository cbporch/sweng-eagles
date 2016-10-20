package scanner.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by chris on 10/18/16.
 *
 */
public class PhraseTest {
    private Phrase testPhrase;
    @Before
    public void setUp() throws Exception {
        testPhrase = new Phrase("test text", 2);
    }

    @Test
    public void getPhrase() throws Exception {
        Assert.assertTrue(testPhrase.getPhrase().equals("test text"));
    }

    @Test
    public void getWordcount() throws Exception {
        Assert.assertEquals(2, testPhrase.getWordcount());
    }

}