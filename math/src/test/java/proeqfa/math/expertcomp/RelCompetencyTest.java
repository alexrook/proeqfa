package proeqfa.math.expertcomp;

import org.apache.commons.math3.linear.RealMatrix;
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
        dd_evaluationRate = 0.01;
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testFromArray() throws Exception {
        RelCompetency instance = RelCompetency.fromArray(dd_expertsRelCompetency01, dd_evaluationRate);
        TestUtils.assert2DArrayEquals(
                Array2DUtils.toPrimitive(instance.getMatrix()),
                dd_expertsRelCompetency01, 0);
        TestUtils.printMatrix(instance.getMatrix(), out);
    }

    @Test
    public void testGetRelCompetencyVector() throws Exception {
        RelCompetency instance =
                RelCompetency.fromArray(dd_expertsRelCompetency01, dd_evaluationRate);
        TestUtils.assert2DArrayEquals(
                Array2DUtils.toPrimitive(instance.getMatrix()),
                dd_expertsRelCompetency01, 0);
        //TestUtils.printMatrix(instance.getMatrix(), out);

        instance.setListener(new RelCompetency.ICalcListener() {
            @Override
            public void onCalcStep(int step, RealMatrix stepResult) {
                out.println("step=" + step);
                TestUtils.printMatrix(stepResult.transpose().getData(), out);
            }
        });

        RealMatrix m = instance.getRelCompetencyVector();
        TestUtils.printMatrix(m.transpose().getData(), out);
    }
}