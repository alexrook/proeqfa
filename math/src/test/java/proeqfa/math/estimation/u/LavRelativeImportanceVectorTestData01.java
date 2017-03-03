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
public class LavRelativeImportanceVectorTestData01 implements IRelativeImportanceVectorTestData {

    @Override
    public RealMatrix getExpectedRelativeImportanceVector() {
        return MatrixUtils.createColumnRealMatrix(new double[]{0.404d, 0.565, 0.030});
    }

    @Override
    public String getDesc() {
        return "Relative importance vector test data from user Lav. v01";
    }

    @Override
    public int getObjCount() {
        return 3;
    }

    @Override
    public ThreeLogicValues getLogicValues() {
        return ThreeLogicValues.getView2();
    }

    @Override
    public List<Double[][]> getPairwiseCompareHighEchelonArrays() {
        List<Double[][]> ret = new ArrayList<>();
        ret.add(new Double[][]{
            {0d, 1d},
            {1d}
        });
        ret.add(new Double[][]{
            {0d, 1d},
            {1d}
        });
        ret.add(new Double[][]{
            {-1d, 1d},
            {1d}
        });
        return ret;
    }

    @Override
    public double[][] getExpectedEstimationMatrix() {
        return new double[][]{
            {(3 / 6d), (2 / 6d), (6 / 6d)},
            {(4 / 6d), (3 / 6d), (6 / 6d)},
            {(0d), (0d), (3 / 6d)}
        };

    }

    @Override
    public double getEvaluationRate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
