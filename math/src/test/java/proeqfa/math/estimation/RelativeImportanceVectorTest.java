package proeqfa.math.estimation;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;
import proeqfa.math.commons.Array2DUtils;
import proeqfa.math.estimation.u.LavRelativeImportanceVectorTestData03;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static proeqfa.math.estimation.TestUtils.assert2DArrayEquals;

/**
 * @author moroz
 */
public class RelativeImportanceVectorTest extends TestBase {

    List<IRelativeImportanceVectorTestData> testDataList;

    public RelativeImportanceVectorTest() {
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();

        testDataList = new ArrayList<>();

        testDataList.add(new LavRelativeImportanceVectorTestData03());

    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLambda01() {

        List<Double[][]> pairM = new ArrayList<>();
        pairM.add(new Double[][]{
                {0d, 1d},
                {1d}
        });
        pairM.add(new Double[][]{
                {0d, 1d},
                {1d}
        });

        pairM.add(new Double[][]{
                {-1d, 1d},
                {1d}
        });

        EstimationMatrix eM = new EstimationMatrix(3, ThreeLogicValues.getView2());
        for (Double[][] matrixD : pairM) {
            PairwiseCompareMatrix m = TestUtils.createFromHighEchelonArray(matrixD, ThreeLogicValues.getView2());
            //   TestUtils.printPairwiseMatrixHumanFriendly(m, ThreeLogicValues.getView2(), out);
            eM.addPairwiseCompare(m);
        }
        eM.calculate();
        double[][] actual = eM.getResultMatrix();
        out.println("---1,-1,0---");

        TestUtils.printMatrix(actual, out);

        RelativeImportanceVector instance
                = new RelativeImportanceVector(3, 0.001);

        instance.calculate(actual);
        TestUtils.printMatrix(instance.getRelativeImportanceVector().getData(), out);

    }

    @Test
    public void testLambda02() {

        List<Double[][]> pairM = new ArrayList<>();
        pairM.add(new Double[][]{
                {0.5d, 1d},
                {1d}
        });

        pairM.add(new Double[][]{
                {0.5d, 1d},
                {1d}
        });

        pairM.add(new Double[][]{
                {0d, 1d},
                {1d}
        });

        EstimationMatrix eM = new EstimationMatrix(3, ThreeLogicValues.getView3());
        for (Double[][] matrixD : pairM) {
            PairwiseCompareMatrix m = TestUtils.createFromHighEchelonArray(matrixD, ThreeLogicValues.getView3());
            //     TestUtils.printPairwiseMatrixHumanFriendly(m, ThreeLogicValues.getView3(), out);
            eM.addPairwiseCompare(m);
        }
        eM.calculate();
        double[][] actual = eM.getResultMatrix();
        out.println("---1,0,0.5---");

        TestUtils.printMatrix(actual, out);
        RelativeImportanceVector instance
                = new RelativeImportanceVector(3, 0.001);

        instance.calculate(actual);
        TestUtils.printMatrix(instance.getRelativeImportanceVector().getData(), out);

    }

    @Test
    public void testLambda03() {

        List<Double[][]> pairM = new ArrayList<>();
        pairM.add(new Double[][]{
                {1d, 2d},
                {2d}
        });

        pairM.add(new Double[][]{
                {1d, 2d},
                {2d}
        });

        pairM.add(new Double[][]{
                {0d, 2d},
                {2d}
        });

        EstimationMatrix eM = new EstimationMatrix(3, ThreeLogicValues.getView1());
        for (Double[][] matrixD : pairM) {
            PairwiseCompareMatrix m = TestUtils.createFromHighEchelonArray(matrixD, ThreeLogicValues.getView1());
            //     TestUtils.printPairwiseMatrixHumanFriendly(m, ThreeLogicValues.getView1(), out);
            eM.addPairwiseCompare(m);
        }
        eM.calculate();
        double[][] actual = eM.getResultMatrix();
        out.println("---2,1,0---");

        TestUtils.printMatrix(actual, out);
        RelativeImportanceVector instance
                = new RelativeImportanceVector(3, 0.001);

        instance.calculate(actual);
        TestUtils.printMatrix(instance.getRelativeImportanceVector().getData(), out);

    }

    @Test
    public void userTestsCalculate01() {
        for (IRelativeImportanceVectorTestData testData : testDataList) {

            EstimationMatrix eM = new EstimationMatrix(testData.getObjCount(), testData.getLogicValues());

            for (Double[][] matrixD : testData.getPairwiseCompareHighEchelonArrays()) {
                PairwiseCompareMatrix m = TestUtils.createFromHighEchelonArray(matrixD, testData.getLogicValues());
                eM.addPairwiseCompare(m);
            }
            eM.calculate();
            double[][] actual = eM.getResultMatrix();

            assert2DArrayEquals(testData.getExpectedEstimationMatrix(), actual, 0.0000000001);

            RelativeImportanceVector instance
                    = new RelativeImportanceVector(testData.getObjCount(), testData.getEvaluationRate());
            instance.calculate(eM.getResultMatrix());
            TestUtils.printMatrix(instance.getRelativeImportanceVector().getData(), out);
            assert2DArrayEquals(testData.getExpectedRelativeImportanceVector().getData(),
                    instance.getRelativeImportanceVector().getData(), 0.05);

        }

    }

    @Test
    public void userTestsCalculate02() {
        for (IRelativeImportanceVectorTestData testData : testDataList) {

            ThreeLogicValues newValues
                    = testData.getLogicValues().equals(ThreeLogicValues.getView3())
                    ? ThreeLogicValues.getView1() : ThreeLogicValues.getView3();

            EstimationMatrix eM = new EstimationMatrix(testData.getObjCount(),
                    newValues);

            for (Double[][] matrixD : testData.getPairwiseCompareHighEchelonArrays()) {
                PairwiseCompareMatrix m = TestUtils.createFromHighEchelonArray(matrixD, testData.getLogicValues());
                PairwiseCompareMatrix n = TestUtils.toOtherLogicValues(m, newValues);
                eM.addPairwiseCompare(n);
            }

            eM.calculate();
            double[][] actual = eM.getResultMatrix();
            //  TestUtils.printMatrix(actual, out);

            RelativeImportanceVector instance
                    = new RelativeImportanceVector(testData.getObjCount(), testData.getEvaluationRate());

            instance.calculate(actual);
            TestUtils.printMatrix(instance.getRelativeImportanceVector().getData(), out);
            assert2DArrayEquals(testData.getExpectedRelativeImportanceVector().getData(),
                    instance.getRelativeImportanceVector().getData(), 0.05);

        }

    }

    /**
     * Test of getEarray method, of class RelativeImportanceVector.
     */
    @Test
    public void testGetEarray() {
        out.println("getEarray");
        double[] expResult = {1, 1, 1};
        double[] result = Array2DUtils.getEarray(3);
        assertArrayEquals(expResult, result, 0);
    }

    /**
     * Test of calculate method, of class RelativeImportanceVector.
     */
    @Test
    public void testCalculate() {
        out.println("calculate");
        final int objCount = 3;

        Double[][] estimationMatrix = {
                {(3 / 6d), (5 / 6d), (4 / 6d)},
                {(1 / 6d), (3 / 6d), (1 / 6d)},
                {(2 / 6d), (5 / 6d), (3 / 6d)}
        };

        TestUtils.printMatrix(estimationMatrix, out);
        RelativeImportanceVector instance = new RelativeImportanceVector(objCount, 0.001);

        instance.calculate(Array2DUtils.toPrimitive(estimationMatrix));

        RealMatrix vector = instance.getRelativeImportanceVector();

        //     TestUtils.printMatrix(Array2DUtils.toObject(vector.getData()), out);
    }

}
