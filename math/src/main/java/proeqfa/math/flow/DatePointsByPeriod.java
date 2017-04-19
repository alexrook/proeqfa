package proeqfa.math.flow;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by moroz on 18.04.17.
 */
public class DatePointsByPeriod extends PointsByPeriodScaled {

    public static class DateConverter {

        public enum DateToPoint {

//            YEAR(BigDecimal.ONE),
//            MONTH(YEAR.getValue().divide(BigDecimal.valueOf(12))),
//            DAY(MONTH.getValue().divide(BigDecimal.valueOf(31))),
//            HOUR(DAY.getValue().divide(BigDecimal.valueOf(24))),
//            MINUTE(HOUR.getValue().divide(BigDecimal.valueOf(60))),
//            SECOND(MINUTE.getValue().divide(BigDecimal.valueOf(60))),
//            MILLIS(SECOND.getValue().divide(BigDecimal.valueOf(1000)));

            YEAR(BigDecimal.TEN),
            MONTH(YEAR.getValue().divide(BigDecimal.valueOf(100))),
            DAY(MONTH.getValue().divide(BigDecimal.valueOf(100))),
            HOUR(DAY.getValue().divide(BigDecimal.valueOf(100))),
            MINUTE(HOUR.getValue().divide(BigDecimal.valueOf(100))),
            SECOND(MINUTE.getValue().divide(BigDecimal.valueOf(100))),
            MILLIS(SECOND.getValue().divide(BigDecimal.valueOf(10000)));


            private BigDecimal val;

            DateToPoint(BigDecimal val) {
                this.val = val;
            }

            public BigDecimal getValue() {
                return val;
            }
        }

        private Date instant;
        DateTime dateTime;
        private BigDecimal point;

        public DateConverter(BigDecimal point) {
            this.point = point;
            int year, month, day, hour, minute, second, millis;

            year = point.divide(DateToPoint.YEAR.getValue()).intValue();
            point = point.subtract(DateToPoint.YEAR.getValue().multiply(BigDecimal.valueOf(year)));

            month = point.divide(DateToPoint.MONTH.getValue()).intValue();
            point = point.subtract(DateToPoint.MONTH.getValue().multiply(BigDecimal.valueOf(month)));

            day = point.divide(DateToPoint.DAY.getValue()).intValue();
            point = point.subtract(DateToPoint.DAY.getValue().multiply(BigDecimal.valueOf(day)));

            hour = point.divide(DateToPoint.HOUR.getValue()).intValue();
            point = point.subtract(DateToPoint.HOUR.getValue().multiply(BigDecimal.valueOf(hour)));

            minute = point.divide(DateToPoint.MINUTE.getValue()).intValue();
            point = point.subtract(DateToPoint.MINUTE.getValue().multiply(BigDecimal.valueOf(minute)));

            second = point.divide(DateToPoint.SECOND.getValue()).intValue();
            point = point.subtract(DateToPoint.SECOND.getValue().multiply(BigDecimal.valueOf(second)));

            millis = point.divide(DateToPoint.MILLIS.getValue()).intValue();

            DateTime dt = new DateTime(year, month, day, hour, minute, second, millis);
            Calendar cal = new GregorianCalendar();
            cal.set(year, month, day, hour, minute, second);
            cal.set(Calendar.MILLISECOND, millis);
//            this.instant = dt.toDate();
            this.instant = cal.getTime();
            this.dateTime = dt;

        }

        public DateConverter(Date instant) {
            this.instant = instant;
            DateTime dt = new DateTime(this.instant);
            BigDecimal year, month, day, hour, minute, second, millis;
            year = BigDecimal.valueOf(dt.year().get());
            year = DateToPoint.YEAR.getValue().multiply(year);
            month = DateToPoint.MONTH.getValue().multiply(BigDecimal.valueOf(dt.monthOfYear().get()));
            day = DateToPoint.DAY.getValue().multiply(BigDecimal.valueOf(dt.dayOfMonth().get()));
            hour = DateToPoint.HOUR.getValue().multiply(BigDecimal.valueOf(dt.hourOfDay().get()));
            minute = DateToPoint.MINUTE.getValue().multiply(BigDecimal.valueOf(dt.minuteOfHour().get()));
            second = DateToPoint.SECOND.getValue().multiply(BigDecimal.valueOf(dt.secondOfMinute().get()));
            millis = DateToPoint.MILLIS.getValue().multiply(BigDecimal.valueOf(dt.millisOfSecond().get()));

            this.point = BigDecimal.ZERO;

            this.point = point.add(year).add(month).add(day).add(hour).add(minute).add(second).add(millis);

            this.dateTime = dt;

        }

        public BigDecimal numberValue() {
            return point;
        }

        public Date dateValue() {
            return instant;
        }
    }

    public void addPoint(Date instant) {
        DateTime point = new DateTime(instant);
//        System.out.println("year: " + point.year().get());
//        System.out.println("month: " + point.monthOfYear().get());
//        System.out.println("hour of day: " + point.hourOfDay().get());
//        System.out.println("minute of hour: " + point.minuteOfHour().get());
//        System.out.println("second of minute: " + point.secondOfMinute().get());
//        System.out.println("millisecond of second: " + point.millisOfSecond().get());
    }

    public void setDiapasonLowBoundary(Date instant) {

    }

    public void setDiapasonHighBoundary(Date instant) {

    }

    public List<Date> getPointsForPeriod(int period) {
        return null;
    }

    public static void main(String[] args) {
//        DatePointsByPeriod dp = new DatePointsByPeriod();
//
//        dp.addPoint(new Date());

        DateFormat df = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss SSS", Locale.ENGLISH);

        DateFormat df2 = new SimpleDateFormat("yyyy:MM:dd", Locale.ENGLISH);

        java.sql.Timestamp ts = java.sql.Timestamp.valueOf("1234-9-1 03:15:43.3");
//        dp.addPoint(ts);

//
//        try {
//            df.parse("2001:12:11 09:11:15 34");
//            df2.parse("1712:01:03");
//            dp.addPoint(df.getCalendar().getTime());
//            dp.addPoint(df2.getCalendar().getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        try {

            df.parse("1712:11:21 15:57:37 345");
            df.setTimeZone(TimeZone.getTimeZone("GMT 0:00"));
            DateConverter dc = new DateConverter(df.getCalendar().getTime());
            System.out.println(dc.dateTime);
            System.out.println(dc.numberValue());
            dc = new DateConverter(dc.numberValue());
            System.out.println(dc.dateValue());
            System.out.println(dc.numberValue());
            System.out.println(dc.dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println("----");
//        ts = java.sql.Timestamp.valueOf("1712-12-03 00:07:01.00");
//        DateConverter dc = new DateConverter(new Date(ts.getTime()));
//        System.out.println(dc.dateValue());
//        System.out.println(dc.numberValue());
//        dc = new DateConverter(dc.numberValue());
//        System.out.println(dc.dateValue());
//        System.out.println(dc.numberValue());
//        System.out.println(dc.dateTime.toString());

    }
}
