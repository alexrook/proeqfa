package proeqfa.math.estimation.u;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import proeqfa.math.estimation.IRelativeImportanceVectorTestData;
import proeqfa.math.estimation.ThreeLogicValues;

/**
 * @author moroz
 */
public class LavRelativeImportanceVectorTestData03 implements IRelativeImportanceVectorTestData {

    @Override
    public double getEvaluationRate() {
        return 0.11;
    }

    @Override
    public RealMatrix getExpectedRelativeImportanceVector() {
        return MatrixUtils.createColumnRealMatrix(new double[]{0.374, 0.442, 0.102, 0.025, 0.057});
    }

    @Override
    public String getDesc() {
        return "Relative importance vector test data from user Lav. v03 ";
    }

    @Override
    public int getObjCount() {
        return 5;
    }

    @Override
    public ThreeLogicValues getLogicValues() {
        return ThreeLogicValues.getView3();
    }

    @Override
    public List<Double[][]> getPairwiseCompareHighEchelonArrays() {
        List<Double[][]> ret = new ArrayList<>();
        ret.add(new Double[][]{
            {0.5, 1d, 1d, 1d},
            {1d, 1d, 1d},
            {0.5, 0.5},
            {0.5},});
        ret.add(new Double[][]{
            {0.5, 1d, 1d, 1d},
            {1d, 1d, 1d},
            {1d, 1d},
            {0d},});
        ret.add(new Double[][]{
            {0d, 1d, 1d, 1d},
            {1d, 1d, 1d},
            {1d, 0.5},
            {0.5},});
        return ret;
    }

    @Override
    public double[][] getExpectedEstimationMatrix() {
        return new double[][]{
            {(3 / 6d), (2 / 6d), (1d), (1d), (1d)},
            {(4 / 6d), (3 / 6d), (1d), (1d), (1d)},
            {(0d), (0d), (3 / 6d), (5 / 6d), (4 / 6d)},
            {(0d), (0d), (1 / 6d), (3 / 6d), (2 / 6d)},
            {(0d), (0d), (2 / 6d), (4 / 6d), (3 / 6d)}
        };

    }

}
