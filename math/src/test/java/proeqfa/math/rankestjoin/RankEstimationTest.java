package proeqfa.math.rankestjoin;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import proeqfa.math.commons.Array2DUtils;
import proeqfa.math.estimation.*;
import proeqfa.math.rank.NaturalOrderPosition2Rank;
import proeqfa.math.rank.RankChain;
import proeqfa.math.rank.RankChainTest;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Created by moroz on 17.03.17.
 */
public class RankEstimationTest {

    private static final Logger log= LogManager.getLogger(RankEstimationTest.class);

    private ByteArrayOutputStream baos;
    private PrintStream out;

    //-- doc рис. А.6
    final int DD_OBJ_COUNT = 3; //dd -> doc data
    final ThreeLogicValues DD_LOGIC_VALUES = ThreeLogicValues.getView3();
    private double DD_EVALUATION_RATE = 0.001;
    RankChain dd_expert1, dd_expert2, dd_expert3;

    RealMatrix dd_ri_vector =
            MatrixUtils.createColumnRealMatrix(new double[]{0.5, 0.349, 0.151}); // relative importance vector

    double[][] dd_pw_expected1 = { //pw -> expected pairwise compare matrix
            {0.5, 1, 1},
            {0, 0.5, 1},
            {0, 0, 0.5}
    };
    double[][] dd_pw_expected2 = {
            {0.5, 1, 1},
            {0, 0.5, 0},
            {0, 1, 0.5}
    };
    double[][] dd_pw_expected3 = {
            {0.5, 0, 1},
            {1, 0.5, 1},
            {0, 0, 0.5}
    };
    //---

    @Before
    public void setUp() {
        baos = new ByteArrayOutputStream();
        out= new PrintStream(baos);
        //doc рис. А.6
        dd_expert1 = RankChain.fromArray(
                RankChainTest.getRankChain("O1(1) >O2(2) >O3(3)")
                , new NaturalOrderPosition2Rank());

        dd_expert2 = RankChain.fromArray(
                RankChainTest.getRankChain("O1(1) >O3(2) >O2(3)")
                , new NaturalOrderPosition2Rank());

        dd_expert3 = RankChain.fromArray(
                RankChainTest.getRankChain("O2(1) >O1(2) >O3(3)")
                , new NaturalOrderPosition2Rank());

    }


    @Test
    public void test_dd_to_RelativeImportanceVector01() throws Exception {

        RankEstimation instance = new RankEstimation(DD_OBJ_COUNT, DD_LOGIC_VALUES);
        instance.add(dd_expert1);
        instance.add(dd_expert2);
        instance.add(dd_expert3);

        EstimationMatrix estM = instance.get();
        estM.calculate();
        out.println("--");
        TestUtils.printMatrix(estM.getResultMatrix(), out);

        RelativeImportanceVector vector = new RelativeImportanceVector(DD_OBJ_COUNT, DD_EVALUATION_RATE);

        vector.setCalcListener(new RelativeImportanceVector.ICalcListener() {
            @Override
            public void onCalcStep(int step, RealMatrix stepResult) {
                out.println("relative importance vector calc step=" + step);
                TestUtils.printMatrix(stepResult.getData(), out, 3, 3);
            }
        });

        vector.calculate(estM.getResultMatrix());
        RealMatrix actualVector = vector.getRelativeImportanceVector();
        out.println("actual vector");
        TestUtils.printMatrix(actualVector.getData(), out, 3, 3);
        TestUtils.assert2DArrayEquals(dd_ri_vector.getData(),
                actualVector.getData(), DD_EVALUATION_RATE);
        log.debug(new String(baos.toByteArray(),java.nio.charset.StandardCharsets.UTF_8));
    }

    @Test
    public void test_dd_toPairwiseCompareMatrix01() {

        PairwiseCompareMatrix actPaMatrix1 =
                RankEstimation.toPairwiseCompareMatrix(dd_expert1, DD_OBJ_COUNT, DD_LOGIC_VALUES);
        PairwiseCompareMatrix actPaMatrix2 =
                RankEstimation.toPairwiseCompareMatrix(dd_expert2, DD_OBJ_COUNT, DD_LOGIC_VALUES);
        PairwiseCompareMatrix actPaMatrix3 =
                RankEstimation.toPairwiseCompareMatrix(dd_expert3, DD_OBJ_COUNT, DD_LOGIC_VALUES);

        out.println("pairwise matrix from expert1:");
        TestUtils.printPairwiseMatrix(actPaMatrix1, out);
        out.println("pairwise matrix from expert2:");
        TestUtils.printPairwiseMatrix(actPaMatrix2, out);
        out.println("pairwise matrix from expert3:");
        TestUtils.printPairwiseMatrix(actPaMatrix3, out);


        TestUtils.assert2DArrayEquals(
                dd_pw_expected1,
                Array2DUtils.toPrimitive(actPaMatrix1.getMatrix()), 0d);
        TestUtils.assert2DArrayEquals(
                dd_pw_expected2,
                Array2DUtils.toPrimitive(actPaMatrix2.getMatrix()), 0d);
        TestUtils.assert2DArrayEquals(
                dd_pw_expected3,
                Array2DUtils.toPrimitive(actPaMatrix3.getMatrix()), 0d);

        log.debug(new String(baos.toByteArray(),java.nio.charset.StandardCharsets.UTF_8));
    }

}