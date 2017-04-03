package proeqfa.math.flow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;

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
    public void calcPeriodCountBySturgesRule01() throws Exception {
        int expected = 5;//doc data
        int actual = EventsByPeriod.getPeriodCountBySturgesRule(16); //doc data
        assertEquals(expected, actual);
        // out.println(Math.round(-3.54));
        // out.println((int)(-3.54-0.5));

        actual = EventsByPeriod.getPeriodCountBySturgesRule(0); //doc data
        out.println(actual);
    }

    @Test
    public void test_all_doc_data() {
        EventsByPeriod instance = new EventsByPeriod(
                12, 20, 0);

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
        assertEquals(4d, instance.getPeriodSize(), 0d);

        //see 10.1 doc diagram
        assertEquals(3, instance.getEventsCountByPeriod(1));
        assertEquals(2, instance.getEventsCountByPeriod(2));
        assertEquals(4, instance.getEventsCountByPeriod(3));
        assertEquals(3, instance.getEventsCountByPeriod(4));
        assertEquals(4, instance.getEventsCountByPeriod(5));

    }

}