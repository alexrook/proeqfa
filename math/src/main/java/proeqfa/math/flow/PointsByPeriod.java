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
    private int calcPrecision;
    private double periodSize;
    private ArrayList<Double> points = new ArrayList<>(133);


    public PointsByPeriod(double diapasonSize,
                          int calcPrecision) {
        setDiapasonSize(diapasonSize);
        if (calcPrecision >= 0) {
            this.calcPrecision = calcPrecision;
        }
    }

    public PointsByPeriod(double diapasonSize) {
        setDiapasonSize(diapasonSize);
        autoPrecision = true;
    }

    public void setDiapasonSize(double diapasonSize) {
        if (diapasonSize >= 0) {
            this.diapasonSize = diapasonSize;
        } else {
            this.diapasonSize = 0;
        }
    }

    public void addPoint(double point) {

        double diapasonHigh = diapasonSize + MathUtils.getMinValueForScale(getCalcPrecision());
        double diapasonLow = 0 - MathUtils.getMinValueForScale(getCalcPrecision());

        if ((diapasonSize == 0)
                || (point > diapasonHigh)
                || (point < diapasonLow)) {
            throw new IllegalArgumentException("out-of-range point:" + point
                    + ", diapason:" + diapasonSize);
        }

        setCalcPrecision(point);

        points.add(point);

    }

    private void setCalcPrecision(double point) {
        if (autoPrecision) {
            BigDecimal p = BigDecimal.valueOf(point);
            calcPrecision = calcPrecision < p.scale() ? p.scale() : calcPrecision;
        }
    }

    public double getPeriodSize() {
        double periodCount = getPeriodsCount();
        periodSize = round(diapasonSize / periodCount, calcPrecision);
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
                        + MathUtils.getMinValueForScale(calcPrecision) : getPeriodSize() * period;
    }

    protected double getPeriodLowBoundary(int period) {
        return period == 1 ? 0 - MathUtils.getMinValueForScale(calcPrecision) : getPeriodSize() * (period - 1);
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

    public int getCalcPrecision() {
        return calcPrecision;
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
