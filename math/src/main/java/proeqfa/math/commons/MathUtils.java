package proeqfa.math.commons;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by moroz on 03.04.17.
 */
public class MathUtils {


    /**
     *  approximates double precision specified
     * @param value
     * @param digitsAfterDecimalPoint
     * @param roundHalf - BigDecimal.ROUND_HALF_?
     * @return rounded value
     */
    public static double round(double value,
                               int digitsAfterDecimalPoint,
                               int roundHalf) {

        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(digitsAfterDecimalPoint,
                roundHalf);
        return bigDecimal.doubleValue();
    }

    public static double getMinValueForScale(int digitsAfterDecimalPoint) {
        return new BigDecimal(BigInteger.valueOf(1), digitsAfterDecimalPoint).doubleValue();
    }

    public static double round(double value, int digitsAfterDecimalPoint) {
        return MathUtils.round(value, digitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
    }

}
