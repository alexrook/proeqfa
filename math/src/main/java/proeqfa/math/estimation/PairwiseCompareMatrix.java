package proeqfa.math.estimation;

/**
 * @author moroz
 */
public class PairwiseCompareMatrix {

    private final Float[][] data; //see dummy test
    private final ThreeLogicValues logicValues;
    private int notInitPairCount, objCount;

    /**
     *
     * @param objCount count of object for pairwise compare
     * @param logicValues three logic values
     */
    public PairwiseCompareMatrix(int objCount, ThreeLogicValues logicValues) {
        this.data = new Float[objCount][objCount];
        this.logicValues = logicValues;
        this.objCount = objCount;
        for (int i = 0; i < objCount; i++) {
            data[i][i] = logicValues.getSame();//setup matrix diagonal (always 'same')
        }
        notInitPairCount = getHighEchelonSquareMatrixEntryCount(objCount);
    }

    public Float getPairwiseCompare(int i, int j) {
        return data[i][j]; //don't worry about backdoor access, Float is immutable
    }

    public void setMore(int i, int j) {
        checkDiagonal(i, j);
        decNotInitPairCount(i, j);
        data[i][j] = logicValues.getMore();
        data[j][i] = logicValues.getLess();
    }

    public void setLess(int i, int j) {
        checkDiagonal(i, j);
        decNotInitPairCount(i, j);
        data[i][j] = logicValues.getLess();
        data[j][i] = logicValues.getMore();
    }

    public void setSame(int i, int j) {
        checkDiagonal(i, j);
        decNotInitPairCount(i, j);
        data[i][j] = logicValues.getSame();
        data[j][i] = logicValues.getSame();
    }

    private void decNotInitPairCount(int i, int j) {
        Float oldValue = data[i][j];
        if (oldValue == null) {
            notInitPairCount--;
        }
    }

    private void checkDiagonal(int i, int j) throws IllegalArgumentException {
        if (i == j) {
            throw new IllegalArgumentException("pair matrix diagonal has always 'same' values");
        }
    }

    public Float[][] getMatrix() throws IllegalStateException {
        if (notInitPairCount != 0) {
            throw new IllegalStateException("pair matrix matrix not yet initialized");
        }
        return data;
    }

   
    public int getObjCount() {
        return objCount;
    }

    /**
     *
     * @param start element of progression
     * @param finish element
     * @param step of elements
     * @return sum of elements of revert progression i.e 9, 7, 5, 3, 1 =25
     */
    static int getRevertProgressionSum(int start, int finish, int step) {

        int ret = 0;
        for (int i = start; i > (finish - step); i = i - step) {
            ret = ret + i;
        }
        return ret;
    }

    /**
     * @param matrixSize the size of square matrix
     * @return the 'n' count Echelon in square matrix
     */
    static int getHighEchelonSquareMatrixEntryCount(int matrixSize) {
        //  ╔═══╦═══╦═══╦═══╗ 
        //  ║ x ║ n ║ n ║ n ║ 
        //  ║ x ║ x ║ n ║ n ║ 
        //  ║ x ║ x ║ x ║ n ║ 
        //  ║ x ║ x ║ x ║ x ║
        //  ╚═══╩═══╩═══╩═══╝
        return (matrixSize * matrixSize - matrixSize) / 2;
    }

}
