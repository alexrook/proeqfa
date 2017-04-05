package proeqfa.math.flow;

import proeqfa.math.commons.MathUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by moroz on 29.03.17.
 */
public class PointsByPeriod {

    private boolean autoPrecision;
    private double diapasonSize;
    private int pointPrecision;
    private double periodSize;
    private ArrayList<Double> points = new ArrayList<>(133);


    public PointsByPeriod(double diapasonSize,
                          int pointPrecision) {
        this.diapasonSize = diapasonSize;
        this.pointPrecision = pointPrecision;
    }

    public PointsByPeriod(double diapasonSize) {
        this.diapasonSize = diapasonSize;
        autoPrecision = true;
    }

    public void addPoint(double point) {
        setPrecision(point);
        points.add(point);
    }

    private void setPrecision(double point) {
        if (autoPrecision) {
            BigDecimal p = BigDecimal.valueOf(point);
            pointPrecision = pointPrecision < p.scale() ? p.scale() : pointPrecision;
        }
    }

    public double getPeriodSize() {
        double periodCount = getPeriodsCount();
        periodSize = round(diapasonSize / periodCount, pointPrecision);
        return periodSize;
    }

    public int getPointsCount() {
        return points.size();
    }

    public int getPeriodsCount() {
        return getPeriodCountBySturgesRule(getPointsCount());
    }

    protected double getPeriodHighBoundary(int period) {
        return period == getPeriodsCount() ?
                getPeriodSize() * period
                        + MathUtils.getMinValueForScale(pointPrecision) : getPeriodSize() * period;
    }

    protected double getPeriodLowBoundary(int period) {
        return period == 1 ? 0 - MathUtils.getMinValueForScale(pointPrecision) : getPeriodSize() * (period - 1);
    }

    public int getPointsCountByPeriod(int period) {
        double periodHigh = getPeriodHighBoundary(period);
        double periodLow = getPeriodLowBoundary(period);
        int ret = 0;
        for (Double point : points) {
            if (((point > periodLow)
                    && (point < periodHigh))
                    || (point == periodHigh)) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * @param pointsCount число точек
     * @return целое число периодов по правилу Стерджеса
     * TODO:do you really need an integer number of periods?
     */
    public static int getPeriodCountBySturgesRule(int pointsCount) {
        double k = 1d;
        if (pointsCount > 0) {
            k = round(1d + 3.3d * Math.log10(pointsCount), 0);
        }
        return (int) k;
    }

    protected static double round(double value, int digitsAfterDecimalPoint) {
        return MathUtils.round(value, digitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
    }


}
