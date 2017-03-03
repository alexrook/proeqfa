package proeqfa.math.estimation;

import java.util.List;

/**
 * @author moroz
 */
public interface IEstimationTestData {

    String getDesc();
    
    int getObjCount()
            ;
    ThreeLogicValues getLogicValues();

    List<Double[][]> getPairwiseCompareHighEchelonArrays();

    double[][] getExpectedEstimationMatrix();

}

