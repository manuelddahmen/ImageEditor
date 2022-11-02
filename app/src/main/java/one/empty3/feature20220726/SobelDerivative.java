package one.empty3.feature20220726;

import javaAnd.awt.Color;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/*
sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?
*/
public class SobelDerivative extends ProcessFile {
    private final boolean isX;
    private int lines = 3;
    private int columns = 3;
    double[] sobelX = new double[]{-1, -2, -1, 0, 0, 0
            , 1, 2, 1};
    double[] sobelY = new double[]{-1, 0, -1,
            -2, 0, 2
            , 1, 0, 1};

    public SobelDerivative() {
        this.isX = true;
    }

    private void fill() {

    }

    public double x(int i, int j) {
        return sobelX[i * lines + j];
    }

    public double y(int i, int j) {
        return sobelY[i * lines + j];
    }

    public void theta(int i, int j) {

    }

    public double filter(PixM p, int x, int y) {
        int dy = (int) (lines / 2);
        int dX = (int) (columns / 2);
        double sumX = 0, sumY = 0;
        for (int c = 0; c < 3; c++) {
            for (int l = 0; l < 3; l++) {
                sumX += sobelX[l * columns + c] * p.get(x + c - 1, y + l - 1);
                sumY += sobelY[l * columns + c] * p.get(x + c - 1, y + l - 1);
            }
        }
        return Math.sqrt(sumX * sumX + sumY * sumY);
    }

    @Override
    public boolean process(File in, File out) {
        PixM p = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)), maxRes);
        PixM pOut = p.copy();
        for (int j = 0; j < p.getLines(); j++) {
            for (int i = 0; i < p.getColumns(); i++) {
                for (int c = 0; c < 3; c++) {
                    p.setCompNo(c);
                    pOut.setCompNo(c);
                    double filter = filter(p, i, j);
                    pOut.set(i, j, filter);
                }
            }
        }
        try {
            ImageIO.write(pOut.normalize(0, 1).getImage(), "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
