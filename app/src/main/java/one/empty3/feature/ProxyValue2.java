package one.empty3.feature;


import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javax.imageio.ImageIO;
import java.io.File;

public class ProxyValue2 extends ProcessFile {

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        one.empty3.feature.PixM original = null;

        try {
            original = one.empty3.feature.PixM.getPixM(ImageIO.read(in), maxRes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
            // assertTrue(false);

        }
        int p = 0;
        one.empty3.feature.PixM copy = original.copy();
        for (int i = 0; i < original.columns; i++)

            for (int j = 0; j < original.lines; j++)


                if (copy.luminance(i, j) < 0.3) {

                    searchFromTo(original, copy, i, j, 0.3);
                    p++;

                }


        try {
            ImageIO.write(copy.getImage(), "jpg", out);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


        System.out.println("point " + p);

        return true;
    }


    public void searchFromTo(
            one.empty3.feature.PixM original, one.empty3.feature.PixM copy, int i, int j, double min) {
        Point3D p = null;
        int i2 = i, j2 = j;

     /*   for(int k=0; k<original.columns*original.lines;k++)
                {

              int [] k1 = new int[] {incr[(k/2)%8],
                                     incr[(k/2+1)%8]};
                i2+= k1[0];
                j2 += k1[1];

           */
        for (int l = 1; l < original.columns; l++) {
            int[] incr = new int[]{

                    -l, -l, 0, 1,
                    l, -l, 0, 1,
                    -l, l, 1, 0,
                    -l, -l, 1, 0

            };

            for (int sq = 0; sq < incr.length; sq += 4) {
                int pass = 0;
                for (int i3 = incr[sq]; i3 < l && pass > -1; i3 += incr[(sq + 2)]) {

                    for (int j3 = incr[(sq + 1)]; j3 < l && pass > -1; j3 += incr[(sq + 3)]) {

                        pass++;
                        i2 = i + i3;
                        j2 = j + j3;
                        p = null;


                        if (original.luminance(i2, j2) >= min) {


                            copyPixel(original, i2,
                                    j2,
                                    copy, i, j);
                            return;
                        }


                        if (pass > 2 * l) pass = -1;

                    }
                }
            }
        }
        // System.out.println("error not found");

        return;
    }


    public void copyPixel(one.empty3.feature.PixM m1, int i, int j,
                          PixM m2, int i2, int j2) {
        for (int c = 0; c < 3; c++) {

            m1.setCompNo(c);
            m2.setCompNo(c);

            m2.set(i2, j2, m1.get(i, j));
        }

    }
} 
