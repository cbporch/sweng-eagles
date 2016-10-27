package scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Steve on 10/26/2016.
 */
public class DoubletTest
{
    public Doublet testDoublet;
    @Before
    public void setUp()
    {
        testDoublet = new Doublet(1, 4);
    }

    @Test
    public void getNumConf() throws Exception
    {
        Assert.assertEquals(1, testDoublet.getNumConf());
    }

    @Test
    public void getNumNorm() throws Exception
    {
        Assert.assertEquals(4, testDoublet.getNumNorm());
    }

}