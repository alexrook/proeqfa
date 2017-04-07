package proeqfa.math.flow;

import proeqfa.math.commons.MathUtils;

/**
 * Created by moroz on 05.04.17.
 */
public class PointsByPeriodEx extends PointsByPeriod {

    public enum PeriodsBoundaryAlignment {
        NONE, LOW, HIGH, EXPAND
    }

    private static final double NON_SET_PERIOD_SIZE = -1d;
    private static final int NON_SET_PERIOD_COUNT = -1;

    PeriodsBoundaryAlignment periodsAlign = PeriodsBoundaryAlignment.NONE;
    private double customPeriodSize = NON_SET_PERIOD_SIZE, shorterPeriodSize = NON_SET_PERIOD_SIZE;
    private int customPeriodsCount = NON_SET_PERIOD_COUNT;

    public PointsByPeriodEx(double diapasonSize,
                            int calcPrecision) {
        super(diapasonSize, calcPrecision);
    }

    public PointsByPeriodEx(double diapasonSize) {
        super(diapasonSize);
    }

    public void setPeriodSize(double periodSize, PeriodsBoundaryAlignment periodsAlign) {
        if (!checkValue(periodSize)) {
            throw new IllegalArgumentException("out-of-range period:" + periodSize
                    + ", diapason:" + getDiapasonSize());
        }
        this.periodsAlign = periodsAlign;
        this.customPeriodSize = periodSize;
        calculate();
    }

    private void calculate() {
        if (periodsAlign != PeriodsBoundaryAlignment.EXPAND) {
            customPeriodsCount = (int) (getDiapasonSize() / customPeriodSize);
            shorterPeriodSize = round(getDiapasonSize() - customPeriodSize * customPeriodsCount, getCalcPrecision());
            if (shorterPeriodSize > 0) {
                customPeriodsCount++;
                if (periodsAlign == PeriodsBoundaryAlignment.NONE) {
                    throw new IllegalArgumentException("unsupported boundary alignment for diapason="
                            + getDiapasonSize()
                            + ", period size=" + customPeriodSize);
                }
            }
        } else {
            //TODO
        }
    }

    @Override
    protected double getPeriodHighBoundary(int period) {
        switch (periodsAlign) {
            case HIGH: {
                if (period == getPeriodsCount()) {
                    return getDiapasonSize() + MathUtils.getMinValueForScale(getCalcPrecision());
                }
                return super.getPeriodHighBoundary(period);

            }
            case LOW: {
                double r = getPeriodSize() * (period - 1);
                if (period == 1) {
                    return round(shorterPeriodSize, getCalcPrecision());
                } else if (period == getPeriodsCount()) {
                    return round(shorterPeriodSize + r + MathUtils.getMinValueForScale(getCalcPrecision()), getCalcPrecision());
                } else {
                    return round(shorterPeriodSize + r, getCalcPrecision());
                }
            }
            default:
                return super.getPeriodHighBoundary(period);
        }

    }

    @Override
    protected double getPeriodLowBoundary(int period) {
        switch (periodsAlign) {
            case LOW: {
                if (period > 1) {
                    return round(shorterPeriodSize + getPeriodSize() * (period - 2) //diapason-shorter-other_periods
                            , getCalcPrecision()
                    );
                }
                return super.getPeriodLowBoundary(period);
            }
            default:
                return super.getPeriodLowBoundary(period);
        }
    }

    @Override
    public double getPeriodSize() {
        if (customPeriodSize == NON_SET_PERIOD_SIZE) {
            return super.getPeriodSize();
        } else {
            return customPeriodSize;
        }
    }

    public void setPeriodsCount(int periodsCount) {
        if (periodsCount > 0) {
            this.customPeriodsCount = periodsCount;
            this.customPeriodSize = NON_SET_PERIOD_SIZE;
        }
        this.periodsAlign = PeriodsBoundaryAlignment.NONE;
    }

    @Override
    public int getPeriodsCount() {
        if (customPeriodsCount == NON_SET_PERIOD_COUNT) {
            return super.getPeriodsCount();
        } else {
            return customPeriodsCount;
        }
    }

    public double getShorterPeriodSize() {
        return shorterPeriodSize;
    }

    public PeriodsBoundaryAlignment getPeriodsAlign() {
        return periodsAlign;
    }

}

