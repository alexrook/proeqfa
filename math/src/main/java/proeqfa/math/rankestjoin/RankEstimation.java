package proeqfa.math.rankestjoin;

import proeqfa.math.estimation.EstimationMatrix;
import proeqfa.math.estimation.PairwiseCompareMatrix;
import proeqfa.math.estimation.ThreeLogicValues;
import proeqfa.math.rank.RankChain;

import static java.lang.System.out;

/**
 * Created by moroz on 17.03.17.
 */
public class RankEstimation {

    private int rankedObjectsCount;
    private ThreeLogicValues logicValues;
    EstimationMatrix estimationMatrix;


    public RankEstimation(int rankedObjectsCount, ThreeLogicValues logicValues) {
        this.rankedObjectsCount = rankedObjectsCount;
        estimationMatrix =
                new EstimationMatrix(rankedObjectsCount, logicValues);
    }

    public void add(RankChain rankChain) {
        if (rankChain.size() != rankedObjectsCount) {
            throw new IllegalArgumentException("ranked chain has different object size");
        }

        estimationMatrix.addPairwiseCompare(toPairwiseCompareMatrix(rankChain));

    }

    /*TODO: protected only for tests*/
    protected PairwiseCompareMatrix toPairwiseCompareMatrix(RankChain rankChain) {
        PairwiseCompareMatrix ret = new PairwiseCompareMatrix(
                estimationMatrix.getObjectsCount(),
                estimationMatrix.getLogicValues());
        int i, j;

        for (RankChain.RankedObject roi : rankChain) {

            i = roi.getId() - 1;

            boolean alreadyMore = false;

            for (RankChain.RankedObject roj : rankChain) {
                j = roj.getId() - 1;
                if (i != j) {
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
                        default:
                            ret.setSame(i, j);
                    }
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
