package proeqfa.math.commons;

import java.math.BigDecimal;

/**
 * Created by moroz on 03.04.17.
 */
public class MathUtils {


    /**
     *  approximates double precision specified
     * @param value
     * @param numberOfDigitsAfterDecimalPoint
     * @param roundHalf - BigDecimal.ROUND_HALF_?
     * @return rounded value
     */
    public static double round(double value,
                               int numberOfDigitsAfterDecimalPoint,
                               int roundHalf) {

        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                roundHalf);
        return bigDecimal.doubleValue();
    }
}
