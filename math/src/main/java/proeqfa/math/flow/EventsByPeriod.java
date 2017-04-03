package proeqfa.math.flow;

import proeqfa.math.commons.MathUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by moroz on 29.03.17.
 */
public class EventsByPeriod {

    private int equipmentCount, eventsPeriodLength, eventPointPrecision;
    private double periodSize;
    private ArrayList<Double> events = new ArrayList<>(133);


    public EventsByPeriod(int equipmentCount,
                          int eventsPeriodLength,
                          int eventPointPrecision) {
        this.equipmentCount = equipmentCount;
        this.eventsPeriodLength = eventsPeriodLength;
        this.eventPointPrecision = eventPointPrecision;
    }

    public void addEvent(double eventPoint) {
        events.add(eventPoint);
    }

    public double getPeriodSize() {
        periodSize = round(eventsPeriodLength / getPeriodCountBySturgesRule(getEventsCount()));
        return periodSize;
    }

    public int getEventsCount() {
        return events.size();
    }

    public int getPeriodsCount() {
        return getPeriodCountBySturgesRule(getEventsCount());
    }


    public int getEventsCountByPeriod(int period) {
        double periodHigh = period == getPeriodsCount() ? getPeriodSize() * period + 1 : getPeriodSize() * period;
        double periodLow = period == 1 ? 0 - 1 : getPeriodSize() * (period - 1);
        int ret = 0;
        for (Double event : events) {
            if ((event > periodLow) && (event < periodHigh)) {
                ret++;
            }

        }
        return ret;
    }

    public static int getPeriodCountBySturgesRule(int eventsCount) {
        double k = 1d;
        if (eventsCount > 0) {
            k = MathUtils.round(1d + 3.3d * Math.log10(eventsCount)
                    , 0, BigDecimal.ROUND_HALF_UP);
        }
        return (int) k;
    }

    private double round(double value) {
        return MathUtils.round(value, eventPointPrecision, BigDecimal.ROUND_HALF_UP);
    }

}
