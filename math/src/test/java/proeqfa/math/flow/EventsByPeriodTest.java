package proeqfa.math.flow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;
import proeqfa.math.commons.MathUtils;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by moroz on 29.03.17.
 */
public class EventsByPeriodTest extends TestBase {


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
        assertEquals(3.5, EventsByPeriod.round(3.5, 2),0d);
        assertEquals(0.001,MathUtils.getMinValueForScale(3),0d);
    }

    @Test
    public void calcPeriodCountBySturgesRule01() throws Exception {
        int expected = 5;//doc data
        int actual = EventsByPeriod.getPeriodCountBySturgesRule(16); //doc data
        assertEquals(expected, actual);
        // out.println(Math.round(-3.54));
        // out.println((int)(-3.54-0.5));

        actual = EventsByPeriod.getPeriodCountBySturgesRule(0); //doc data
        assertEquals(1, actual);
    }

    @Test
    public void test_all_doc_data() {
        EventsByPeriod instance = new EventsByPeriod(
                20, 2);
        all_doc_data_tester(instance);
    }

    @Test
    public void test_all_doc_data_autoPrecision() {
        EventsByPeriod instance = new EventsByPeriod(
                20);
        all_doc_data_tester(instance);
    }

    private void all_doc_data_tester(EventsByPeriod instance) {
        instance.addEvent(1);
        instance.addEvent(1);
        instance.addEvent(3.5);

        instance.addEvent(4.5);
        instance.addEvent(7.3);

        instance.addEvent(8.1);
        instance.addEvent(8.31);
        instance.addEvent(9.5);
        instance.addEvent(11.9);

        instance.addEvent(12.2);
        instance.addEvent(13.91);
        instance.addEvent(14.1);


        instance.addEvent(17.9);
        instance.addEvent(18.3);
        instance.addEvent(19.5);
        instance.addEvent(19.9);

        assertEquals(16, instance.getEventsCount());
        assertEquals(5, instance.getPeriodsCount());
        assertEquals(4d, instance.getPeriodSize(), 0d);

        //see 10.1 doc diagram
        assertEquals(3, instance.getEventsCountByPeriod(1));
        assertEquals(2, instance.getEventsCountByPeriod(2));
        assertEquals(4, instance.getEventsCountByPeriod(3));
        assertEquals(3, instance.getEventsCountByPeriod(4));
        assertEquals(4, instance.getEventsCountByPeriod(5));
    }

    @Test
    public void test_all_user_data01() {
        EventsByPeriod instance = new EventsByPeriod(
                17, 2);
        all_user_data01_tester(instance);
    }

    @Test
    public void test_all_user_data_01_autoPrecision() {
        EventsByPeriod instance = new EventsByPeriod(
                17);
        all_user_data01_tester(instance);
    }

    private void all_user_data01_tester(EventsByPeriod instance) {
        //period 1
        instance.addEvent(0.1);
        instance.addEvent(1);
        //period 2
        instance.addEvent(3.5);
        instance.addEvent(4.5);
        //period 3
        instance.addEvent(7.3);
        instance.addEvent(8.1);
        instance.addEvent(8.31);
        instance.addEvent(9.5);
        //period 4
        instance.addEvent(11.9);
        instance.addEvent(12.2);
        //period 5
        instance.addEvent(13.91);
        instance.addEvent(14.1);


        assertEquals(12, instance.getEventsCount());
        assertEquals(5, instance.getPeriodsCount());
        assertEquals(3.4, instance.getPeriodSize(), 0d);


        assertEquals(2, instance.getEventsCountByPeriod(1));
        assertEquals(2, instance.getEventsCountByPeriod(2));
        assertEquals(4, instance.getEventsCountByPeriod(3));
        assertEquals(2, instance.getEventsCountByPeriod(4));
        assertEquals(2, instance.getEventsCountByPeriod(5));
    }
}