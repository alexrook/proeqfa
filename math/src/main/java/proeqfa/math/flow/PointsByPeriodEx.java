package proeqfa.math.flow;

/**
 * Created by moroz on 05.04.17.
 */
public class PointsByPeriodEx extends PointsByPeriod {

    public enum PeriodsBoundaryAlignment {
        NONE, LOW, HIGH, EXPAND
    }

    PeriodsBoundaryAlignment periodsAlign;

    public PointsByPeriodEx(double diapasonSize,
                            int calcPrecision) {
        super(diapasonSize, calcPrecision);
    }

    public PointsByPeriodEx(double diapasonSize) {
        super(diapasonSize);
    }

    public void setPeriodSize(double periodSize, PeriodsBoundaryAlignment periodsAlign) {
        this.periodsAlign= periodsAlign;
    }

}

