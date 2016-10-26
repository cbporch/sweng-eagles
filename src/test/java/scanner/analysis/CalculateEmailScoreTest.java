package scanner.analysis;

import org.junit.Assert;
import org.junit.Test;
import scanner.Doublet;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Chris on 10/24/2016.
 */
public class CalculateEmailScoreTest {
    private static final double DELTA = 1e-10;

    @Test
    public void calculate() throws Exception {
        ArrayList<Doublet> pairs = new ArrayList<>();

        Assert.assertEquals(0.0, CalculateEmailScore.calculate(pairs), DELTA);

        pairs.add(new Doublet(0, 0));
        Assert.assertEquals(0.0, CalculateEmailScore.calculate(pairs), DELTA);

        pairs.add(new Doublet(2, 1));
        Assert.assertEquals(0.6666666666666, CalculateEmailScore.calculate(pairs), DELTA);
    }

}