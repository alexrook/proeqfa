package proeqfa.math.flow;

import proeqfa.math.commons.MathUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by moroz on 29.03.17.
 */
public class EventsByPeriod {

    private boolean autoPrecision;
    private int eventsPeriodLength, eventPointPrecision;
    private double periodSize;
    private ArrayList<Double> events = new ArrayList<>(133);


    public EventsByPeriod(int eventsPeriodLength,
                          int eventPointPrecision) {
        this.eventsPeriodLength = eventsPeriodLength;
        this.eventPointPrecision = eventPointPrecision;
    }

    public EventsByPeriod(int eventsPeriodLength) {
        this.eventsPeriodLength = eventsPeriodLength;
        autoPrecision = true;
    }

    public void addEvent(double eventPoint) {
        setPrecision(eventPoint);
        events.add(eventPoint);
    }

    private void setPrecision(double eventPoint) {
        if (autoPrecision) {
            BigDecimal v = BigDecimal.valueOf(eventPoint);
            eventPointPrecision = eventPointPrecision < v.scale() ? v.scale() : eventPointPrecision;
        }
    }

    public double getPeriodSize() {
        double periodCountByRule = getPeriodCountBySturgesRule(getEventsCount());
        periodSize = round(eventsPeriodLength / periodCountByRule, eventPointPrecision);
        return periodSize;
    }

    public int getEventsCount() {
        return events.size();
    }

    public int getPeriodsCount() {
        return getPeriodCountBySturgesRule(getEventsCount());
    }

    protected double getPeriodHighBoundary(int period) {
        return period == getPeriodsCount() ?
                getPeriodSize() * period
                        + MathUtils.getMinValueForScale(eventPointPrecision) : getPeriodSize() * period;
    }

    protected double getPeriodLowBoundary(int period) {
        return period == 1 ? 0 - MathUtils.getMinValueForScale(eventPointPrecision) : getPeriodSize() * (period - 1);
    }

    public int getEventsCountByPeriod(int period) {
        double periodHigh = getPeriodHighBoundary(period);
        double periodLow = getPeriodLowBoundary(period);
        int ret = 0;
        for (Double event : events) {
            if (((event > periodLow)
                    && (event < periodHigh))
                    || (event == periodHigh)) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * @param eventsCount число событий
     * @return целое число периодов по правилу Стерджеса
     * TODO:do you really need an integer number of periods?
     */
    public static int getPeriodCountBySturgesRule(int eventsCount) {
        double k = 1d;
        if (eventsCount > 0) {
            k = round(1d + 3.3d * Math.log10(eventsCount), 0);
        }
        return (int) k;
    }

    protected static double round(double value, int digitsAfterDecimalPoint) {
        return MathUtils.round(value, digitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
    }


}
