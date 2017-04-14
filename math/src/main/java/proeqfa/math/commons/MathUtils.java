package proeqfa.math.commons;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by moroz on 03.04.17.
 */
public class MathUtils {


    /**
     * approximates double precision specified
     *
     * @param value
     * @param digitsAfterDecimalPoint
     * @param roundHalf               - BigDecimal.ROUND_HALF_?
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

    /**
     * @param digitsAfterDecimalPoint
     * @return min value for given scale i.e digitsAfterDecimalPoint=2 =>0.01
     */
    public static double getMinValueForScale(int digitsAfterDecimalPoint) {
        return new BigDecimal(BigInteger.valueOf(1), digitsAfterDecimalPoint).doubleValue();
    }

    public static double round(double value, int digitsAfterDecimalPoint) {
        return MathUtils.round(value, digitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @param number
     * @return The minimum number for the number of digits of a given number, i.e 93 => 10, 0.2 => 0.1 ...
     */
    public static double getMinHighBitOfNumber(double number) {
        BigDecimal bd = BigDecimal.valueOf(number);
        return Math.pow(10, (bd.precision() - bd.scale() - 1));
    }

    /**
     * @param value
     * @return rounds value to high bits, 12 => 10, 123=>100, 0.0023 =>0.002
     */
    public static double roundToHighBit(double value) {
        BigDecimal d = BigDecimal.valueOf(getMinHighBitOfNumber(value));
        long k = (long) (value / d.doubleValue());
        BigDecimal ret = d.multiply(BigDecimal.valueOf(k));
        ret.setScale(d.scale(), BigDecimal.ROUND_HALF_UP);
        return ret.doubleValue();

    }

}
