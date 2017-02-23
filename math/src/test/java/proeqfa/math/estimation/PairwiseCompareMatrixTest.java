package proeqfa.math.estimation;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author moroz
 */
public class PairwiseCompareMatrixTest {

    public PairwiseCompareMatrixTest() {
    }

    @Test
    public void dummy() {

        float[] a1 = new float[3];
        assertNotNull(a1[2]);
        assertEquals(a1[0], 0, 0);

        Float[] a2 = new Float[5];
        assertNull(a2[1]); //so we need Float[] as data holder for PairwiseCompareMatrix
        //with null as marker
    }

    @Test
    public void testGetEntry() {
        ThreeLogicValues threeLogic = ThreeLogicValues.getView1();
        PairwiseCompareMatrix matrix = new PairwiseCompareMatrix(3, ThreeLogicValues.getView1());

        Float ret1 = matrix.getPairwiseCompare(0, 0); //first on diagonal, must be 'same'
        assertEquals(threeLogic.getSame(), ret1, 0);

        Float ret2 = matrix.getPairwiseCompare(2, 2); //last on diagonal, must be 'same'
        assertEquals(threeLogic.getSame(), ret2, 0);

        Float ret3 = matrix.getPairwiseCompare(0, 1); //last on diagonal, must be 'same'
        assertNull(ret3);

    }

    /**
     * Test of setOMore method, of class PairwiseCompareMatrix.
     */
    @Test
    public void testSetMore() {
        System.out.println("setMore");
        ThreeLogicValues threeLogic = ThreeLogicValues.getView1();
        int i = 1, j = 0;

        PairwiseCompareMatrix instance = new PairwiseCompareMatrix(3, threeLogic);
        instance.setMore(i, j);
        /* for view1 expexted result
        ╔═══╦═══╦═══╗
        ║ 1 ║ 0 ║ n ║
        ║ 2 ║ 1 ║ n ║
        ║ n ║ n ║ 1 ║
        ╚═══╩═══╩═══╝
         */
        assertEquals(threeLogic.getMore(), instance.getPairwiseCompare(i, j), 0);
        assertEquals(threeLogic.getLess(), instance.getPairwiseCompare(j, i), 0);
        printMatrix(instance);
    }

    /**
     * Test of setLess method, of class PairwiseCompareMatrix.
     */
    @Test
    public void testSetLess() {
        System.out.println("setLess");
        ThreeLogicValues threeLogic = ThreeLogicValues.getView2();
        int i = 2, j = 1;

        PairwiseCompareMatrix instance = new PairwiseCompareMatrix(3, threeLogic);
        instance.setLess(i, j);
        /* for view2 expexted result
        ╔═══╦═══╦═══╗
        ║ 0 ║ n ║ n ║
        ║ n ║ 0 ║ 1 ║
        ║ n ║ -1║ 0 ║
        ╚═══╩═══╩═══╝
         */
        assertEquals(threeLogic.getLess(), instance.getPairwiseCompare(i, j), 0);
        assertEquals(threeLogic.getMore(), instance.getPairwiseCompare(j, i), 0);
        printMatrix(instance);

    }

    @Test
    public void testGetRevertProgressionSum() {
        System.out.println("getRevertProgressionSum");

        int exp_s1 = 9; //4+3+2 (step 1)
        int s1 = PairwiseCompareMatrix.getRevertProgressionSum(4, 2, 1);
        assertEquals(exp_s1, s1);

        int exp_s2 = 25; //9+7+5+3+1 (step 2)
        int s2 = PairwiseCompareMatrix.getRevertProgressionSum(9, 1, 2);
        assertEquals(exp_s2, s2);

        int exp_s3 = 6; //3+2+1 (step 1)
        int s3 = PairwiseCompareMatrix.getRevertProgressionSum(3, 1, 1);
        assertEquals(exp_s3, s3);
    }

    @Test
    public void testGetHighEchelonSquareMatrixEntryCount() {
        System.out.println("getHighEchelonSquareMatrixEntryCount");

        int exp_c1 = 6;
        /*
        ╔═══╦═══╦═══╦═══╗
        ║ x ║ n ║ n ║ n ║
        ║ x ║ x ║ n ║ n ║
        ║ x ║ x ║ x ║ n ║
        ║ x ║ x ║ x ║ x ║
        ╚═══╩═══╩═══╩═══╝
         */
        int c1 = PairwiseCompareMatrix.getHighEchelonSquareMatrixEntryCount(4);
        assertEquals(exp_c1, c1);

        int exp_c2 = 3;
        /*
        ╔═══╦═══╦═══╗
        ║ x ║ n ║ n ║
        ║ x ║ x ║ n ║
        ║ x ║ x ║ x ║
        ╚═══╩═══╩═══╝
         */
        int c2 = PairwiseCompareMatrix.getHighEchelonSquareMatrixEntryCount(3);
        assertEquals(exp_c2, c2);

        int exp_c3 = 10;
        /*
        ╔═══╦═══╦═══╦═══╦═══╗
        ║ x ║ n ║ n ║ n ║ n ║
        ║ x ║ x ║ n ║ n ║ n ║
        ║ x ║ x ║ x ║ n ║ n ║
        ║ x ║ x ║ x ║ x ║ n ║
        ║ x ║ x ║ x ║ x ║ x ║
        ╚═══╩═══╩═══╩═══╩═══╝
         */
        int c3 = PairwiseCompareMatrix.getHighEchelonSquareMatrixEntryCount(5);
        assertEquals(exp_c3, c3);

    }

    private void printMatrix(PairwiseCompareMatrix matrix) {
        Float[][] array = new Float[matrix.getObjCount()][matrix.getObjCount()];
        for (int i = 0; i < matrix.getObjCount(); i++) {
            for (int j = 0; j < matrix.getObjCount(); j++) {
                array[i][j] = matrix.getPairwiseCompare(i, j);
            }
        }
        TestUtils.printMatrix((Float[][]) array, matrix.getObjCount(), matrix.getObjCount(), System.out);
    }
}
