/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proeqfa.math.estimation;

import java.io.PrintStream;

/**
 *
 * @author moroz
 */
public class TestUtils {

    public static void printMatrix(Float[][] array, int xSize, int ySize, PrintStream out) {

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                String v = array[i][j] == null ? "n" : Float.toString(array[i][j]);
                out.append(" ")
                        .append(v)
                        .append("  ")
                        .append("|");
            }
            out.append("\n")
               //     .append("-")
                    .append("\n");

        }
    }
}
