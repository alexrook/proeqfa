package proeqfa.math.estimation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static proeqfa.math.estimation.TestUtils.*;

import proeqfa.math.estimation.u.*;
import proeqfa.math.rankestjoin.RankEstimationTest;

/**
 * @author moroz
 */
public class EstimationMatrixTest {

    private static final Logger log = LogManager.getLogger(EstimationMatrixTest.class);

    private ByteArrayOutputStream baos;
    private PrintStream out;

    List<IEstimationTestData> testDataList;

    public EstimationMatrixTest() {
    }

    @Before
    public void setUp() {

        baos = new ByteArrayOutputStream();
        out = new PrintStream(baos);

        testDataList = new ArrayList<>();

        IEstimationTestData docData01 = new IEstimationTestData() {
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
            public double[][] getExpectedEstimationMatrix() {
                return new double[][]{
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

        IEstimationTestData docData02 = new IEstimationTestData() {// A.1.3
            @Override
            public ThreeLogicValues getLogicValues() {
                return ThreeLogicValues.getView3();//more=1, less=0, same=0.5d
            }

            @Override
            public List<Double[][]> getPairwiseCompareHighEchelonArrays() {
                List<Double[][]> ret = new ArrayList<>();
                ret.add(new Double[][]{
                        {1d, 1d},
                        {1d}
                });
                ret.add(new Double[][]{
                        {1d, 1d},
                        {0d}
                });
                ret.add(new Double[][]{
                        {0d, 1d},
                        {1d}
                });
                return ret;
            }

            @Override
            public double[][] getExpectedEstimationMatrix() {
                return new double[][]{
                        {(1 / 2d), (2 / 3d), (1d)},
                        {(1 / 3d), (1 / 2d), (2 / 3d)},
                        {(0d), (1 / 3d), (1 / 2d)}
                };

            }

            @Override
            public String getDesc() {
                return "test data from Приложение А.1.3";
            }

            @Override
            public int getObjCount() {
                return 3;
            }
        };


        testDataList.add(docData01);
        testDataList.add(docData02);
        //   testDataList.add(new LavRelativeImportanceVectorTestData01());
        testDataList.add(new LavRelativeImportanceVectorTestData03());

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
        log.debug(new String(baos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8));
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
            double[][] actual = estimationMatrix.getResultMatrix();

            assert2DArrayEquals(testData.getExpectedEstimationMatrix(), actual, 0.0000000001);

        }
        log.debug(new String(baos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8));
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
        double[][] ret = estimationMatrix.getResultMatrix();

        double[][] expectedEstimationMatrix = {
                {(3 / 6d), (5 / 6d), (4 / 6d)},
                {(1 / 6d), (3 / 6d), (1 / 6d)},
                {(2 / 6d), (5 / 6d), (3 / 6d)}
        };

        assert2DArrayEquals(expectedEstimationMatrix, ret, 0.0000000001);

        out.println("---estimation matrix--");
        TestUtils.printMatrix(ret, out);

        log.debug(new String(baos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8));

    }

}
