package proeqfa.math.estimation;

import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author moroz
 */
public interface IRelativeImportanceVectorTestData extends IEstimationTestData {

    RealMatrix getExpectedRelativeImportanceVector();
    double getEvaluationRate();
     
    
}
