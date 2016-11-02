package scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scanner.filtering.Hasher;

import static org.junit.Assert.*;

/**
 * Created by chris on 10/22/16.
 *
 * This class tests the methods of the Word class using JUnit.
 */
public class WordTest {
    private Word testWord;
    private static final double DELTA = 1e-15;
    @Before
    public void setUp() throws Exception {
        testWord = new Word("test", 1, 1, 4, 2);
    }

    @Test
    public void getWord() throws Exception {
        Assert.assertTrue(Hasher.hashSHA("test").equals(testWord.getWord()));
    }

    @Test
    public void getRarity() throws Exception {
        Assert.assertEquals(1, testWord.getRarity(), DELTA);
    }

    @Test
    public void isNum() throws Exception {
        Assert.assertTrue(testWord.isNum());
    }

    @Test
    public void getConf() throws Exception {
        Assert.assertEquals(4, testWord.getConf());
    }

    @Test
    public void getNorm() throws Exception {
    Assert.assertEquals(2, testWord.getNorm());
    }

    @Test
    public void setWord() throws Exception {
        Word w = new Word("test", 1, 1);
        w.setWord("word");
        Assert.assertTrue(w.getWord().equals(Hasher.hashSHA("word")));
    }

    @Test
    public void setRarity() throws Exception {
        Word w = new Word("test", 1, 1);
        w.setRarity(0);
        Assert.assertEquals(0, w.getRarity(), DELTA);
    }

    @Test
    public void setNum() throws Exception {
        Word w = new Word("test", 1, 1);
        w.setNum(0);
        Assert.assertFalse(w.isNum());
    }

    @Test
    public void setNorm() throws Exception {
        Word w = new Word("test", 1, 1);
        w.setNorm(0);
        Assert.assertEquals(0, w.getNorm());
    }

    @Test
    public void setConf() throws Exception {
        Word w = new Word("test", 1, 1);
        w.setConf(2);
        Assert.assertEquals(2, w.getConf());
    }

}