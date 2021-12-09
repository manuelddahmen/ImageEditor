package one.empty3.feature;

import java.awt.*;

import one.empty3.feature.FilterMatPixM;
import one.empty3.feature.M3;

/*
 * liste de régions petites ou grandes
 */
public class AfterGradientBeforeExtremum extends FilterMatPixM {
    private int angles;
    private double eigenValueLamba1 = 0.0, eigenValueLamba2 = 0.0;

    public AfterGradientBeforeExtremum(int angleIterations) {
        this.angles = angleIterations;
    }

    @Override
    public one.empty3.feature.M3 filter(one.empty3.feature.M3 maximumAndGradient00m01gx02gy) {
        one.empty3.feature.M3 original = maximumAndGradient00m01gx02gy;
        /* Choisir les points filtrés min (noirs //ou monochromes -- toutes ou  composantes annulés)
         * et repérer les zones par régions (r,theta)
         * Réunir les zones par valeur et proximité.
         *
         *  (GX,GY).(cos(theta),(sin(theta))
         * FAUX
         */

//        M3 filtered = new GradientFilter(original.columns, original.lines).filter(original);
        one.empty3.feature.M3 filtered = original;
        int ii;
        int ij = 0;
        double itere = angles;

        one.empty3.feature.M3 orientations = new one.empty3.feature.M3(original.columns, original.lines,
                1, 1);

        double angle = 0;
        for (ii = 0; ii < angles; ii++) {

            double r = 1.0;
            for (int i = 0; i < original.columns; i++)
                for (int j = 0; j < original.lines; j++)
                    for (int c = 0; c < 4; c++) {
                        if (maximumAndGradient00m01gx02gy.get(i, j, 0, 0) == 1.0) {
                            maximumAndGradient00m01gx02gy.setCompNo(c);
                            orientations.setCompNo(c);
                            double x = maximumAndGradient00m01gx02gy.get(i, j, 0, 1, c);
                            double y = maximumAndGradient00m01gx02gy.get(i, j, 0, 2, c);
                            double[] normale = {
                                    Math.cos(angle),
                                    Math.sin(angle)
                            };
                            double dotVec = r * (x * normale[0] + y * normale[1]);
                            if (orientations.get(i, j, 0, 0) <= dotVec)
                                orientations.set(i, j, 0, 0, angle);
                        }
                    }
            angle += 2 * Math.PI / angles;
        }

        // iterer sur les matrices => angle choisi 
        // quand maximum en ii tangente angle en i,j
        return orientations;
        // Rechercher les extremums.
    }

    @Override
    public void element(one.empty3.feature.M3 source, one.empty3.feature.M3 copy, int i, int j, int ii, int ij) {

    }

    @Override
    public one.empty3.feature.M3 norm(one.empty3.feature.M3 m3, M3 copy) {
        return null;
    }

    public double getEigenValueLamba1() {
        return eigenValueLamba1;
    }

    public void setEigenValueLamba1(double eigenValueLamba1) {
        this.eigenValueLamba1 = eigenValueLamba1;
    }

    public double getEigenValueLamba2() {
        return eigenValueLamba2;
    }

    public void setEigenValueLamba2(double eigenValueLamba2) {
        this.eigenValueLamba2 = eigenValueLamba2;
    }
}
