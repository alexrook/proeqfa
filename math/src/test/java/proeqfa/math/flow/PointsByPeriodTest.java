package proeqfa.math.flow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;
import proeqfa.math.commons.MathUtils;

import static org.junit.Assert.*;

/**
 * Created by moroz on 29.03.17.
 */
public class PointsByPeriodTest extends TestBase {


    @Before
    @Override
    public void setUp() {
        super.setUp();
    }


    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void test_minValue() {
//        BigDecimal a = BigDecimal.valueOf(3.171);
//        out.println("precision=" + a.precision());
//        out.println("scale=" + a.scale());
//        out.println("unscaled= " + a.unscaledValue());
        assertEquals(3.5, PointsByPeriod.round(3.5, 2), 0d);
        assertEquals(0.001, MathUtils.getMinValueForScale(3), 0d);
    }

    @Test
    public void calcPeriodCountBySturgesRule01() throws Exception {
        int expected = 5;//doc data
        int actual = PointsByPeriod.getPeriodCountBySturgesRule(16); //doc data
        assertEquals(expected, actual);
        // out.println(Math.round(-3.54));
        // out.println((int)(-3.54-0.5));

        actual = PointsByPeriod.getPeriodCountBySturgesRule(0); //doc data
        assertEquals(1, actual);
    }

    @Test
    public void test_all_doc_data() {
        PointsByPeriod instance = new PointsByPeriod(
                20, 2);
        all_doc_data_tester(instance);
    }

    @Test
    public void test_all_doc_data_autoPrecision() {
        PointsByPeriod instance = new PointsByPeriod(
                20);
        all_doc_data_tester(instance);
    }

    private void all_doc_data_tester(PointsByPeriod instance) {
        instance.addPoint(1);
        instance.addPoint(1);
        instance.addPoint(3.5);

        instance.addPoint(4.5);
        instance.addPoint(7.3);

        instance.addPoint(8.1);
        instance.addPoint(8.31);
        instance.addPoint(9.5);
        instance.addPoint(11.9);

        instance.addPoint(12.2);
        instance.addPoint(13.91);
        instance.addPoint(14.1);


        instance.addPoint(17.9);
        instance.addPoint(18.3);
        instance.addPoint(19.5);
        instance.addPoint(19.9);

        assertEquals(16, instance.getPointsCount());
        assertEquals(5, instance.getPeriodsCount());
        assertEquals(4d, instance.getPeriodSize(), 0d);

        //see 10.1 doc diagram
        assertEquals(3, instance.getPointsCountByPeriod(1));
        assertEquals(2, instance.getPointsCountByPeriod(2));
        assertEquals(4, instance.getPointsCountByPeriod(3));
        assertEquals(3, instance.getPointsCountByPeriod(4));
        assertEquals(4, instance.getPointsCountByPeriod(5));
    }

    @Test
    public void test_all_user_data01() {
        PointsByPeriod instance = new PointsByPeriod(
                17, 2);
        all_user_data01_tester(instance);
    }

    @Test
    public void test_all_user_data_01_autoPrecision() {
        PointsByPeriod instance = new PointsByPeriod(
                17);
        all_user_data01_tester(instance);
    }

    private void all_user_data01_tester(PointsByPeriod instance) {
        //period 1
        instance.addPoint(0.1);
        instance.addPoint(1);
        //period 2
        instance.addPoint(3.5);
        instance.addPoint(4.5);
        //period 3
        instance.addPoint(7.3);
        instance.addPoint(8.1);
        instance.addPoint(8.31);
        instance.addPoint(9.5);
        //period 4
        instance.addPoint(11.9);
        instance.addPoint(12.2);
        //period 5
        instance.addPoint(13.91);
        instance.addPoint(14.1);


        assertEquals(12, instance.getPointsCount());
        assertEquals(5, instance.getPeriodsCount());
        assertEquals(3.4, instance.getPeriodSize(), 0d);


        assertEquals(2, instance.getPointsCountByPeriod(1));
        assertEquals(2, instance.getPointsCountByPeriod(2));
        assertEquals(4, instance.getPointsCountByPeriod(3));
        assertEquals(2, instance.getPointsCountByPeriod(4));
        assertEquals(2, instance.getPointsCountByPeriod(5));
    }

    @Test
    public void test_user_data02_diapason() {
        PointsByPeriod instance = new PointsByPeriod(
                7.4, 2);
        all_user_data02_diapason_tester(instance);
    }

    @Test
    public void test_user_data02_diapason_autoPrecision() {
        PointsByPeriod instance = new PointsByPeriod(
                7.4);
        all_user_data02_diapason_tester(instance);
    }


    private void all_user_data02_diapason_tester(PointsByPeriod instance) {

        //period 1
        instance.addPoint(0.25);
        instance.addPoint(0.71);
        instance.addPoint(0.97);
        instance.addPoint(1.5);
        //period 2
        instance.addPoint(2.2);
        instance.addPoint(2.9);
        //period 3
        instance.addPoint(4.1);
        instance.addPoint(4.7);
        //period 4
        instance.addPoint(6.53);

        assertEquals(9, instance.getPointsCount());
        assertEquals(4, instance.getPeriodsCount());
        assertEquals(1.85, instance.getPeriodSize(), 0d);

        assertEquals(4, instance.getPointsCountByPeriod(1));
        assertEquals(2, instance.getPointsCountByPeriod(2));
        assertEquals(2, instance.getPointsCountByPeriod(3));
        assertEquals(1, instance.getPointsCountByPeriod(4));

    }

    @Test
    public void stress_01() {
        PointsByPeriod instance = new PointsByPeriod(
                7.4, -2);
        assertEquals(0, instance.getCalcPrecision());
        assertEquals(7d, instance.getPeriodSize(), 0d); //precision=0, round(7.4) ->7
        assertEquals(1, instance.getPeriodsCount());
    }


    @Test
    public void stress_02() {
        PointsByPeriod instance = new PointsByPeriod(
                -1, -2);
        assertEquals(0d, instance.getPeriodSize(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void stress_03() {
        PointsByPeriod instance = new PointsByPeriod(
                -1, -2);
        instance.addPoint(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void stress_04() {
        PointsByPeriod instance = new PointsByPeriod(
                3, 2);
        instance.addPoint(3.1); //diapason=3, precision=2, high boundary  -> 3.01
    }
}