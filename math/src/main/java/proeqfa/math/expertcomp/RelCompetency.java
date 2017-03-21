package proeqfa.math.expertcomp;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import proeqfa.math.commons.Array2DUtils;

/**
 * Created by moroz on 21.03.17.
 */
public class RelCompetency {

    public enum Relation {

        APPROVE(1), REJECT(0);
        private int val;

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

    private Double[][] data;
    private int change;
    private final double evaluationRate;
    boolean calculated = false;
    RealMatrix K, zeroK;

    public RelCompetency(int expertCount, double evaluationRate) {
        data = new Double[expertCount][expertCount];
        change = expertCount * expertCount;
        this.evaluationRate = evaluationRate;
        zeroK = MatrixUtils.createColumnRealMatrix(Array2DUtils.getEarray(expertCount));
        K = zeroK;
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



    public void calculate() {

    }

    public RealMatrix getRelCompetencyVector() {
        if (!calculated) {
            calculate();
        }
        return K;
    }
}
