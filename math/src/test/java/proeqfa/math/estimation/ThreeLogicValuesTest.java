package proeqfa.math.estimation;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author moroz
 */
public class ThreeLogicValuesTest {

    /*warn! order is important*/
    float[] VIEW1 = {2, 1, 0},
            VIEW2 = {1, 0, -1},
            VIEW3 = {1, 0.5f, 0};

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
        assertArrayEquals(new float[]{result.getMore(), result.getSame(), result.getLess()},
                VIEW1, 0);
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
        assertArrayEquals(new float[]{result.getMore(), result.getSame(), result.getLess()},
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
        float[] resultValues = {result.getMore(), result.getSame(), result.getLess()};
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
        float expResult = 5.0F;
        ThreeLogicValues instance = new ThreeLogicValues(expResult, 2, 1);
        float result = instance.getMore();
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of getLess method, of class ThreeLogicValues.
     */
    @Test
    public void testGetLess() {
        System.out.println("getLess");
        float expResult = 1.0F;
        ThreeLogicValues instance = new ThreeLogicValues(3, 2, expResult);
        float result = instance.getLess();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getSame method, of class ThreeLogicValues.
     */
    @Test
    public void testGetSame() {
        System.out.println("getSame");
        float expResult = 2.0F;
        ThreeLogicValues instance = new ThreeLogicValues(3, expResult, 1);
        float result = instance.getSame();
        assertEquals(expResult, result, 0.0);
    }

}
