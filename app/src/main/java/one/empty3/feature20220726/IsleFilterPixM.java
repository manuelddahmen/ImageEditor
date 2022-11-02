package one.empty3.feature20220726;

import android.graphics.Color;

public class IsleFilterPixM
        extends FilterPixM {
    private Color selColor;
    private Color oppositeColor;
    private double threshold;
    private float selComps[] = new float[4];
    private float oppComps[] = new float[4];

    public IsleFilterPixM(PixM pix) {
        super(pix);
    }

    public boolean selectPoint(int x, int y) {
        float[] sel = new float[3];
        getColor(x, y, sel);
        double d = 0;
        for (int i = 0; i < 3; i++)
            d += (sel[i] - selComps[i])
                    * (sel[i] - selComps[i]);
        return Math.sqrt(d) < threshold ? true : false;
    }

    public void setCValues(Color background,
                           Color sel, double threshold) {
        this.oppositeColor = background;
        this.selColor = sel;
        this.threshold = threshold;
        selColor.getComponents(this.selComps);
        oppositeColor.getComponents(this.oppComps);

    }

    public double filter(double x, double y) {
        return 0.0;
    }

    public void filter() {
    }
}
