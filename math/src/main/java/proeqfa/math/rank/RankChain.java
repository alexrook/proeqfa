package proeqfa.math.rank;

import java.util.*;

/**
 * @author moroz
 *         see appendix A.1.2
 */
public class RankChain implements Iterable<RankChain.RankedObject> {

    @Override
    public Iterator<RankedObject> iterator() {
        return this.rankedChain.iterator();
    }

    public enum RankedObjectsLink {

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
                case "":
                    return NIL;
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

    public static class RankedObject {

        private final int id;
        private double rank;
        private RankedObjectsLink relToPreviousObject = RankedObjectsLink.NIL;

        public RankedObject(int id) {
            this.id = id;
        }

        public RankedObjectsLink getPreviousObjectRel() {
            return relToPreviousObject;
        }

        public void setPreviousObjectRel(RankedObjectsLink link) {
            this.relToPreviousObject = link;
        }

        public int getId() {
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
            return relToPreviousObject + " O" + id + "(" + rank + ")";
        }

    }

    private final List<RankedObject> rankedChain = new LinkedList();

    private double expertCompetenceFactor = 1;

    private IPosition2Rank position2Rank;

    public static RankChain fromArray(RankedObject[] rankedObjectOrderedArray,
                                      IPosition2Rank position2RankImpl) {

        RankChain ret = new RankChain();
        ret.setPosition2Rank(position2RankImpl);

        for (RankedObject ro : rankedObjectOrderedArray) {
            ret.addHead(ro);
        }

        return ret;

    }

    public double[] toRankedObjectVector() {
        double[] ret = new double[this.rankedChain.size()];

        for (RankedObject item : this.rankedChain) {
            ret[item.id - 1] = item.rank;
        }

        return ret;

    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("RankChain{{ ");
        for (RankedObject o : rankedChain) {
            buf.append(o);
            buf.append(" ");
        }
        return buf.append("}, k=").append(expertCompetenceFactor).append("}").toString();
    }

    public double getExpertCompetenceFactor() {
        return expertCompetenceFactor;
    }

    public void setExpertCompetenceFactor(double expertCompetenceFactor) {
        this.expertCompetenceFactor = expertCompetenceFactor;
    }

    public void addHead(RankedObject obj) {

        double rank = position2Rank.getRankForPosition(rankedChain.size() + 1);

        if (!checkChainAndObj(obj)) {
            throw new IllegalArgumentException("first object should not have a link");
        }

        switch (obj.relToPreviousObject) {
            case MORE: {
                obj.setRank(rank);
                rankedChain.add(obj);
                break;
            }
            case SAME: {
                obj.setRank(rank);//fake rank for start calc
                rankedChain.add(obj);
                List<RankedObject> tail = getTail(rankedChain.size());
                if (tail != null) {
                    ListIterator<RankedObject> tailIterator = tail.listIterator(tail.size());
                    setRankRecursive(tailIterator, 0, 0);
                }
                break;
            }
            default: {
                if (rankedChain.isEmpty()) {
                    obj.setRank(rank);
                    rankedChain.add(obj);
                } else {
                    throw new IllegalArgumentException("non first object must have a link to previous object");
                }
            }
        }

    }

    private boolean checkChainAndObj(RankedObject obj) {
        if (rankedChain.isEmpty()) {
            return obj.getPreviousObjectRel() == RankedObjectsLink.NIL;
        } else {
            return obj.getPreviousObjectRel() != RankedObjectsLink.NIL;
        }
    }

    private double setRankRecursive(ListIterator<RankedObject> tail, int counter, double rankSum) {

        double rank;

        if (tail.hasPrevious()) {
            RankedObject ro = tail.previous();

            rankSum += ro.getRank();
            counter++;

            if (ro.getPreviousObjectRel() == RankedObjectsLink.SAME) {
                ro.setRank(setRankRecursive(tail, counter, rankSum));
                return ro.getRank();
            }

            rank = rankSum / counter;
            ro.setRank(rank);
            return ro.getRank();

        } else {
            rank = counter > 0 ? rankSum / counter : rankSum;
            return rank;
        }
    }

    public List<RankedObject> getTail(int headStart) {
        return rankedChain.subList(0, headStart);
    }

    public List<RankedObject> getHead(int tailEnd) {
        return rankedChain.subList(tailEnd, rankedChain.size());
    }

    public int size() {
        return this.rankedChain.size();
    }

    public IPosition2Rank getPosition2Rank() {
        return position2Rank;
    }

    public void setPosition2Rank(IPosition2Rank position2Rank) {
        this.position2Rank = position2Rank;
    }

}
