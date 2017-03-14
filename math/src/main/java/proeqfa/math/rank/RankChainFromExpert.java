package proeqfa.math.rank;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author moroz
 */
public class RankChainFromExpert {

    public static enum RankedObjectsLink {

        MORE(2), SAME(1), NIL(-1);

        private final int id;

        public static RankedObjectsLink valueOf(int id) {

            switch (id) {
                case 1:
                    return SAME;
                case 2:
                    return MORE;
                case -1:
                    return NIL;
                default:
                    throw new IllegalArgumentException("unsupported RankedObjectsLink value");
            }

        }

        public static RankedObjectsLink fromString(String ch) {

            switch (ch) {
                case "~":
                    return SAME;
                case ">":
                    return MORE;
                default:
                    throw new IllegalArgumentException("unsupported RankedObjectsLink value");
            }

        }

        RankedObjectsLink(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            switch (id) {
                case 1:
                    return "~";
                case 2:
                    return ">";
                case -1:
                    return "";
                default:
                    throw new IllegalArgumentException("unsupported RankedObjectsLink value");
            }
        }

    }

    public static void main(String[] args) {

        List<String> aaa = new LinkedList<>();

        aaa.addAll(Arrays.asList(new String[]{"aaa", "bbb", "ccc"}));

        ListIterator<String> li = aaa.listIterator(aaa.size() - 1);
        while (li.hasPrevious()) {
            System.out.println(li.previous());
        }

    }

    public static class RankedObject {

        private final int id;
        private int position;
        private double rank;
        private RankedObjectsLink relToPreviosObject = RankedObjectsLink.NIL;

        public RankedObject(int id) {
            this.id = id;
        }

        public int getPosition() {
            return position;
        }

        public RankedObjectsLink getPreviosObjectRel() {
            return relToPreviosObject;
        }

        public void setPreviosObjectRel(RankedObjectsLink link) {
            this.relToPreviosObject = link;
        }

        public int getRankedObjectId() {
            return this.id;
        }

        public double getRank() {
            return rank;
        }

        public void setRank(double rank) {
            this.rank = rank;
        }

        @Override
        public String toString() {
            return relToPreviosObject + " O" + id + "(" + rank + ")";
        }

    }

    private final List<RankedObject> rankedObjectOrderedChain = new LinkedList();

    private double expertCompetenceFactor = 1;

    private IPosition2Rank position2Rank;

    public static RankChainFromExpert fromArray(RankedObject[] rankedObjectOrderedArray,
                                                IPosition2Rank position2RankImpl) {

        RankChainFromExpert ret = new RankChainFromExpert();
        ret.setPosition2Rank(position2RankImpl);

        for (RankedObject ro : rankedObjectOrderedArray) {
            ret.addHead(ro);
        }

        return ret;

    }

    public double[] toRankedObjectVector() {
        double[] ret = new double[this.rankedObjectOrderedChain.size()];

        for (RankedObject item : this.rankedObjectOrderedChain) {
            ret[item.id - 1] = item.rank;
        }

        return ret;

    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("RankList{ ");
        for (RankedObject o : rankedObjectOrderedChain) {
            buf.append(o);
            buf.append(" ");
        }
        return buf.append("}").toString();
    }

    public double getExpertCompetenceFactor() {
        return expertCompetenceFactor;
    }

    public void setExpertCompetenceFactor(double expertCompetenceFactor) {
        this.expertCompetenceFactor = expertCompetenceFactor;
    }

    public void addHead(RankedObject obj) {

        double rank = position2Rank.getRankForPosition(rankedObjectOrderedChain.size() + 1);

        switch (obj.relToPreviosObject) {
            case MORE: {
                obj.setRank(rank);
                rankedObjectOrderedChain.add(obj);
                break;
            }
            case SAME: {
                obj.setRank(rank);//fake rank for start calcualtion
                rankedObjectOrderedChain.add(obj);
                List<RankedObject> tailList = getTailList(rankedObjectOrderedChain.size());
                if (tailList != null) {
                    ListIterator<RankedObject> tailIterator = tailList.listIterator(tailList.size());
                    setRankRecursive(tailIterator, 0, 0);
                }
                break;
            }
            default: {
                if (rankedObjectOrderedChain.isEmpty()) {
                    obj.setRank(rank);
                    rankedObjectOrderedChain.add(obj);
                } else {
                    throw new IllegalArgumentException("non fisrt object must have a link to previos object");
                }
            }
        }

    }

    private double setRankRecursive(ListIterator<RankedObject> tail, int counter, double rankSum) {
        RankedObject ro = tail.previous();
        if (ro.getPreviosObjectRel() == RankedObjectsLink.SAME) {
            counter++;
            rankSum += ro.getRank();
            ro.setRank(setRankRecursive(tail, counter, rankSum));
            return ro.getRank();
        }
        rankSum += ro.getRank();
        counter++;
        double rank = rankSum / counter;
        ro.setRank(rank);
        return rank;
    }

    private List<RankedObject> getTailList(int headStart) {
        return rankedObjectOrderedChain.subList(0, headStart);
    }

    public int size() {
        return this.rankedObjectOrderedChain.size();
    }

    public IPosition2Rank getPosition2Rank() {
        return position2Rank;
    }

    public void setPosition2Rank(IPosition2Rank position2Rank) {
        this.position2Rank = position2Rank;
    }

}
