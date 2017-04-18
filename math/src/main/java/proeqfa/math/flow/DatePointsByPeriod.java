package proeqfa.math.flow;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by moroz on 18.04.17.
 */
public class DatePointsByPeriod extends PointsByPeriodScaled {

    public void addPoint(Date instant) {
        DateTime point=new DateTime(instant);
        System.out.println("year: "+point.year().get());
        System.out.println("month: "+point.monthOfYear().get());
        System.out.println("hour of day: "+point.hourOfDay().get());
        System.out.println("minute of hour: "+point.minuteOfHour().get());
        System.out.println("second of minute: "+point.secondOfMinute().get());
        System.out.println("millisecond of second: "+point.millisOfSecond().get());


    }

    public void setDiapasonLowBoundary(Date instant) {

    }

    public void setDiapasonHighBoundary(Date instant) {

    }

    public List<Date> getPointsForPeriod(int period) {
        return null;
    }

    public static void main(String[] args) {
        DatePointsByPeriod dp=new DatePointsByPeriod();

        dp.addPoint(new Date());

        DateFormat df=new SimpleDateFormat("yyyy:MM:dd hh:mm:ss SS", Locale.ENGLISH);

        DateFormat df2=new SimpleDateFormat("yyyy:MM:dd", Locale.ENGLISH);

        java.sql.Timestamp ts=java.sql.Timestamp.valueOf("1234-9-1 03:15:43.3");
        dp.addPoint(ts);

      
        try {
            df.parse("2001:12:11 09:11:15 34");
            df2.parse("1712:01:03");
            dp.addPoint(df.getCalendar().getTime());
            dp.addPoint(df2.getCalendar().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
