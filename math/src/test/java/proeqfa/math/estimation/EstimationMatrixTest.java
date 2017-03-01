package proeqfa.math.estimation;

import org.junit.*;
import static org.junit.Assert.*;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import static proeqfa.math.estimation.TestUtils.*;

/**
 *
 * @author moroz
 */
public class EstimationMatrixTest {

    List<IEstimationTestData> testDataList;

    public EstimationMatrixTest() {
    }

    @Before
    public void setUp() {

        testDataList = new ArrayList<>();

        IEstimationTestData docData = new IEstimationTestData() {
            @Override
            public ThreeLogicValues getLogicValues() {
                return ThreeLogicValues.getView3();//more=1, less=0, same=0.5d
            }

            @Override
            public List<Double[][]> getPairwiseCompareHighEchelonArrays() {
                List<Double[][]> ret = new ArrayList<>();
                ret.add(new Double[][]{
                    {1d, 1d},
                    {0d}
                });
                ret.add(new Double[][]{
                    {0.5d, 0.5d},
                    {0.5d}
                });
                ret.add(new Double[][]{
                    {1d, 0.5d},
                    {0d}
                });
                return ret;
            }

            @Override
            public Double[][] getExpectedEstimationMatrix() {
                return new Double[][]{
                    {(3 / 6d), (5 / 6d), (4 / 6d)},
                    {(1 / 6d), (3 / 6d), (1 / 6d)},
                    {(2 / 6d), (5 / 6d), (3 / 6d)}
                };

            }

            @Override
            public String getDesc() {
                return "test data from Приложение А";
            }

            @Override
            public int getObjCount() {
                return 3;
            }
        };

        testDataList.add(docData);

    }

    @Test
    public void printTestData() {

        for (IEstimationTestData testData : testDataList) {
            for (Double[][] matrixD : testData.getPairwiseCompareHighEchelonArrays()) {
                PairwiseCompareMatrix m = TestUtils.createFromHighEchelonArray(matrixD, testData.getLogicValues());
                out.println("---test data pairwise matrix--");
                TestUtils.printPairwiseMatrix(m, out);
            }
        }

    }

    @Test
    public void testAddPairwiseCompare02() {//Данные других пользователей
        for (IEstimationTestData testData : testDataList) {
            out.println(testData.getDesc());
            EstimationMatrix estimationMatrix = new EstimationMatrix(testData.getObjCount(),
                    testData.getLogicValues());
            for (Double[][] matrixD : testData.getPairwiseCompareHighEchelonArrays()) {
                PairwiseCompareMatrix m = TestUtils.createFromHighEchelonArray(matrixD, testData.getLogicValues());
                estimationMatrix.addPairwiseCompare(m);
            }
            estimationMatrix.calculate();
            Double[][] actual = estimationMatrix.getResultMatrix();

            assert2DArrayEquals(testData.getExpectedEstimationMatrix(),actual, 0.0000000001);

        }

    }

    /**
     * Test of addPairwiseCompare method, of class ExpectationMatrix.
     */
    @Test
    public void testAddPairwiseCompare01() {//Данные примера приложения А

        ThreeLogicValues logicValuesView3 = ThreeLogicValues.getView3();

        EstimationMatrix estimationMatrix = new EstimationMatrix(3, logicValuesView3);

        PairwiseCompareMatrix matrix1 = new PairwiseCompareMatrix(3, logicValuesView3);
        matrix1.setMore(0, 1);
        matrix1.setMore(0, 2);
        matrix1.setLess(1, 2);
//        out.println("---matrix1---");
//        TestUtils.printPairwiseMatrix(matrix1);
//        out.println("---more decision--");
        estimationMatrix.addPairwiseCompare(matrix1);
        //  TestUtils.printMatrix(estimationMatrix.moreDecisionMarix, out);

        PairwiseCompareMatrix matrix2 = new PairwiseCompareMatrix(3, logicValuesView3);
        matrix2.setSame(0, 1);
        matrix2.setSame(0, 2);
        matrix2.setSame(1, 2);
//        out.println("---matrix2---");
//        TestUtils.printPairwiseMatrix(matrix2);
//        out.println("---more decision--");
        estimationMatrix.addPairwiseCompare(matrix2);
        //  TestUtils.printMatrix(estimationMatrix.moreDecisionMarix,out);

        PairwiseCompareMatrix matrix3 = new PairwiseCompareMatrix(3, logicValuesView3);
        matrix3.setMore(0, 1);
        matrix3.setSame(0, 2);
        matrix3.setLess(1, 2);
//        out.println("---matrix3---");
//        TestUtils.printPairwiseMatrix(matrix3);
//        out.println("---more decision--");
        estimationMatrix.addPairwiseCompare(matrix3);
        // TestUtils.printMatrix(estimationMatrix.moreDecisionMarix, out);

        estimationMatrix.calculate();
        Double[][] ret = estimationMatrix.getResultMatrix();

        Double[][] expectedEstimationMatrix = {
            {(3 / 6d), (5 / 6d), (4 / 6d)},
            {(1 / 6d), (3 / 6d), (1 / 6d)},
            {(2 / 6d), (5 / 6d), (3 / 6d)}
        };

        assert2DArrayEquals(expectedEstimationMatrix, ret, 0.0000000001);

        out.println("---estimation matrix--");
        TestUtils.printMatrix(ret, out);

    }

}
