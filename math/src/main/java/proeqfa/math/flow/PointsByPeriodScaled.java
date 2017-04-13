package proeqfa.math.flow;

import proeqfa.math.commons.MathUtils;

/**
 * Created by moroz on 13.04.17.
 */
public class PointsByPeriodScaled extends PointsByPeriodEx {

    private double diapasonNextStep = -1;

    public PointsByPeriodScaled() {
        super(0);
    }

    public PointsByPeriodScaled(int calcPrecision) {
        super(0, calcPrecision);
    }

    @Override
    public void addPoint(double point) {
        double diapason = getDiapasonSize();

        if (diapason < point) {

            diapason = point;

            double mb = MathUtils.getMaxBitOfNumber(point);

            if (mb > diapasonNextStep) {
                diapasonNextStep = mb;
                diapason += diapasonNextStep;
            }

            setDiapasonSize(diapason);
        }

        super.addPoint(point);
    }
}
