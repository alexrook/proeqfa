package proeqfa.math.flow;

import proeqfa.math.commons.MathUtils;

/**
 * Created by moroz on 13.04.17.
 */
public class PointsByPeriodScaled extends PointsByPeriodEx {

    private double diapasonNextStep = -1;
    private boolean autoExpandDiapason = true;

    public PointsByPeriodScaled() {
        super(0);
    }

    public PointsByPeriodScaled(int calcPrecision) {
        super(0, calcPrecision);
    }

    @Override
    public void addPoint(double point) {
        if (autoExpandDiapason) {
            double diapason = getDiapasonSize();

            if (diapason < point) {

                double mb = MathUtils.getMinHighBitOfNumber(point);

                diapasonNextStep = mb > diapasonNextStep ? mb : diapasonNextStep;

                diapason = MathUtils.roundToHighBit(point);

                diapason += diapasonNextStep;

                setDiapasonSize(diapason);
            }
        }

        super.addPoint(point);
    }


    public void setDiapasonHighBoundary(double diapasonBoundary) {
        autoExpandDiapason = false;
        setDiapasonSize(diapasonBoundary);
    }
}
