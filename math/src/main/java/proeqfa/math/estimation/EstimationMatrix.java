package proeqfa.math.estimation;

/**
 * @author moroz
 */
public class EstimationMatrix {

    private final Double[][] data;
    private final ThreeLogicValues logicValues;
    private final int objCount;
    private float totalExperts; //total number of experts (see calculate for "why is float"
    /*TODO: protected only for tests */
    protected final int[][] moreDecisionMarix; //matrix for 'setMore' decision

    private boolean calculated = false;

    public EstimationMatrix(int objCount, ThreeLogicValues logicValues) {
        this.objCount = objCount;
        this.logicValues = logicValues;
        data = new Double[objCount][objCount];
        moreDecisionMarix = new int[objCount][objCount];
    }

    public void addPairwiseCompare(PairwiseCompareMatrix compareMatrix)
            throws IllegalArgumentException {

        if ((!compareMatrix.getLogicValues().equals(this.logicValues))
                || (compareMatrix.getObjCount() != objCount)) {
            throw new IllegalArgumentException("not supported matrix for this calculation");
        }

        Float[][] matrixData = compareMatrix.getMatrix();

        totalExperts++;

        for (int i = 0; i < objCount; i++) {
            for (int j = 0; j < objCount; j++) {
                Float expertDecision = matrixData[i][j];
                if (expertDecision == logicValues.getMore()) {
                    int old = moreDecisionMarix[i][j];
                    int naw = old + 1;
                    moreDecisionMarix[i][j] = naw;
                }
            }
        }

        calculated = false;
    }

    public void calculate() {
        for (int i = 0; i < objCount; i++) {
            for (int j = 0; j < objCount; j++) {

               // https://mathbits.com/MathBits/Java/DataBasics/Mathoperators.htm
               //Be careful when performing integer division.  
               //When dividing an integer by an integer, 
               //the answer will be an integer (not rounded)
                double moreDecision = moreDecisionMarix[i][j];
                double lessDecision = moreDecisionMarix[j][i];
                double sameDecision = totalExperts - (moreDecision + lessDecision);

                double estimation = logicValues.getMore() * (moreDecision / totalExperts)
                        + logicValues.getLess() * (lessDecision / totalExperts)
                        + logicValues.getSame() * (sameDecision / totalExperts);

                data[i][j] = estimation;

            }
        }
        calculated = true;
    }

    /**
     *
     * @return result matrix
     */
    public Double[][] getResultMatrix() {
        if (calculated) {
            return this.data;
        } else {
            throw new IllegalStateException("matrix is not calculated, use calculate() first");
        }
    }

}
