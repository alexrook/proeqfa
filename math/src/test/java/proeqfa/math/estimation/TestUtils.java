package proeqfa.math.estimation;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Locale;
import static org.junit.Assert.*;
import proeqfa.math.commons.Array2DUtils;

/**
 * @author moroz
 */
public class TestUtils {

    private static final NumberFormat N_FORMATER = NumberFormat.getInstance(Locale.US);

    static {
        N_FORMATER.setMaximumFractionDigits(3);
        N_FORMATER.setMinimumFractionDigits(3);
    }

    public static <T extends Number> void assert2DArrayEquals(T[][] expecteds, T[][] actuals, double delta) {

        assertEquals(expecteds.length, actuals.length);
        double[][] expectedsPrimitive = Array2DUtils.toPrimitive(expecteds);
        double[][] actualsPrimitive = Array2DUtils.toPrimitive(actuals);

        for (int i = 0; i < expectedsPrimitive.length; i++) {
            assertArrayEquals(expectedsPrimitive[i], actualsPrimitive[i], delta);
        }

    }

    public static <T extends Number> void printMatrix(T[][] array, PrintStream out) {

        for (T[] row : array) {
            for (T val : row) {
                String v = val == null ? "n" : N_FORMATER.format(val);
                out.append(" ")
                        .append(v)
                        .append("  ")
                        .append("|");
            }
            out.append("\n")
                    //     .append("-")
                    .append("\n");
        }
    }

    public static void printMatrix(int[][] array, PrintStream out) {

        for (int[] row : array) {
            for (int val : row) {
                String v = N_FORMATER.format(val);
                out.append(" ")
                        .append(v)
                        .append("  ")
                        .append("|");
            }
            out.append("\n")
                    //     .append("-")
                    .append("\n");
        }
    }

    public static void printPairwiseMatrix(PairwiseCompareMatrix matrix, PrintStream out) {
        Double[][] array = new Double[matrix.getObjCount()][matrix.getObjCount()];
        for (int i = 0; i < matrix.getObjCount(); i++) {
            for (int j = 0; j < matrix.getObjCount(); j++) {
                array[i][j] = matrix.getPairwiseCompare(i, j);
            }
        }
        printMatrix(array, out);
    }

    public static PairwiseCompareMatrix createFromHighEchelonArray(Double[][] array, ThreeLogicValues logicValues) {

        PairwiseCompareMatrix ret = new PairwiseCompareMatrix(array.length+1, logicValues);
        for (int i = 0; i < array.length; i++) {
            for (int j =0; j < array[i].length; j++) {
                Double val = array[i][j];
                if (val.equals(logicValues.getLess())) {
                    ret.setLess(i, j+i+1);
                } else if (val.equals(logicValues.getMore())) {
                    ret.setMore(i, j+i+1);
                } else if (val.equals(logicValues.getSame())) {
                    ret.setSame(i, j+i+1);
                } else {
                    throw new IllegalArgumentException("unsupported logic value in given array");
                }
            }

        }

        return ret;

    }
}
