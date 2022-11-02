package one.empty3.feature20220726.lbp;

import one.empty3.feature20220726.PixM;
import one.empty3.library.Point3D;

import javaAnd.awt.*;

public class OriginalLBP {
    private final PixM pixM;
    private final float radius;

    public OriginalLBP(PixM pixM, float radius) {
        this.radius = radius;
        this.pixM = pixM;
    }

    public void computeCircle() {
        float[] values = new float[1 + (int) (2 * Math.PI * radius)];
        PixM m = new PixM(pixM.getColumns(), pixM.getLines());
        for (int i = 0; i < pixM.getColumns(); i++)
            for (int j = 0; j < pixM.getLines(); j++) {
                values[0] = (float) pixM.luminance(i, j);
                for (float angle = 0; angle < 2 * Math.PI * radius; angle++) {
                    Point3D p = new Point3D((double) i, (double) j, 0.0).plus(
                            new Point3D(radius * Math.cos(angle), -radius * Math.sin(angle), 0.0));
                    Point point = new Point((int) (double) p.getX(), (int) (double) p.getY());
                    double v = pixM.luminance((int) point.getX(), (int) point.getY());
                    values[(int) angle + 1] = (float) v;
                }
                long binary = 0;
                for (int k = 1; i < values.length; i++) {
                    if (values[k] > values[0]) {
                        binary += Math.pow(2, i);
                    } else if (values[k] < values[0]) {

                    }
                }
                m.set(i, j, binary);
            }
        m.compCount = 1;
    }
}
