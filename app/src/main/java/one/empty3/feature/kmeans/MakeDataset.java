package one.empty3.feature.kmeans;

import java.io.*;

import one.empty3.feature.PixM;
import one.empty3.io.ProcessFile;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import javax.imageio.ImageIO;

import java.io.File;
import java.util.*;

/***
 line : l, c, r, g, b
 */
public class MakeDataset {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MakeDataset(File image,
                       File outputCsv, int res) {
        try {
            Bitmap img = ImageIO.read
                    (image);
            PixM pix = null;
            if (res > 0)

                pix = PixM.getPixM(img, res);
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                pix = new PixM(img);
            }

            PrintWriter pw = new PrintWriter(outputCsv);
            for (int l = 0; l < pix.getLines(); l++)
                for (int c = 0; c < pix.getColumns(); c++) {
                    if(pix.luminance(c, l)>0.1) { // ADDED
                        pix.setCompNo(0);
                        double r = pix.get(l, c);

                        pix.setCompNo(1);
                        double g = pix.get(l, c);

                        pix.setCompNo(2);
                        double b = pix.get(l, c);

                        pw.println("" + l + " " + c + " " +
                                r + " " + g + " " + b);
                    }
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeFeature
            (HashMap<Integer, double[]> data,
             File csvout) {
        try {

            PrintWriter pw = new PrintWriter(csvout);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < data.get(i).length; j++) {
                    pw.print(data.get(i)[j] + " ");
                }
                pw.println();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

} 
