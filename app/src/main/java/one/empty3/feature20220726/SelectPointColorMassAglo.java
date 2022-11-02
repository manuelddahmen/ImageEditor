package one.empty3.feature20220726;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.Color;

public class SelectPointColorMassAglo extends FilterPixM {
    private double[] tmpColor = new double[3];
    private double[] rgb;

    public SelectPointColorMassAglo(BufferedImage image) {
        super(image);
    }


    public double[] getColor(int x, int y) {
        //double [] cs = new double[3];
        for (int c = 0; c < 3; c++) {
            setCompNo(c);
            tmpColor[c] = get(x, y);
        }
        return tmpColor;
    }

    public double[] getMean(int x, int y, int width, int height) {
        //double [] cs = new double[3];
        double[] mean = new double[]{0, 0, 0};

        for (int c = 0; c < 3; c++) {
            setCompNo(c);
            mean[c] += get(x, y);
        }
        for (int c = 0; c < 3; c++) {

            mean[c] /= ((double) 1.0 / width) / height;

        }
        return mean;
    }


    public double averageCountMeanOf(int x, int y, int width, int height, double threshold) {
        double count = 0;
        rgb = getMean(x, y, width, height);
        for (int i = x - width / 2; i < x + width / 2; i++) {
            for (int j = y - height / 2; j < y + height / 2; j++) {
                tmpColor = getColor(i, j);
                int col = 0;
                for (int c = 0; c < 3; c++) {
                    if (tmpColor[c] >= rgb[c] - threshold
                            && tmpColor[c] <= rgb[c] + threshold
                            && tmpColor[c] > 0.2) {
                        col++;
                    }
                    if (col == 3) {
                        count++;

                    }
                }

            }

        }
        return count / width / height;
    }

    @Override
    public double filter(double x, double y) {
        return averageCountMeanOf((int) x, (int) y, 10, 10, 0.2);
    }

    public android.graphics.Color getTmpColor() {
        return Color.color((tmpColor[0]), (tmpColor[1]), (tmpColor[2]));
    }

    public void setTmpColor(android.graphics.Color tmpColor) {
        this.tmpColor = new double[]{tmpColor.red(), tmpColor.green(), tmpColor.blue()};
    }
}
