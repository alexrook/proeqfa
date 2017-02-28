package proeqfa.math.commons;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;

/**
 * @author moroz
 */
public class Array2DUtils {

    public static double[][] EMPTY_DOUBLE_2D_ARRAY = new double[0][0];
    public static Double[][] EMPTY_DOUBLE_OBJ_2D_ARRAY = new Double[0][0];

    public static double[][] toPrimitive(Double[][] array, int rows, int columns) {

        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_2D_ARRAY;
        }
        final double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = array[i][j];
            }
        }
        return result;

    }

    public static Double[][] toObject(final double[][] array, int rows, int columns) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJ_2D_ARRAY;
        }
        final Double[][] result = new Double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = array[i][j];
            }
        }
        return result;
    }
}
