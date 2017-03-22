package proeqfa.math.commons;

import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by moroz on 22.03.17.
 */
public interface ICalcListener { //calculate events listener, mainly for testing purposes
    boolean onCalcStep(int step, RealMatrix stepResult); //if false then break calculation
}

