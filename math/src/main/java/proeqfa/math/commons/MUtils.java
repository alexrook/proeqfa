package proeqfa.math.commons;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author moroz
 */
public class MUtils {

    public static double determinant(RealMatrix matrix) {
        LUDecomposition lu = new LUDecomposition(matrix);
        return lu.getDeterminant();
    }
}
