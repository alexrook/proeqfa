package proeqfa.math.estimation;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import proeqfa.math.commons.Array2DUtils;


/**
 * @author moroz
 */
public class RelativeImportanceVector {

    public interface ICalcListener { //calculate events listener, mainly for testing purposes
        void onCalcStep(int step, RealMatrix stepResult);
    }


    public final RealMatrix zero_K;
    private final double evaluationRate;
    private final int objCount;

    private RealMatrix K;

    private ICalcListener calcListener;

    public RelativeImportanceVector(int objCount, double evaluationRate) {
        zero_K = MatrixUtils.createColumnRealMatrix(Array2DUtils.getEarray(objCount));
        this.evaluationRate = evaluationRate;
        this.objCount = objCount;
        K = zero_K;
    }


    public void calculate(double[][] estimationMatrix) {

        final RealMatrix X = MatrixUtils.createRealMatrix(estimationMatrix);
        int step = 0;

        checkMatrix(X);

        K = zero_K;

        final RealMatrix Earray = MatrixUtils.createRowRealMatrix(Array2DUtils.getEarray(objCount));

        RealMatrix Kold;

        double absMMax;
        do {
            Kold = K;

            RealMatrix Y = X.multiply(Kold);

            RealMatrix lambdaM = Earray.multiply(Y);
            assert lambdaM.getRowDimension() == 1;
            assert lambdaM.getColumnDimension() == 1;
            double lambda = lambdaM.getEntry(0, 0);
            if (lambda == 0) {
                throw new IllegalArgumentException("the algorithm does not converge,"
                        + "check that the matrix is irreducible");
            }
            K = Y.scalarMultiply(1 / lambda);

            RealMatrix Minus = K.subtract(Kold);

            absMMax = getAbsMax(Minus);

            step++;
            if (calcListener != null) {
                calcListener.onCalcStep(step, K);
            }

        } while (absMMax >= evaluationRate);

    }

    public void checkMatrix(RealMatrix X) {

        if ((X.getColumnDimension() != objCount) || (X.getRowDimension() != objCount)) {
            throw new IllegalArgumentException("estimation matrix has inappropriate size");
        }

        for (int i = 0; i < X.getRowDimension(); i++) {
            for (int j = 0; j < X.getColumnDimension(); j++) {
                double e = X.getEntry(i, j);
                if (e < 0) {
                    throw new IllegalArgumentException("estimation matrix must be non-negative");
                }
            }
        }

//        double det = Array2DUtils.determinant(X);
//
//        if (det == 0d) {//Если две (или несколько) строки (столбца) 
//            //матрицы линейно зависимы, то её определитель равен нулю
//            throw new IllegalArgumentException("estimation matrix must be irreducible");
//        }
    }

    private double getAbsMax(RealMatrix M) {

        double ret = Double.MIN_VALUE;

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

    public RealMatrix getRelativeImportanceVector() {
        return K;
    }

    public void setCalcListener(ICalcListener calcListener) {
        this.calcListener = calcListener;
    }

}
