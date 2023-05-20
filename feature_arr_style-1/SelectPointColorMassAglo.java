package one.empty3.feature_arr_style;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.Lumiere;

public class SelectPointColorMassAglo extends FilterPixGMatrix {
    private double[] tmpColor = new double[3];
    private double[] rgb;

    public SelectPointColorMassAglo(Bitmap image) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Color getTmpColor() {
        return Color.valueOf((float) (tmpColor[0]), (float) (tmpColor[1]), (float) (tmpColor[2]));
    }

    public void setTmpColor(int tmpColor) {
        this.tmpColor = Lumiere.getDoubles(tmpColor);
    }
}
