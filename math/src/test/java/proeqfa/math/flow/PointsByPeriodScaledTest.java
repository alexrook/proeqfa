package proeqfa.math.flow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import proeqfa.math.TestBase;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by moroz on 13.04.17.
 */
public class PointsByPeriodScaledTest extends TestBase {


    double[] doc_points_array = {1, 1, 3.5, 4.5,
            7.3, 8.1, 8.31, 9.5,
            11.9, 12.2, 13.91, 14.1,
            17.9, 18.3, 19.5, 19.9};

    double[] user_data01_array = {
            0.1, 1, 3.5, 4.5,
            7.3, 8.1, 8.31, 9.5,
            11.9, 12.2, 13.91, 14.1
    };

    double[] user_data02_array = {
            0.25, 0.71, 0.97, 1.5,
            2.2, 2.9, 4.1, 4.7,
            6.53
    };

    double[] user_data03_array = {
            0.003, 0.01, 0.009, 0.00134
    };


    PointsByPeriodScaled doc_points, doc_points_autoPrecision,
            user_data01, user_data01_autoPrecision,
            user_data02, user_data02_autoPrecision,
            user_data03, user_data03_autoPrecision;


    @Override
    @Before
    public void setUp() {
        super.setUp();
        doc_points = new PointsByPeriodScaled(2);
        doc_points_autoPrecision = new PointsByPeriodScaled();
        for (double point : doc_points_array) {
            doc_points.addPoint(point);
            doc_points_autoPrecision.addPoint(point);
        }

        user_data01 = new PointsByPeriodScaled();
        user_data01_autoPrecision = new PointsByPeriodScaled(2);
        for (double point : user_data01_array) {
            user_data01.addPoint(point);
            user_data01_autoPrecision.addPoint(point);
        }

        user_data02 = new PointsByPeriodScaled();
        user_data02_autoPrecision = new PointsByPeriodScaled(2);
        for (double point : user_data02_array) {
            user_data02.addPoint(point);
            user_data02_autoPrecision.addPoint(point);
        }

        user_data03 = new PointsByPeriodScaled();
        user_data03_autoPrecision = new PointsByPeriodScaled(5);
        for (double point : user_data03_array) {
            user_data03.addPoint(point);
            user_data03_autoPrecision.addPoint(point);
        }

    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void test_doc_data() {
        for (PointsByPeriodEx instance : Arrays.asList(doc_points, doc_points_autoPrecision)) {
            assertEquals(16, instance.getPointsCount());
            assertEquals(5, instance.getPeriodsCount());
            System.out.println(instance.getDiapasonSize());
//            assertEquals(4d, instance.getPeriodSize(), 0d);
//            //see 10.1 doc diagram
//            assertEquals(3, instance.getPointsCountByPeriod(1));
//            assertEquals(2, instance.getPointsCountByPeriod(2));
//            assertEquals(4, instance.getPointsCountByPeriod(3));
//            assertEquals(3, instance.getPointsCountByPeriod(4));
//            assertEquals(4, instance.getPointsCountByPeriod(5));
        }
    }

}