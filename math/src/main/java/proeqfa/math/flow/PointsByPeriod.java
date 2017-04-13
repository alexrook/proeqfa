package proeqfa.math.flow;

import proeqfa.math.commons.MathUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by moroz on 29.03.17.
 */
public class PointsByPeriod implements Iterable<Double> {

    private final ArrayList<Double> points = new ArrayList<>(133);
    private boolean autoPrecision;
    private double diapasonSize;
    private int calcPrecision;


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

    /**
     * @param pointsCount число точек
     * @return целое число периодов по правилу Стерджеса
     * TODO:do you really need an integer number of periods?
     */
    public static int getPeriodCountBySturgesRule(int pointsCount) {
        double k = 1d;
        if (pointsCount > 0) {
            k = MathUtils.round(1d + 3.3d * Math.log10(pointsCount), 0);
        }
        return (int) k;
    }

    public void addPoint(double point) {

        if (!checkValue(point)) {
            throw new IllegalArgumentException("out-of-range point:" + point
                    + ", diapason:" + diapasonSize);
        }
        setCalcPrecision(point);

        points.add(point);

    }

    protected boolean checkValue(double value) {
        double diapasonHigh = diapasonSize + getMinVal();
        double diapasonLow = 0 - getMinVal();

        return !((diapasonSize == 0)
                || (value > diapasonHigh)
                || (value < diapasonLow));
    }

    public double getPeriodSize() {
        return round(diapasonSize / getPeriodsCount());
    }

    public int getPointsCount() {
        return points.size();
    }

    public int getPeriodsCount() {
        return getPeriodCountBySturgesRule(getPointsCount());
    }

    protected double getPeriodHighBoundary(int period) {
        double r = round(getPeriodSize() * period);
        return period == getPeriodsCount() ?
                round(getDiapasonSize() + getMinVal()) : r; //18.8+0.01 returns 18.810000...2
    }

    protected double getPeriodLowBoundary(int period) {
        double r = round(getPeriodSize() * (period - 1));//try 9*2.1 and see why round here
        return period == 1 ? 0 - getMinVal() : r;
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

    private void setCalcPrecision(double point) {
        if (autoPrecision) {
            BigDecimal p = BigDecimal.valueOf(point);
            calcPrecision = calcPrecision < p.scale() ? p.scale() : calcPrecision;
        }
    }

    public double getDiapasonSize() {
        return diapasonSize;
    }

    protected void setDiapasonSize(double diapasonSize) {
        if (diapasonSize >= 0) {
            this.diapasonSize = diapasonSize;
        } else {
            this.diapasonSize = 0;
        }
    }

    protected double getMinVal() {
        return MathUtils.getMinValueForScale(getCalcPrecision());
    }

    protected double round(double value) {
        return MathUtils.round(value, getCalcPrecision());
    }

    @Override
    public Iterator<Double> iterator() {
        return points.iterator();
    }


}
