package proeqfa.math.flow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;

import static org.junit.Assert.*;

/**
 * Created by moroz on 06.04.17.
 */
public class PointsByPeriodExTest extends TestBase {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }

    private void fill_instance_with_doc_points(PointsByPeriod instance) {
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
    }

    @Test
    public void test_all_doc_data() {
        PointsByPeriodEx instance = new PointsByPeriodEx(
                20, 2);
        fill_instance_with_doc_points(instance);
        all_doc_data_tester(instance);
    }

    @Test
    public void test_all_doc_data_autoPrecision() {
        PointsByPeriodEx instance = new PointsByPeriodEx(
                20);
        fill_instance_with_doc_points(instance);
        all_doc_data_tester(instance);
    }

    private void all_doc_data_tester(PointsByPeriod instance) {
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
    public void test_doc_data_custom_periods_count() {
        PointsByPeriodEx instance = new PointsByPeriodEx(
                20);
        fill_instance_with_doc_points(instance);
        assertEquals(16, instance.getPointsCount());
        assertEquals(5, instance.getPeriodsCount()); //by starges rule

        instance.setPeriodsCount(2);
        assertEquals(16, instance.getPointsCount());
        assertEquals(2, instance.getPeriodsCount()); //custom number of periods
        assertEquals(10d, instance.getPeriodSize(), 0d);
        assertEquals(8, instance.getPointsCountByPeriod(1));
        assertEquals(8, instance.getPointsCountByPeriod(2));

        instance.setPeriodsCount(3);
        assertEquals(16, instance.getPointsCount());
        assertEquals(3, instance.getPeriodsCount()); //custom number of periods
        assertEquals(6.67, instance.getPeriodSize(), 0d); //doc data points precision=2, so 20/3≈6.67
        assertEquals(4, instance.getPointsCountByPeriod(1)); // 0-6.67
        assertEquals(6, instance.getPointsCountByPeriod(2)); // 6.67-13.34
        assertEquals(6, instance.getPointsCountByPeriod(3)); // 6.67-20.01

    }

    @Test
    public void test_doc_data_custom_period_size() {
        PointsByPeriodEx instance = new PointsByPeriodEx(
                20);
        fill_instance_with_doc_points(instance);

        instance.setPeriodSize(2.1,PointsByPeriodEx.PeriodsBoundaryAlignment.HIGH);



    }

}