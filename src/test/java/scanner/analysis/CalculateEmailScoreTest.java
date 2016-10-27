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

        pairs.add(new Doublet(15, 1));
        Assert.assertEquals(0.9375, CalculateEmailScore.calculate(pairs), DELTA);

        pairs.add(new Doublet(1, 15));
        Assert.assertEquals(0.5, CalculateEmailScore.calculate(pairs), DELTA);

        pairs.add(new Doublet(6, 3));
        Assert.assertEquals(2 / 3.0, CalculateEmailScore.calculate(pairs), DELTA);
    }

}