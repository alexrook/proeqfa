package proeqfa.math.expertcomp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;
import proeqfa.math.commons.Array2DUtils;
import proeqfa.math.estimation.TestUtils;

/**
 * Created by moroz on 21.03.17.
 */
public class RelCompetencyTest extends TestBase {

    private double[][] dd_expertsRelCompetency01;
    private double dd_evaluationRate;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        dd_expertsRelCompetency01 = new double[][]{
                {1, 1, 1},
                {0, 1, 0},
                {1, 0, 1}
        };
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void fromArray() throws Exception {
        RelCompetency instance = RelCompetency.fromArray(dd_expertsRelCompetency01, dd_evaluationRate);
        TestUtils.assert2DArrayEquals(
                Array2DUtils.toPrimitive(instance.getMatrix()),
                dd_expertsRelCompetency01, 0);
        TestUtils.printMatrix(instance.getMatrix(), out);
    }


}