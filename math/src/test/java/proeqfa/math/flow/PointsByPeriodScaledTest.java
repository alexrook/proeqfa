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

    double[] user_data04_array = {
            12.0078, 100.003, 100.01, 101.5, 107.3
    };

    PointsByPeriodScaled doc_points, doc_points_autoPrecision,
            user_data01, user_data01_autoPrecision,
            user_data02, user_data02_autoPrecision,
            user_data03, user_data03_autoPrecision,
            user_data04, user_data04_autoPrecision;


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

        user_data04 = new PointsByPeriodScaled();
        user_data04_autoPrecision = new PointsByPeriodScaled(3);
        for (double point : user_data04_array) {
            user_data04.addPoint(point);
            user_data04_autoPrecision.addPoint(point);
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
            assertEquals(doc_points_array.length, instance.getPointsCount());
            //max(doc_points_array)=19.9 minHighBit=10, roundToHighBit(19.9)=10 => 10+10=20
            assertEquals(20, instance.getDiapasonSize(), 0d);
        }
    }

    @Test
    public void test_user_data01() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data01, user_data01_autoPrecision)) {
            assertEquals(user_data01_array.length, instance.getPointsCount());
            assertEquals(5, instance.getPeriodsCount());
            //max(user_data01_array)=14.1 minHighBit=10, roundToHighBit(14.1)=10 => 10+10=20
            assertEquals(20, instance.getDiapasonSize(), 0d);
        }
    }

    @Test
    public void test_user_data02() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data02, user_data02_autoPrecision)) {
            assertEquals(user_data02_array.length, instance.getPointsCount());
            //max(user_data02_array)=6.53 minHighBit=1, roundToHighBit(6.53)=7 => 6+1=7
            assertEquals(7, instance.getDiapasonSize(), 0d);
        }
    }

    @Test
    public void test_user_data03() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data03, user_data03_autoPrecision)) {
            assertEquals(user_data03_array.length, instance.getPointsCount());
            //max(user_data03_array)=0.01 minHighBit=0.01, roundToHighBit(0.01)=0.01,so 0.01+0.01=0.02
            assertEquals(0.02, instance.getDiapasonSize(), 0d);
        }
    }

    @Test
    public void test_user_data04() {
        for (PointsByPeriodEx instance : Arrays.asList(user_data04, user_data04_autoPrecision)) {
            assertEquals(user_data04_array.length, instance.getPointsCount());
            //max(user_data04_array)=107.3 minHighBit=100, roundToHighBit(107.3)=100,so 100+100=200
            assertEquals(200, instance.getDiapasonSize(), 0d);
        }
    }

}