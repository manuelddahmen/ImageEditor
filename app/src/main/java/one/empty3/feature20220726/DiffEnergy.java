package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.image.BufferedImage;

import java.io.*;
import java.util.Objects;

public class DiffEnergy extends ProcessFile {
    static PrintWriter pw;
    double[] energy = new double[3];
    private PixM i2;
    private PixM i1;

    public DiffEnergy() {
        super();
    }

    public void setPixMS(int img1, int img2) {
        i1 = PixM.getPixM(Objects.requireNonNull(ImageIO.read(getStackItem(img1))), maxRes);
        i2 = PixM.getPixM(Objects.requireNonNull(ImageIO.read(getStackItem(img2))), maxRes);


    }

    public double diffEnergy() {
        energy = new double[]{0.0, 0.0, 0.0};
        for (int i = 0; i < i1.getColumns(); i++)
            for (int j = 0; j < i1.getLines(); j++)
                for (int c = 0; c < 3; c++) {
                    i1.setCompNo(c);
                    i2.setCompNo(c);
                    energy[c] += Math.abs(
                            i1.get((int) i, (int) j) - i2.get((int) i, (int) j)
                    );
                }
        return energy[0] + energy[1] + energy[2];
    }
}
