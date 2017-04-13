package proeqfa.math.expertcomp;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import proeqfa.math.commons.Array2DUtils;
import proeqfa.math.commons.ICalcListener;

/**
 * Created by moroz on 21.03.17.
 * see appendix A.2.1
 */
public class RelCompetency {

    public enum Relation {

        APPROVE(1), REJECT(0);
        private final int val;

        Relation(int i) {
            val = i;
        }

        public int getVal() {
            return val;
        }

        public static Relation valueOf(int i) {
            switch (i) {
                case 1: {
                    return APPROVE;
                }
                case 0: {
                    return REJECT;
                }
                default: {
                    throw new IllegalArgumentException("unsupported relation value: " + i);
                }

            }
        }

        public static Relation valueOf(double i) {
            int v = (int) Math.round(i);
            return valueOf(v);
        }
    }

    private final Double[][] data;
    private int change;
    private final double evaluationRate;
    private boolean calculated = false;
    private RealMatrix K;
    private final RealMatrix K_step_0;
    private final RealMatrix zero_K;
    private ICalcListener listener;

    public RelCompetency(int expertCount, double evaluationRate) {
        data = new Double[expertCount][expertCount];
        change = expertCount * expertCount;
        this.evaluationRate = evaluationRate;
        K_step_0 = MatrixUtils.createColumnRealMatrix(Array2DUtils.getEarray(expertCount));
        K = K_step_0;
        zero_K=MatrixUtils.createRealMatrix(new double[expertCount][expertCount]);
    }

    public static RelCompetency fromArray(double[][] relArray, double evaluationRate) {
        RelCompetency ret = new RelCompetency(relArray.length, evaluationRate);
        for (int i = 0; i < relArray.length; i++) {
            if (relArray.length != relArray[i].length) {
                throw new IllegalArgumentException("inappropriate array size");
            }
            for (int j = 0; j < relArray[i].length; j++) {
                Relation rel = Relation.valueOf(relArray[i][j]);
                ret.setRelation(i, j, rel);
            }
        }
        return ret;
    }

    public void setRelation(int i, int j, Relation rel) {
        if (data[i][j] == null) {
            change--;
        }
        data[i][j] = Double.valueOf(rel.getVal());
        calculated = false;
    }

    public void approve(int i, int j) {
        setRelation(i, j, Relation.APPROVE);
    }

    public void reject(int i, int j) {
        setRelation(i, j, Relation.REJECT);
    }

    public Double[][] getMatrix() {
        if (change > 0) {
            throw new IllegalStateException("matrix is not filled");
        }
        return data;
    }

    public RealMatrix getReMatrix() {
        double[][] data = Array2DUtils.toPrimitive(getMatrix());
        return MatrixUtils.createRealMatrix(data);
    }


    public void calculate() {
        RealMatrix oldK;
        RealMatrix data = getReMatrix();
        int step = 0;

        double absMMax;

        K = K_step_0;

        do {
            oldK = K;

            RealMatrix D = data.multiply(K);

            RealMatrix Y = K_step_0.transpose().multiply(D);

            assert Y.getColumnDimension() == 1;
            assert Y.getRowDimension() == 1;
            double y = Y.getData()[0][0];
            if (y==0){//no one approve for others or self
                K=zero_K;
                break;
            }
            K = D.scalarMultiply(1 / y);

            RealMatrix Minus = K.subtract(oldK);

            absMMax = Array2DUtils.getAbsMax(Minus);

            if (listener != null) {
                step++;
                if (!listener.onCalcStep(step, K)) {
                    break;
                }
            }


        } while (absMMax >= evaluationRate);


    }


    public RealMatrix getRelCompetencyVector() {
        if (!calculated) {
            calculate();
        }
        return K;
    }

    public void setListener(ICalcListener listener) {
        this.listener = listener;
    }
}
