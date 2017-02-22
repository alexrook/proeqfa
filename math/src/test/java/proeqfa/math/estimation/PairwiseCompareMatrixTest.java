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

    /**
     * Test of setOMore method, of class PairwiseCompareMatrix.
     */
    @Ignore
    @Test
    public void testSetOMore() {
        System.out.println("setOMore");
        int i = 0;
        int j = 0;
        PairwiseCompareMatrix instance = null;
        instance.setMore(i, j);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLess method, of class PairwiseCompareMatrix.
     */
    @Ignore
    @Test
    public void testSetLess() {
        System.out.println("setLess");
        int i = 0;
        int j = 0;
        PairwiseCompareMatrix instance = null;
        instance.setLess(i, j);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

}
