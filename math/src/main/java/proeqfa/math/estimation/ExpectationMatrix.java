package proeqfa.math.estimation;

import java.util.ArrayList;

/**
 * @author moroz
 */
public class ExpectationMatrix {

    private final Float[][] data; //see dummy test
    private final ThreeLogicValues logicValues;
    private final int objCount;
    private int m; //total number of experts
    private final int[][] positiveDecisionMarix;

    public ExpectationMatrix(int objCount, ThreeLogicValues logicValues) {
        this.objCount = objCount;
        this.logicValues = logicValues;
        data = new Float[objCount][objCount];
        positiveDecisionMarix=new int[objCount][objCount];
    }

    public void addPairwiseCompare(PairwiseCompareMatrix compareMatrix)
            throws IllegalArgumentException {
      
        if ((!compareMatrix.getLogicValues().equals(this.logicValues))
                || (compareMatrix.getObjCount() != objCount)) {
            throw new IllegalArgumentException("not supported matrix for this setup");
        }

        
    }
}
