package scanner.analysis;

import org.junit.Assert;
import org.junit.Test;
import scanner.Doublet;

import java.util.ArrayList;

/**
 * Created by Chris on 10/24/2016.
 *
 */
public class CalculateEmailScoreTest {
    private static final double DELTA = 1e-10;

    @Test
    public void calculate() throws Exception {
        ArrayList<Doublet> pairs = new ArrayList<>();

        Assert.assertEquals(0.0, CalculateEmailScore.calculate(pairs), DELTA);

        pairs.add(new Doublet(90, 10));
        Assert.assertEquals(0.9, CalculateEmailScore.calculate(pairs), DELTA);

        pairs.add(new Doublet(1, 99));
        Assert.assertEquals(0.9, CalculateEmailScore.calculate(pairs), DELTA);

        pairs.add(new Doublet(6, 3));
        Assert.assertEquals(0.9, CalculateEmailScore.calculate(pairs), DELTA);
    }

}