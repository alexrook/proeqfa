package proeqfa.math.estimation;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.*;
import static org.junit.Assert.*;
import static java.lang.System.out;
import proeqfa.math.commons.Array2DUtils;

/**
 *
 * @author moroz
 */
public class RelativeImportanceVectorTest {

    public RelativeImportanceVectorTest() {
    }

    /**
     * Test of getEarray method, of class RelativeImportanceVector.
     */
    @Test
    public void testGetEarray() {
        System.out.println("getEarray");
        double[] expResult = {1, 1, 1};
        double[] result = RelativeImportanceVector.getEarray(3);
        assertArrayEquals(expResult, result, 0);
    }

    /**
     * Test of calculate method, of class RelativeImportanceVector.
     */
    @Test
    public void testCalculate() {
        System.out.println("calculate");
        final int objCount = 3;

        Double[][] estimationMatrix = {
            {(3 / 6d), (5 / 6d), (4 / 6d)},
            {(1 / 6d), (3 / 6d), (1 / 6d)},
            {(2 / 6d), (5 / 6d), (3 / 6d)}
        };

        TestUtils.printMatrix(estimationMatrix, out);
        RelativeImportanceVector instance = new RelativeImportanceVector(objCount, 0.001);

        instance.calculate(Array2DUtils.toPrimitive(estimationMatrix));
      
        RealMatrix vector = instance.getRelativeImportanceVector();
        
        TestUtils.printMatrix(Array2DUtils.toObject(vector.getData()), out);
        
    }

}
