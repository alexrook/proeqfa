package proeqfa.math.commons;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;

/**
 * @author moroz
 */
public class Array2DUtils {

    public static double[][] EMPTY_DOUBLE_2D_ARRAY = new double[0][0];
    public static Double[][] EMPTY_DOUBLE_OBJ_2D_ARRAY = new Double[0][0];

    public static <T extends Number> double[][] toPrimitive(T[][] array) {

        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_2D_ARRAY;
        }
        //http://stackoverflow.com/questions/5958186/multidimensional-arrays-lengths-in-java
        int rows = array.length;
        final double[][] result = new double[rows][];
        for (int i = 0; i < rows; i++) {

            int columns = array[i].length;
            result[i] = new double[columns];

            for (int j = 0; j < columns; j++) {
                result[i][j] = array[i][j].doubleValue();
            }
        }
        return result;

    }

    public static Double[][] toObject(final double[][] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJ_2D_ARRAY;
        }
        int rows = array.length;
        final Double[][] result = new Double[rows][];
        for (int i = 0; i < rows; i++) {
            int columns = array[i].length;
            result[i] = new Double[columns];
            for (int j = 0; j < columns; j++) {
                result[i][j] = array[i][j];
            }
        }
        return result;
    }

    public static double determinant(RealMatrix matrix) {
        LUDecomposition lu = new LUDecomposition(matrix);
        return lu.getDeterminant();
    }

    public static double[] getEarray(int objCount) {
        double[] ret = new double[objCount];
        Arrays.fill(ret, 1);
        return ret;
    }

    public static double getAbsMax(RealMatrix M) {

        double ret = -Double.MAX_VALUE;

        for (int i = 0; i < M.getRowDimension(); i++) {
            for (int j = 0; j < M.getColumnDimension(); j++) {
                double val = Math.abs(M.getEntry(i, j));
                if (val > ret) {
                    ret = val;
                }
            }
        }

        return ret;

    }
}
