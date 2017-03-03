package proeqfa.math.estimation;

import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author moroz
 */
public interface IRelativeImportanceVectorTestData extends IEstimationTestData {

    public RealMatrix getExpectedRelativeImportanceVector();
    public double getEvaluationRate();
}
