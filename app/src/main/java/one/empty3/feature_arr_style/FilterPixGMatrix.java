package one.empty3.feature_arr_style;

import android.graphics.Bitmap;

public abstract class FilterPixGMatrix extends PixM {
    public final static int NORM_NONE = 0;
    public final static int NORM_MEAN = 1;
    public final static int NORM_MAX = 2;
    public final static int NORM_FLOOR_0 = 4;
    public final static int NORM_FLOOR_1 = 8;
    public final static int NORM_CUSTOM = 16;

    public int getNormalize() {
        return normalize;
    }

    public FilterPixGMatrix setNormalize(int normalize) {
        this.normalize = normalize;
        return this;
    }

    private int normalize = NORM_NONE;

    public FilterPixGMatrix(int l, int c) {
        super(l, c);
    }

    public FilterPixGMatrix(Bitmap image) {
        super(image);
    }

    public FilterPixGMatrix(PixM image) {
        super(image.getImage());
    }

    public abstract double filter(double i, double i1);

}
