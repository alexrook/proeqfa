package proeqfa.math.rank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moroz on 16.03.17.
 * see appendix A.1.2
 */
public class RankMatrix {

    private List<RankChain> expertChains = new ArrayList<>(5);

    private int rankedObjectsCount;

    public RankMatrix(int rankedObjectsCount) {
        this.rankedObjectsCount = rankedObjectsCount;
    }

    /**
     * @param rankChain
     */
    public void addRankChain(RankChain rankChain) {
        if (rankChain.size() == this.rankedObjectsCount) {
            expertChains.add(rankChain);
        } else {
            throw new IllegalArgumentException("The rank chain has a different number of objects");
        }
    }

    /**
     * @return
     */
    public double[] getRankedObjectsSums() {
        double[] ret = new double[rankedObjectsCount];
        for (RankChain rc : expertChains) {
            double[] vec = rc.toRankedObjectVector();
            assert vec.length == ret.length;
            //objects in vector sorted by their id
            for (int i = 0; i < vec.length; i++) {
                ret[i] += vec[i] * rc.getExpertCompetenceFactor();
            }

        }
        return ret;
    }

    /**
     * @return
     */
    public RankChain.RankedObject getMaxRankObject() {
        double[] sums = getRankedObjectsSums();
        int id = -1;
        double rank = Double.MIN_VALUE;
        for (int i = 0; i < sums.length; i++) {
            if (rank < sums[i]) {
                rank = sums[i];
                id = i + 1;
            }
        }
        RankChain.RankedObject ret = new RankChain.RankedObject(id);
        ret.setRank(rank);
        return ret;
    }

    /**
     * @return
     */
    public RankChain.RankedObject getMinRankObject() {
        double[] sums = getRankedObjectsSums();
        int id = -1;
        double rank = Double.MAX_VALUE;
        for (int i = 0; i < sums.length; i++) {
            if (rank > sums[i]) {
                rank = sums[i];
                id = i + 1;
            }
        }
        RankChain.RankedObject ret = new RankChain.RankedObject(id);
        ret.setRank(rank);
        return ret;
    }

    public int getRankedObjectsCount() {
        return rankedObjectsCount;
    }
}
