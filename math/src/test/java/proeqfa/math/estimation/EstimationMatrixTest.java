/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proeqfa.math.estimation;

import org.junit.*;
import static org.junit.Assert.*;
import static java.lang.System.out;

/**
 *
 * @author moroz
 */
public class EstimationMatrixTest {

    public EstimationMatrixTest() {
    }

    /**
     * Test of addPairwiseCompare method, of class ExpectationMatrix.
     */
    @Test
    public void testAddPairwiseCompare01() {
        ThreeLogicValues logicValuesView3 = ThreeLogicValues.getView3();
        //more=1, less=0, same=0.5f

        EstimationMatrix estimationMatrix = new EstimationMatrix(3, logicValuesView3);

        PairwiseCompareMatrix matrix1 = new PairwiseCompareMatrix(3, logicValuesView3);
        matrix1.setMore(0, 1);
        matrix1.setMore(0, 2);
        matrix1.setLess(1, 2);
//        out.println("---matrix1---");
//        TestUtils.printPairwiseMatrix(matrix1);
//        out.println("---more decision--");
        estimationMatrix.addPairwiseCompare(matrix1);
//        TestUtils.printMatrix(estimationMatrix.moreDecisionMarix, 3, 3, out);

        PairwiseCompareMatrix matrix2 = new PairwiseCompareMatrix(3, logicValuesView3);
        matrix2.setSame(0, 1);
        matrix2.setSame(0, 2);
        matrix2.setSame(1, 2);
//        out.println("---matrix2---");
//        TestUtils.printPairwiseMatrix(matrix2);
//        out.println("---more decision--");
        estimationMatrix.addPairwiseCompare(matrix2);
//        TestUtils.printMatrix(estimationMatrix.moreDecisionMarix, 3, 3, out);

        PairwiseCompareMatrix matrix3 = new PairwiseCompareMatrix(3, logicValuesView3);
        matrix3.setMore(0, 1);
        matrix3.setSame(0, 2);
        matrix3.setLess(1, 2);
//        out.println("---matrix3---");
//        TestUtils.printPairwiseMatrix(matrix3);
//        out.println("---more decision--");
        estimationMatrix.addPairwiseCompare(matrix3);
//        TestUtils.printMatrix(estimationMatrix.moreDecisionMarix, 3, 3, out);

        estimationMatrix.calculate();
        Float[][] ret = estimationMatrix.getResultMatrix();

        out.println("---estimation matrix--");
        TestUtils.printMatrix(ret, 3, 3, out);

    }

   
}
