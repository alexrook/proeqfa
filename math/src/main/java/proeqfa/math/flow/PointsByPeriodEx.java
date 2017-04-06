package proeqfa.math.flow;

/**
 * Created by moroz on 05.04.17.
 */
public class PointsByPeriodEx extends PointsByPeriod {

    public enum PeriodsBoundaryAlignment {
        NONE, LOW, HIGH, EXPAND
    }

    private static final double NON_SET_PERIOD_SIZE = -1d;
    private static final int NON_SET_PERIOD_COUNT = -1;

    PeriodsBoundaryAlignment periodsAlign;
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
        calculate();
    }

    private void calculate() {
        if (periodsAlign != PeriodsBoundaryAlignment.EXPAND) {
            customPeriodsCount = (int) (getDiapasonSize() / customPeriodSize);
            shorterPeriodSize = round(getDiapasonSize() - customPeriodSize * customPeriodsCount, getCalcPrecision());
            if (shorterPeriodSize > 0) {
                customPeriodSize++;
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
