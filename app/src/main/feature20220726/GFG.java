/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;
// Implementing Coppersmith Winograd Algorithm in Java

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class GFG {

    public static boolean coppersmithWinograd(double[][] M1,
                                              double[][] M2,
                                              double[][] M3, int n) {
        double[][] a = new double[n][1];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            a[i][0] = rand.nextInt() % 2;
        }

        double[][] M2a = new double[n][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < n; k++) {
                    M2a[i][j]
                            = M2a[i][j] + M2[i][k] * a[k][j];
                }
            }
        }

        double[][] M3a = new double[n][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < n; k++) {
                    M3a[i][j]
                            = M3a[i][j] + M3[i][k] * a[k][j];
                }
            }
        }

        double[][] M12a = new double[n][1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < n; k++) {
                    M12a[i][j]
                            = M12a[i][j] + M1[i][k] * M2a[k][j];
                }
            }
        }
        for (int i = 0; i < n; i++) {
            M12a[i][0] -= M3a[i][0];
        }
        boolean sameResultantMatrix = true;
        for (int i = 0; i < n; i++) {
            if (M12a[i][0] == 0)
                continue;
            else
                sameResultantMatrix = false;
        }
        return sameResultantMatrix;
    }

    // Driver's Function m1*m2==m3
    public static void inv(double[][] M2, int n) {

        /// "Input the dimension of the matrices: "


        // "Input the 1st or M1 matrix: "
        double[][] M1 = {{1, 2}, {3, 4}};
        // "Input the 2nd or M2 matrix: "

        // double[][] M2 = { { 2, 0 }, { 1, 2 } };

        // "Input the result or M3 matrix: "
        double[][] M3 = {{4, 4}, {10, 8}};

        if (coppersmithWinograd(M1, M2, M3, n))
            Logger.getAnonymousLogger().log(Level.INFO, "Resultant matrix is Matching");
        else
            Logger.getAnonymousLogger().log(Level.INFO, "Resultant matrix is not Matching");
    }
}
