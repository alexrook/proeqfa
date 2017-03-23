package proeqfa.math.rank;

/**
 * @author moroz
 *         see appendix A.1.2
 */
public class NaturalOrderPosition2Rank implements IPosition2Rank {

    @Override
    public double getRankForPosition(int position) {
        if (position > 0) {
            return position;
        } else {
            throw new IllegalArgumentException("position must be > 0");
        }
    }

}
