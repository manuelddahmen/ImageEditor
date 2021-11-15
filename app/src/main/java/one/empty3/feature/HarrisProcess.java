package one.empty3.feature;

import one.empty3.io.ProcessFile;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;

import android.graphics.Bitmap;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import one.empty3.library.core.lighting.Colors;

public class HarrisProcess extends ProcessFile {
    PixM m;

    public HarrisProcess() {

    }

    public boolean process(File in, File out) {
        try {
            Properties resourceBundle = new Properties();
            resourceBundle.load(new FileInputStream(new File("resources/settings.properties")));
            Bitmap img = ImageIO.read
                    (in);
            PixM m;
            m = PixM.getPixM(img, Double.parseDouble(resourceBundle.getProperty("HarrisProcess.maxRes")));

            PixM m2;

            HarrisToPointInterest h = new HarrisToPointInterest(2, 2);

            m2 = m.applyFilter(h);

            LocalExtrema le;

            le = new LocalExtrema(m2.columns, m2.lines, 7, 5);

            m2 = le.filter(new
                    M3(m2, 1, 1)).getImagesMatrix()[0][0];

            ImageIO.write(m2.getImage(), "JPEG", out);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
