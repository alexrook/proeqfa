package flow;

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
        int actual = EventsByPeriod.calcPeriodCountBySturgesRule(16); //doc data
        assertEquals(expected, actual);
        // out.println(Math.round(-3.54));
        // out.println((int)(-3.54-0.5));

        actual = EventsByPeriod.calcPeriodCountBySturgesRule(0); //doc data
        out.println(actual);
    }

}