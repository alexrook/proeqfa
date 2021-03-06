package proeqfa.math.estimation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;

import static org.junit.Assert.*;


/**
 * @author moroz
 */
public class PairwiseCompareMatrixTest extends TestBase {

    public PairwiseCompareMatrixTest() {
    }

    @Before
    @Override
    public void setUp() {
        super.setUp( );
    }

    @After
    @Override
    public void tearDown(){

    }

    @Test
    public void dummy() {

        double[] a1 = new double[3];
        assertNotNull(a1[2]);
        assertEquals(a1[0], 0, 0);

        Double[] a2 = new Double[5];
        assertNull(a2[1]); //so we need Double[] as data holder for PairwiseCompareMatrix
        //with null as marker
    }

    @Test
    public void testGetEntry() {
        ThreeLogicValues threeLogic = ThreeLogicValues.getView1();
        PairwiseCompareMatrix matrix = new PairwiseCompareMatrix(3, ThreeLogicValues.getView1());

        Double ret1 = matrix.getPairwiseCompare(0, 0); //first on diagonal, must be 'same'
        assertEquals(threeLogic.getSame(), ret1, 0);

        Double ret2 = matrix.getPairwiseCompare(2, 2); //last on diagonal, must be 'same'
        assertEquals(threeLogic.getSame(), ret2, 0);

        Double ret3 = matrix.getPairwiseCompare(0, 1); //not on diagonal must be null
        assertNull(ret3);

    }

    /**
     * Test of setMore method, of class PairwiseCompareMatrix.
     */
    @Test
    public void testSetMore() {
        out.println("setMore");
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
        TestUtils.printPairwiseMatrix(instance, out);
    }

    /**
     * Test of setLess method, of class PairwiseCompareMatrix.
     */
    @Test
    public void testSetLess() {
        out.println("setLess");
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
        TestUtils.printPairwiseMatrix(instance, out);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetMoreWithBadVal() {
        out.println("setLess - expected exception");
        ThreeLogicValues threeLogic = ThreeLogicValues.getView3();
        int i = 1, j = 1;
        PairwiseCompareMatrix instance = new PairwiseCompareMatrix(3, threeLogic);
        /* for view3 expected result
        ╔═════╦═════╦═════╗
        ║ 0.5 ║  n  ║  n  ║
        ║  n  ║ 0.5 ║  n  ║
        ║  n  ║  n  ║ 0.5 ║
        ╚═════╩═════╩═════╝
         */
        TestUtils.printPairwiseMatrix(instance, out);
        instance.setLess(i, j);
    }

    @Test
    public void testGetRevertProgressionSum() {
        out.println("getRevertProgressionSum");

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
        out.println("getHighEchelonSquareMatrixEntryCount");

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

}
