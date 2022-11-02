package one.empty3.feature20220726;


import java.io.File;

import one.empty3.library.Point3D;
import one.empty3.io.ProcessFile;


import javaAnd.awt.image.imageio.ImageIO;

import one.empty3.feature20220726.*;

import java.util.logging.*;

public class ProxyValue extends ProcessFile {

    public boolean process(File in, File out) {
        double min = 0.3;
        if (!in.getName().endsWith(".jpg"))
            return false;
        File file = in;
        PixM original = null;

        try {
            original = PixM.getPixM(ImageIO.read(in), maxRes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
            // assertTrue(false);

        }
        int p = 0;
        PixM copy = new PixM(original.getColumns(), original.getLines());


        for (int i = 0; i < original.getColumns(); i++)

            for (int j = 0; j < original.getLines(); j++)


                if (original.luminance(i, j) < min) {

                    searchFromTo(original, copy, i, j, min * 2);
                    p++;

                } else {
                    copy.set(i, j, original.get(i, j));
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
            PixM original, PixM copy, int i, int j, double min) {
        Point3D p = null;
        int i2 = i, j2 = j;
        
     /*   for(int k=0; k<original.getColumns()*original.getLines();k++)
                { 
            
              int [] k1 = new int[] {incr[(k/2)%8], 
                                     incr[(k/2+1)%8]};
                i2+= k1[0];
                j2 += k1[1];
            
           */
        for (int l = 1; l < original.getColumns(); l++) {
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

    public void copyPixel(PixM m1, int i, int j,
                          PixM m2, int i2, int j2) {
        for (int c = 0; c < 3; c++) {

            m1.setCompNo(c);
            m2.setCompNo(c);

            m2.set(i2, j2, m1.get(i, j));
        }

    }
} 
