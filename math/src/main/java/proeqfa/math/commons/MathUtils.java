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

    public static void main(String[] args){
        double i=.0032;
        BigDecimal d=BigDecimal.valueOf(getMinHighBitOfNumber(i));
        long k=(long) (i/d.doubleValue());
        BigDecimal ret=d.multiply(BigDecimal.valueOf(k));
        ret.setScale(d.scale(),BigDecimal.ROUND_HALF_UP);
        System.out.println("d="+d+", k="+k +",k*d="+(ret.doubleValue()));
    }

}
