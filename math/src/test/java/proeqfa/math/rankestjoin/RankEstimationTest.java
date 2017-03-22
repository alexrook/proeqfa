package proeqfa.math.rankestjoin;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;
import proeqfa.math.commons.Array2DUtils;
import proeqfa.math.commons.ICalcListener;
import proeqfa.math.estimation.*;
import proeqfa.math.rank.NaturalOrderPosition2Rank;
import proeqfa.math.rank.RankChain;
import proeqfa.math.rank.RankChainTest;


/**
 * Created by moroz on 17.03.17.
 */
public class RankEstimationTest extends TestBase {

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
    //users data
    final int PD_OBJ_COUNT = 3;
    final ThreeLogicValues PD_LOGIC_VALUES = ThreeLogicValues.getView3();
    RankChain pd_expert1, pd_expert2, pd_expert3, pd_expert4;
    double[][] pd_pw_expected1 = { //pw -> expected pairwise compare matrix
            {0.5, 1, 1},
            {0, 0.5, 1},
            {0, 0, 0.5}
    };
    double[][] pd_pw_expected2 = {
            {0.5, 0, 1},
            {1, 0.5, 1},
            {0, 0, 0.5}
    };
    double[][] pd_pw_expected3 = {
            {0.5, 1, 1},
            {0, 0.5, 0},
            {0, 1, 0.5}
    };
    double[][] pd_pw_expected4 = {
            {0.5, 1, 1},
            {0, 0.5, 1},
            {0, 0, 0.5}
    };
    //
    double[][] pd_estimationmatrix_expected = {
            {0.5, (3 / 4d), 1d},
            {(1 / 4d), 0.5, (3 / 4d)},
            {0d, (1 / 4d), 0.5}
    };
    //---

    @Before
    @Override
    public void setUp() {
        super.setUp();
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

        /*users test data*/
        pd_expert1 = RankChain.fromArray(
                RankChainTest.getRankChain("O1(1) >O2(2) >O3(3)")
                , new NaturalOrderPosition2Rank());

        pd_expert2 = RankChain.fromArray(
                RankChainTest.getRankChain("O2(1) >O1(2) >O3(3)")
                , new NaturalOrderPosition2Rank());

        pd_expert3 = RankChain.fromArray(
                RankChainTest.getRankChain("O1(1) >O3(2) >O2(3)")
                , new NaturalOrderPosition2Rank());

        pd_expert4 = RankChain.fromArray(
                RankChainTest.getRankChain("O1(1) >O2(2) >O3(3)")
                , new NaturalOrderPosition2Rank());

    }

    @After
    @Override
    public void tearDown() {
        super.tearDown();
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

        vector.setCalcListener(new ICalcListener() {
            @Override
            public boolean onCalcStep(int step, RealMatrix stepResult) {
                out.println("relative importance vector calc step=" + step);
                TestUtils.printMatrix(stepResult.getData(), out, 3, 3);
                return true;
            }
        });

        vector.calculate(estM.getResultMatrix());
        RealMatrix actualVector = vector.getRelativeImportanceVector();
        out.println("actual vector");
        TestUtils.printMatrix(actualVector.getData(), out, 3, 3);
        TestUtils.assert2DArrayEquals(dd_ri_vector.getData(),
                actualVector.getData(), DD_EVALUATION_RATE);
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
    }

    @Test
    public void test_userdata_toPairwiseCompareMatrix01() {

        PairwiseCompareMatrix actPaMatrix1 =
                RankEstimation.toPairwiseCompareMatrix(pd_expert1, PD_OBJ_COUNT, PD_LOGIC_VALUES);
        PairwiseCompareMatrix actPaMatrix2 =
                RankEstimation.toPairwiseCompareMatrix(pd_expert2, PD_OBJ_COUNT, PD_LOGIC_VALUES);
        PairwiseCompareMatrix actPaMatrix3 =
                RankEstimation.toPairwiseCompareMatrix(pd_expert3, PD_OBJ_COUNT, PD_LOGIC_VALUES);
        PairwiseCompareMatrix actPaMatrix4 =
                RankEstimation.toPairwiseCompareMatrix(pd_expert4, PD_OBJ_COUNT, PD_LOGIC_VALUES);

        out.println("pairwise matrix from expert1:");
        TestUtils.printPairwiseMatrix(actPaMatrix1, out);
        out.println("pairwise matrix from expert2:");
        TestUtils.printPairwiseMatrix(actPaMatrix2, out);
        out.println("pairwise matrix from expert3:");
        TestUtils.printPairwiseMatrix(actPaMatrix3, out);
        out.println("pairwise matrix from expert4:");
        TestUtils.printPairwiseMatrix(actPaMatrix4, out);


        TestUtils.assert2DArrayEquals(
                pd_pw_expected1,
                Array2DUtils.toPrimitive(actPaMatrix1.getMatrix()), 0d);
        TestUtils.assert2DArrayEquals(
                pd_pw_expected2,
                Array2DUtils.toPrimitive(actPaMatrix2.getMatrix()), 0d);
        TestUtils.assert2DArrayEquals(
                pd_pw_expected3,
                Array2DUtils.toPrimitive(actPaMatrix3.getMatrix()), 0d);
        TestUtils.assert2DArrayEquals(
                pd_pw_expected4,
                Array2DUtils.toPrimitive(actPaMatrix4.getMatrix()), 0d);

        EstimationMatrix em = new EstimationMatrix(PD_OBJ_COUNT, PD_LOGIC_VALUES);

        em.addPairwiseCompare(actPaMatrix1);
        em.addPairwiseCompare(actPaMatrix2);
        em.addPairwiseCompare(actPaMatrix3);
        em.addPairwiseCompare(actPaMatrix4);
        em.calculate();
        // TestUtils.printMatrix(em.getResultMatrix(), out);
        TestUtils.assert2DArrayEquals(
                pd_estimationmatrix_expected,
                em.getResultMatrix(), 0d);
    }
}