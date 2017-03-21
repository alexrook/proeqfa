package proeqfa.math.commons;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;

/**
 * @author moroz
 */
public class MUtils {

    public static double determinant(RealMatrix matrix) {
        LUDecomposition lu = new LUDecomposition(matrix);
        return lu.getDeterminant();
    }

    public static double[] getEarray(int objCount) {
        double[] ret = new double[objCount];
        Arrays.fill(ret, 1);
        return ret;
    }
}
