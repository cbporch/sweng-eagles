package scanner.analysis;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 10/24/2016.
 */
public class TextParserTest {
    @Test
    public void parse() throws Exception {
    TextParser t = new TextParser("Tom");
        Assert.assertEquals(1.0, t.parse(), .0000000001);
    }

}