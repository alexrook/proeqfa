package proeqfa.math.estimation;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author moroz
 */
public class ThreeLogicValuesTest {

    /*warn! order is important*/
    double[] VIEW1 = {2d, 1d, 0d},
            VIEW2 = {1d, 0d, -1d},
            VIEW3 = {1d, 0.5d, 0d};

    public ThreeLogicValuesTest() {
    }

    /**
     * Test of getView1 method, of class ThreeLogicValues.
     */
    @Test
    public void testGetView1() {
        System.out.println("getView1");
        ThreeLogicValues result = ThreeLogicValues.getView1();
        assertNotNull(result);
        assertArrayEquals(new double[]{result.getMore(), result.getSame(), result.getLess()},
                VIEW1, 0d);
        assertEquals(result.getMore(), VIEW1[0], 0);
        assertEquals(result.getLess(), VIEW1[2], 0);
        assertEquals(result.getSame(), VIEW1[1], 0);

    }

    /**
     * Test of getView2 method, of class ThreeLogicValues.
     */
    @Test
    public void testGetView2() {
        System.out.println("getView2");
        ThreeLogicValues result = ThreeLogicValues.getView2();
        assertNotNull(result);
        assertArrayEquals(new double[]{result.getMore(), result.getSame(), result.getLess()},
                VIEW2, 0);
        assertEquals(result.getMore(), VIEW2[0], 0);
        assertEquals(result.getLess(), VIEW2[2], 0);
        assertEquals(result.getSame(), VIEW2[1], 0);
    }

    /**
     * Test of getView3 method, of class ThreeLogicValues.
     */
    @Test
    public void testGetView3() {
        System.out.println("getView3");
        ThreeLogicValues result = ThreeLogicValues.getView3();
        assertNotNull(result);
        double[] resultValues = {result.getMore(), result.getSame(), result.getLess()};
        assertEquals(result.getMore(), VIEW3[0], 0);
        assertEquals(result.getLess(), VIEW3[2], 0);
        assertEquals(result.getSame(), VIEW3[1], 0);
        assertArrayEquals(resultValues, VIEW3, 0);
    }

    /**
     * Test of getMore method, of class ThreeLogicValues.
     */
    @Test
    public void testGetMore() {
        System.out.println("getMore");
        double expResult = 5.0d;
        ThreeLogicValues instance = new ThreeLogicValues(expResult, 2, 1);
        double result = instance.getMore();
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of getLess method, of class ThreeLogicValues.
     */
    @Test
    public void testGetLess() {
        System.out.println("getLess");
        double expResult = 1.0d;
        ThreeLogicValues instance = new ThreeLogicValues(3, 2, expResult);
        double result = instance.getLess();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getSame method, of class ThreeLogicValues.
     */
    @Test
    public void testGetSame() {
        System.out.println("getSame");
        double expResult = 2.0d;
        ThreeLogicValues instance = new ThreeLogicValues(3, expResult, 1);
        double result = instance.getSame();
        assertEquals(expResult, result, 0.0);
    }

}
