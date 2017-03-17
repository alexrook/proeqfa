package proeqfa.math.rankestjoin;

import org.junit.Test;
import proeqfa.math.commons.Array2DUtils;
import proeqfa.math.estimation.PairwiseCompareMatrix;
import proeqfa.math.estimation.TestUtils;
import proeqfa.math.estimation.ThreeLogicValues;
import proeqfa.math.rank.NaturalOrderPosition2Rank;
import proeqfa.math.rank.RankChain;
import proeqfa.math.rank.RankChainTest;

import static java.lang.System.out;
import static org.junit.Assert.*;

/**
 * Created by moroz on 17.03.17.
 */
public class RankEstimationTest {

    @Test
    public void add() throws Exception {
        //todo
    }

    @Test
    public void toPairwiseCompareMatrix_01() {
        //doc рис. А.6
        RankChain expert1 = RankChain.fromArray(
                RankChainTest.getRankChain("O1(1) >O2(2) >O3(3)")
                , new NaturalOrderPosition2Rank());

        RankChain expert2 = RankChain.fromArray(
                RankChainTest.getRankChain("O1(1) >O3(2) >O2(3)")
                , new NaturalOrderPosition2Rank());

        RankChain expert3 = RankChain.fromArray(
                RankChainTest.getRankChain("O2(1) >O1(2) >O3(3)")
                , new NaturalOrderPosition2Rank());

        RankEstimation instance = new RankEstimation(3,
                ThreeLogicValues.getView3());

        PairwiseCompareMatrix actualMatrix1 = instance.toPairwiseCompareMatrix(expert1);
        TestUtils.printPairwiseMatrix(actualMatrix1, out);
//        Double[][] actual1 = actualMatrix1.getMatrix();
//        double[][] expected1 = {
//                {0.5, 1, 1},
//                {0, 0.5, 1},
//                {0, 0, 0.5}
//        };
//        TestUtils.printMatrix(actual1, out);
//        TestUtils.printMatrix(expected1, out);
//
//        TestUtils.assert2DArrayEquals(expected1, Array2DUtils.toPrimitive(actual1), 0d);

    }

    @Test
    public void get() throws Exception {
        //todo
    }

}