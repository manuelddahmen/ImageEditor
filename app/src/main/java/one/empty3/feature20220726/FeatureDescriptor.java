package one.empty3.feature20220726;

import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;

import java.util.*;
import java.io.File;
/*
public class FeatureDescriptor extends PixM {


    private PixM m;
    private PixM pi;
    private List<Point3D> poi;

    public FeatureDescriptor() {
        super(3, 3);
    }

    public boolean setDescription(double[][] values) {
        for (int i = 0; i < lines; i++)
            for (int j = 0; j < columns; j++)
                set(i, j, values[i][j]);

        return true;
    }

    public void setPixM(PixM m) {
        this.m = m;
        HarrisToPointInterest h = new HarrisToPointInterest(
                m.getColumns(), m.getLines());
        pi = m.applyFilter(h);
        poi = h.getPoi();
    }

    // table line
    // featuredescriptor, image, imagelocation, matchscore*
    // *e min
    public List<FeatureImageLocationMatchScore> matchesAll(FeatureDescriptor[] fd, File[] set) {
        List<FeatureImageLocationMatchScore> l = new ArrayList<>();

        for (File file : set) {
            try {
                PixM pm = new PixM(ImageIO.read(file));
                for (FeatureDescriptor f : fd) {
                    f.setPixM(pm);
                    for (int i = 0; i < poi.size(); i++) {
                        double de = f.deltaEnergy((int) (double) (poi.get(i).getX()), (int) (double) (poi.get(i).getY()));
                        if (de < 0.5) {
                            l.add(new FeatureImageLocationMatchScore());
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return l;

    }


    public double deltaEnergy(int i, int j) {
        return pi.getColorsRegion(i - columns / 2, j - lines / 2, columns, lines, columns, lines).distance(this);
    }


    public double filter(double x, double y) {


        return 0.0;
    }


    public static double[][][] getFeatureSampleDescr(int mSize,
                                                     double max, double[][] anglesIntensitiesRatio) {
        double[][][] m = new double[anglesIntensitiesRatio.length][mSize][mSize];
        return m;
    }
}
*/