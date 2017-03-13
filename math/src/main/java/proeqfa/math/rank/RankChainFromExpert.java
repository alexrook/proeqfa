package proeqfa.math.rank;

import java.util.LinkedList;
import java.util.List;

/**
 * @author moroz
 */
public class RankList {

    public static enum RankedObjectsLink {

        MORE(2), SAME(1);

        private final int id;

        public static RankedObjectsLink valueOf(int id) {

            switch (id) {
                case 1:
                    return SAME;
                case 2:
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
                default:
                    throw new IllegalArgumentException("unsupported RankedObjectsLink value");
            }
        }

    }

    public static class RankedObject {

        private final int id;
        private int position;
        private double rank;
        private RankedObjectsLink link = RankedObjectsLink.MORE;
        private RankedObject nextRankedObject = null;

        public RankedObject(int id) {
            this.id = id;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int pos) {
            this.position = pos;
        }

        public RankedObjectsLink getLink() {
            return link;
        }

        public void setLink(RankedObjectsLink link) {
            this.link = link;
        }

        public RankedObject getNextRankedObject() {
            return nextRankedObject;
        }

        public void setNextRankedObject(RankedObject nextRankedObject) {
            this.nextRankedObject = nextRankedObject;
        }

        public void setNextRankedObject(RankedObject nextRankedObject, RankedObjectsLink link) {
            setNextRankedObject(nextRankedObject);
            setLink(link);
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
            return "O" + id + "(" + rank + ")" + link;
        }

    }

    private final List<RankedObject> rankedObjectOrderedChain = new LinkedList();

    public static RankList fromArray(RankedObject[] rankedObjectOrderedArray) {
        RankList ret = new RankList();

        RankedObject old = null;

        for (RankedObject ro : rankedObjectOrderedArray) {
            if (old == null) {
                old = ro;
            } else {
                old.setNextRankedObject(ro);
                ret.rankedObjectOrderedChain.add(old);
                old = ro;
            }

        }
        ret.rankedObjectOrderedChain.add(old);

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
    
    
    
    public int size(){
        return this.rankedObjectOrderedChain.size();
    }

}
