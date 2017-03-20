package proeqfa.math.rankestjoin;

import proeqfa.math.estimation.EstimationMatrix;
import proeqfa.math.estimation.PairwiseCompareMatrix;
import proeqfa.math.estimation.ThreeLogicValues;
import proeqfa.math.rank.RankChain;

import java.util.List;



/**
 * Created by moroz on 17.03.17.
 */
public class RankEstimation {

    private EstimationMatrix estimationMatrix;


    public RankEstimation(int rankedObjectsCount, ThreeLogicValues logicValues) {
        estimationMatrix =
                new EstimationMatrix(rankedObjectsCount, logicValues);
    }

    public void add(RankChain rankChain) {
        if (rankChain.size() != estimationMatrix.getObjectsCount()) {
            throw new IllegalArgumentException("ranked chain has different object size");
        }

        estimationMatrix.addPairwiseCompare(
                toPairwiseCompareMatrix(rankChain,
                        estimationMatrix.getObjectsCount(),
                        estimationMatrix.getLogicValues()));

    }


    public static PairwiseCompareMatrix toPairwiseCompareMatrix(RankChain rankChain,
                                                            int objectCount,
                                                            ThreeLogicValues logicValues) {
        PairwiseCompareMatrix ret = new PairwiseCompareMatrix(
                objectCount,
                logicValues);
        int i, j, k = 0;

        for (RankChain.RankedObject roi : rankChain) {

            k++;

            i = roi.getId() - 1;
            boolean alreadyMore = false;

            List<RankChain.RankedObject> head = rankChain.getHead(k);

            for (RankChain.RankedObject roj : head) {
                j = roj.getId() - 1;
                switch (roj.getPreviousObjectRel()) {
                    case MORE: {
                        ret.setMore(i, j);
                        alreadyMore = true;
                        break;
                    }
                    case SAME: {
                        if (alreadyMore) {
                            ret.setMore(i, j);
                        } else {
                            ret.setSame(i, j);
                        }
                    }
//                        default:
//                            ret.setSame(i, j);
                }

            }
        }

        return ret;

    }

    public EstimationMatrix get() {
        estimationMatrix.calculate();
        return estimationMatrix;
    }

}
