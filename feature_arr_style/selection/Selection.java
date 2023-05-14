package one.empty3.feature_arr_style.selection;

import one.empty3.feature_arr_style.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * Trouver les primitives.
 */
public abstract class Selection extends ProcessFile {
    private List<Point3D> selectedPoints = new ArrayList<>();
    private PixM image;
    public static final int SELECTION_TYPE_COLOR = 1;
    public static final int SELECTION_TYPE_COLOR_FROM_POINT = 2;
    public static final int SELECTION_TYPE_COLOR_FROM_RECT = 4;
    public static final int SELECTION_OPTION_PRESELECTION = 8;

    public List<Point3D> select(List<Point3D> preSelection, PixM pix,
                                int rgb, double threshold) {
        List<Point3D> list = new ArrayList<>();
        double[] doubles = Lumiere.getDoubles(rgb);
        for (int i = 0; i < pix.getColumns(); i++) {
            for (int j = 0; j < pix.getLines(); j++) {
                for (int c = 0; c < pix.getCompNo(); c++) {
                    if (pix.get(i, j) <= doubles[c] - threshold && pix.get(i, j) >= doubles[c] + threshold) {
                        list.add(Point3D.n(i, j, 0));
                    }
                }
            }
        }
        return list;
    }

    public List<Point3D> selectColorPoint(List<Point3D> preSelection, PixM pix,
                                          int rgb, int x, int y, double threshold) {
        List<Point3D> list = new ArrayList<>();
        double[] doubles = Lumiere.getDoubles(rgb);
        for (int i = 0; i < pix.getColumns(); i++) {
            for (int j = 0; j < pix.getLines(); j++) {
                for (int c = 0; c < pix.getCompNo(); c++) {
                    if (pix.get(i, j) <= doubles[c] - threshold && pix.get(i, j) >= doubles[c] + threshold) {
                        list.add(Point3D.n(i, j, 0));
                    }
                }
            }
        }
        return list;
    }


    public abstract List<Point3D> selectPoint(List<Point3D> preSelection, PixM pix,
                                              int x, int y);

    public abstract List<Point3D> selectInRect(List<Point3D> preSelection, PixM pix,
                                               int x1, int y1, int x2, int y2);

    @Override
    public boolean process(File in, File out) {
        return super.process(in, out);
    }
}
