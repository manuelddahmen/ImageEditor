package one.empty3.feature_arr_style;

import one.empty3.library.Point3D;
import one.empty3.library.RepresentableConteneur;

import java.util.Arrays;
import java.util.function.Consumer;

public abstract class AreaDescriptor {
    RepresentableConteneur area = new RepresentableConteneur();

    public AreaDescriptor(int x, int y, int sizeX, int sizeY) {

    }

    public void setRegion(Point3D... p) {
        final int[] i = new int[1];
        Arrays.stream(p).sequential().forEach(new Consumer<Point3D>() {
            @Override
            public void accept(Point3D point3D) {
                area.add(point3D);
                i[0] = i[0] + 1;
                //this.c = p[i].texture().getColorAt(0.5, 0.5);
                //this.p = p;
            }
        });
    }

    public abstract FilterPixGMatrix getFilter();

    public abstract double match();
}
