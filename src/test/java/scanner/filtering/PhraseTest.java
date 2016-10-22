package scanner.filtering;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scanner.Phrase;

/**
 * Created by chris on 10/18/16.
 * Modified on 10/22/16
 *
 * This class tests the methods of the Phrase class using JUnit.
 *
 */
public class PhraseTest {
    private Phrase testPhrase;
    @Before
    public void setUp() throws Exception {
        testPhrase = new Phrase("test text", 2, 1,3,4,true);
    }

    @Test
    public void getRarity() throws Exception {
        Assert.assertEquals(1, testPhrase.getRarity());
    }

    @Test
    public void setRarity() throws Exception {
        Phrase p = new Phrase("phrase", 1);
        p.setRarity(0);
        Assert.assertEquals(0, p.getRarity());

    }

    @Test
    public void isNum() throws Exception {
        Assert.assertTrue(testPhrase.isNum());
    }

    @Test
    public void setNum() throws Exception {
        Phrase p = new Phrase("test", 1);
        p.setNum(false);
        Assert.assertFalse(p.isNum());
    }

    @Test
    public void setConf() throws Exception {
        Phrase p = new Phrase("test", 1);
        p.setConf(0);
        Assert.assertEquals(0, p.getConf());
    }

    @Test
    public void getConf() throws Exception {
        Assert.assertEquals(3, testPhrase.getConf());
    }

    @Test
    public void setNorm() throws Exception {
        Phrase p = new Phrase("test", 1);
        p.setNorm(3);
        Assert.assertEquals(3, p.getNorm());
    }

    @Test
    public void getNorm() throws Exception {
        Assert.assertEquals(4, testPhrase.getNorm());
    }

    @Test
    public void setPhrase() throws Exception {
        Phrase p = new Phrase("", 0);
        p.setPhrase("test this one");
        Assert.assertTrue(p.getPhrase().equals("test this one"));
    }

    @Test
    public void getPhrase() throws Exception {
        Assert.assertTrue(testPhrase.getPhrase().equals("test text"));
    }

    @Test
    public void setWordcount() throws Exception {
        Phrase p = new Phrase("test", 1);
        p.setWordcount(17);
        Assert.assertEquals(17, p.getWordcount());
    }

    @Test
    public void getWordcount() throws Exception {
        Assert.assertEquals(2, testPhrase.getWordcount());
    }

}