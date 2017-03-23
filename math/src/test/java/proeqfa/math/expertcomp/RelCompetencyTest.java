package proeqfa.math.expertcomp;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;
import proeqfa.math.commons.Array2DUtils;
import proeqfa.math.commons.ICalcListener;
import proeqfa.math.estimation.TestUtils;

/**
 * Created by moroz on 21.03.17.
 */
public class RelCompetencyTest extends TestBase {
    /*dd -> doc test data A.2.1*/
    private double[][] dd_expertsRelCompetency01;
    private double dd_evaluationRate01;
    private double[][] dd_compVector01;

    /*ud */
    private double[][] ud_acad_expertsRelCompetency01;
    private double ud_acad_evaluationRate01;
    private double[][] ud_acad_compVector01;


    @Before
    @Override
    public void setUp() {
        super.setUp();
        //doc data
        dd_expertsRelCompetency01 = new double[][]{
                {1, 1, 1},
                {0, 1, 0},
                {1, 0, 1}
        };
        dd_evaluationRate01 = 0.01;
        dd_compVector01 = new double[][]{
                {0.5},
                {0.01},
                {0.49}
        };
        //user data
        ud_acad_expertsRelCompetency01 = new double[][]{
                {1, 1, 1, 1},
                {0, 1, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        ud_acad_evaluationRate01 = 0.01;
        ud_acad_compVector01 = new double[][]{
                {0.873},
                {0.110},
                {0.008},
                {0.008}
        };
    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testFromArray() throws Exception {
        RelCompetency instance = RelCompetency.fromArray(dd_expertsRelCompetency01, dd_evaluationRate01);
        TestUtils.assert2DArrayEquals(
                Array2DUtils.toPrimitive(instance.getMatrix()),
                dd_expertsRelCompetency01, 0);
        TestUtils.printMatrix(instance.getMatrix(), out);
    }

    @Test
    public void test_dd_GetRelCompetencyVector01() throws Exception {
        RelCompetency instance =
                RelCompetency.fromArray(dd_expertsRelCompetency01, dd_evaluationRate01);
        TestUtils.assert2DArrayEquals(
                Array2DUtils.toPrimitive(instance.getMatrix()),
                dd_expertsRelCompetency01, 0);
        //TestUtils.printMatrix(instance.getMatrix(), out);

        instance.setListener(new ICalcListener() {
            @Override
            public boolean onCalcStep(int step, RealMatrix stepResult) {
                out.println("step=" + step);
                TestUtils.printMatrix(stepResult.transpose().getData(), out);
                return true;
            }
        });

        RealMatrix m = instance.getRelCompetencyVector();
        TestUtils.assert2DArrayEquals(
                m.getData(),
                dd_compVector01, dd_evaluationRate01);
        out.println("competence vector:");
        TestUtils.printMatrix(m.transpose().getData(), out);
    }

    @Test
    public void test_ud_acad_GetRelCompetencyVector01() throws Exception {
        RelCompetency instance =
                RelCompetency.fromArray(ud_acad_expertsRelCompetency01, ud_acad_evaluationRate01);
        TestUtils.assert2DArrayEquals(
                Array2DUtils.toPrimitive(instance.getMatrix()),
                ud_acad_expertsRelCompetency01, 0);
        TestUtils.printMatrix(instance.getMatrix(), out);

        instance.setListener(new ICalcListener() {
            @Override
            public boolean onCalcStep(int step, RealMatrix stepResult) {
                out.println("step=" + step);
                TestUtils.printMatrix(stepResult.transpose().getData(), out);
                return true;
            }
        });

        RealMatrix m = instance.getRelCompetencyVector();
        TestUtils.assert2DArrayEquals(
                m.getData(),
                ud_acad_compVector01, ud_acad_evaluationRate01);
        out.println("competence vector:");
        TestUtils.printMatrix(m.transpose().getData(), out);
    }

}