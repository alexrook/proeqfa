package proeqfa.math.flow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;

import java.util.Arrays;


import static org.junit.Assert.*;

/**
 * Created by moroz on 06.04.17.
 */
public class PointsByPeriodExTest extends TestBase {

    double[] doc_points_array = {1, 1, 3.5, 4.5,
            7.3, 8.1, 8.31, 9.5,
            11.9, 12.2, 13.91, 14.1,
            17.9, 18.3, 19.5, 19.9};

    double[] user_data01_array = {
            0.1, 1, 3.5, 4.5,
            7.3, 8.1, 8.31, 9.5,
            11.9, 12.2, 13.91, 14.1
    };

    PointsByPeriodEx doc_points, doc_points_auto, user_data01, user_data01_auto;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        doc_points = new PointsByPeriodEx(20, 2);
        doc_points_auto = new PointsByPeriodEx(20);
        for (double point : doc_points_array) {
            doc_points.addPoint(point);
            doc_points_auto.addPoint(point);
        }

        user_data01 = new PointsByPeriodEx(17, 2);
        user_data01_auto = new PointsByPeriodEx(17);
        for (double point : user_data01_array) {
            user_data01.addPoint(point);
            user_data01_auto.addPoint(point);
        }

    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void test_doc_data() {
        for (PointsByPeriodEx instance : Arrays.asList(doc_points, doc_points_auto)) {
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
    }

    @Test
    public void test_doc_data_custom_periods_count() {
        for (PointsByPeriodEx instance : Arrays.asList(doc_points, doc_points_auto)) {

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
    }

    @Test
    public void test_doc_data_custom_period_size() {
        for (PointsByPeriodEx instance : Arrays.asList(doc_points, doc_points_auto)) {

            instance.setPeriodSize(2.1, PointsByPeriodEx.PeriodsBoundaryAlignment.HIGH);
            assertEquals(10, instance.getPeriodsCount());
            assertEquals(1.1, instance.getShorterPeriodSize(), 0d);
            assertEquals(2.1, instance.getPeriodSize(), 0d);
            assertEquals(18.9, instance.getPeriodLowBoundary(10), 0d);
            assertEquals(20.01, instance.getPeriodHighBoundary(10), 0d); //precision=2
            assertEquals(6.3, instance.getPeriodHighBoundary(3), 0d); //3*2.1
            assertEquals(4.2, instance.getPeriodLowBoundary(3), 0d); //2*2.1

            //[[point]...]      ->low_boundary-high-boundary (period)
            // 1 1          -> 0-2.1  (1)
            // 3.5          -> 2.1-4.2 (2)
            // 4.5          -> 4.2-6.3 (3)
            // 7.3 8.1 8.31 -> 6.3-8.4 (4)
            // 9.5          -> 8.4-10.5 (5)
            // 11.9 12.2    -> 10.5-12.6 (6)
            // 13.9 14.1    -> 12.6-14.7 (7)
            //              -> 14.7-16.8 (8)
            // 17.9 18.3    -> 16.8-18.9 (9)
            // 19.5 19.9    -> 18.9-20 (10) //shorter period=1.1
            assertEquals(2, instance.getPointsCountByPeriod(1));
            assertEquals(1, instance.getPointsCountByPeriod(2));
            assertEquals(1, instance.getPointsCountByPeriod(3));
            assertEquals(3, instance.getPointsCountByPeriod(4));
            assertEquals(1, instance.getPointsCountByPeriod(5));
            assertEquals(2, instance.getPointsCountByPeriod(6));
            assertEquals(2, instance.getPointsCountByPeriod(7));
            assertEquals(0, instance.getPointsCountByPeriod(8));
            assertEquals(2, instance.getPointsCountByPeriod(9));
            assertEquals(2, instance.getPointsCountByPeriod(10));
        }
    }


    @Test
    public void test_doc_data_custom_period_size_count_mixed() {
        for (PointsByPeriodEx instance : Arrays.asList(doc_points, doc_points_auto)) {

            instance.setPeriodSize(2.1, PointsByPeriodEx.PeriodsBoundaryAlignment.HIGH);
            assertEquals(10, instance.getPeriodsCount());
            assertEquals(1.1, instance.getShorterPeriodSize(), 0d);
            assertEquals(2.1, instance.getPeriodSize(), 0d);
            assertEquals(18.9, instance.getPeriodLowBoundary(10), 0d);
            assertEquals(20.01, instance.getPeriodHighBoundary(10), 0d); //precision=2
            assertEquals(6.3, instance.getPeriodHighBoundary(3), 0d); //3*2.1
            assertEquals(4.2, instance.getPeriodLowBoundary(3), 0d); //2*2.1

            instance.setPeriodsCount(2);
            assertEquals(16, instance.getPointsCount());
            assertEquals(2, instance.getPeriodsCount());
            assertEquals(10d, instance.getPeriodSize(), 0d);
            assertEquals(8, instance.getPointsCountByPeriod(1));
            assertEquals(8, instance.getPointsCountByPeriod(2));
        }
    }

    @Test
    public void test_user_data01() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data01, user_data01_auto)) {
            std_user_data01_tester(instance);
        }
    }

    private void std_user_data01_tester(PointsByPeriod instance) {
        assertEquals(12, instance.getPointsCount());
        assertEquals(5, instance.getPeriodsCount()); //by starges rule
        assertEquals(3.4, instance.getPeriodSize(), 0d);
        assertEquals(2, instance.getPointsCountByPeriod(1));
        assertEquals(2, instance.getPointsCountByPeriod(2));
        assertEquals(4, instance.getPointsCountByPeriod(3));
        assertEquals(2, instance.getPointsCountByPeriod(4));
        assertEquals(2, instance.getPointsCountByPeriod(5));
    }

    @Test
    public void test_user_data01_custom_periods_count() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data01, user_data01_auto)) {
            std_user_data01_tester(instance);
            // 0.1 1 3.5 4.5 7.3 8.1 8.31 9.5 11.9 12.2 13.91 14.1 ->points
            instance.setPeriodsCount(2);
            assertEquals(12, instance.getPointsCount());
            assertEquals(2, instance.getPeriodsCount()); //custom number of periods
            assertEquals(8.5, instance.getPeriodSize(), 0d);
            assertEquals(7, instance.getPointsCountByPeriod(1));
            assertEquals(5, instance.getPointsCountByPeriod(2));

            instance.setPeriodsCount(3);
            // 0.1 1 3.5 4.5 (1) 7.3 8.1 8.31 9.5 (2) 11.9 12.2 13.91 14.1 (3) ->[[point]...] (period)
            assertEquals(12, instance.getPointsCount());
            assertEquals(3, instance.getPeriodsCount()); //custom number of periods
            assertEquals(5.67, instance.getPeriodSize(), 0d); //user data precision=2, so 17/3≈5.67
            assertEquals(4, instance.getPointsCountByPeriod(1)); // 0-5.67
            assertEquals(4, instance.getPointsCountByPeriod(2)); // 5.67-11.14
            assertEquals(4, instance.getPointsCountByPeriod(3)); // 11.14-17.01
        }
    }

    @Test
    public void test_user_data01_custom_period_size() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data01, user_data01_auto)) {

            instance.setPeriodSize(3.3,
                    PointsByPeriodEx.PeriodsBoundaryAlignment.LOW);
            assertEquals(6, instance.getPeriodsCount());
            assertEquals(0.5, instance.getShorterPeriodSize(), 0d); //17-3.3*5
            assertEquals(3.3, instance.getPeriodSize(), 0d);
            assertEquals(13.7, instance.getPeriodLowBoundary(6), 0d);//0.5+4*3.3
            assertEquals(17.01, instance.getPeriodHighBoundary(6), 0d); //precision=2
            assertEquals(7.1, instance.getPeriodHighBoundary(3), 0d); //0.5+3.3*2
            assertEquals(3.8, instance.getPeriodLowBoundary(3), 0d); //0.5+3.3*1

            //[[point]...]      ->low_boundary-high-boundary (period)
            // 0.1              -> 0-0.5        (1)
            // 1 3.5            -> 0.5-3.8      (2)
            // 4.5              -> 3.8-7.1      (3)
            // 7.3 8.1 8.31 9.5 -> 7.1-10.4     (4)
            // 11.9 12.2        -> 10.4-13.7    (5)
            // 13.91 14.1       -> 13.7-17      (6)

            assertEquals(1, instance.getPointsCountByPeriod(1));
            assertEquals(2, instance.getPointsCountByPeriod(2));
            assertEquals(1, instance.getPointsCountByPeriod(3));
            assertEquals(4, instance.getPointsCountByPeriod(4));
            assertEquals(2, instance.getPointsCountByPeriod(5));
            assertEquals(2, instance.getPointsCountByPeriod(6));

        }
    }

    @Test
    public void test_user_data01_custom_period_size_count_mixed() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data01, user_data01_auto)) {

            instance.setPeriodSize(5.7, PointsByPeriodEx.PeriodsBoundaryAlignment.LOW);
            assertEquals(3, instance.getPeriodsCount());
            assertEquals(5.6, instance.getShorterPeriodSize(), 0d); //5.6+2*5.7=17
            assertEquals(5.7, instance.getPeriodSize(), 0d);
            assertEquals(11.3, instance.getPeriodLowBoundary(3), 0d);//5.6+5.7*1
            assertEquals(17.01, instance.getPeriodHighBoundary(3), 0d);
            assertEquals(-0.01, instance.getPeriodLowBoundary(1), 0d); //precision=2
            assertEquals(5.6, instance.getPeriodLowBoundary(2), 0d); //3*2.1
            assertEquals(5.6, instance.getPeriodHighBoundary(1), 0d); //3*2.1

            instance.setPeriodsCount(2);
            assertEquals(12, instance.getPointsCount());
            assertEquals(2, instance.getPeriodsCount());
            assertEquals(8.5, instance.getPeriodSize(), 0d);
            assertEquals(7, instance.getPointsCountByPeriod(1));
            assertEquals(5, instance.getPointsCountByPeriod(2));

        }
    }


}