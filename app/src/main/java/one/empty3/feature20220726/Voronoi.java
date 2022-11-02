package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.image.BufferedImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Voronoi extends ProcessFile {

    private Point3D near(Point3D point3D, List<Point3D> p) {
        double dist = 1000000;
        Point3D pRes = null;

        int index2 = 0;

        while (index2 <= p.size() - 1) {
            Point3D p3 = p.get(index2);

            if (Point3D.distance(point3D, p3) < dist && p3 != point3D && !p3.equals(point3D)) {
                dist = Point3D.distance(point3D, p3);
                pRes = p3;


            }
            index2++;
        }
        return pRes;
    }

    @Override
    public boolean process(File in, File out) {
        try {
            List<Point3D> points = new ArrayList<>();
            BufferedImage read = ImageIO.read(in);
            PixM pixM = PixM.getPixM(read, maxRes);
            PixM pixMOut = pixM.copy();
            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    if (pixM.luminance(i, j) > 0.4) {
                        points.add(new Point3D((double) i, (double) j, pixM.luminance(i, j)));
                    }
                }
            }


            for (int i = 0; i < pixM.getColumns(); i++) {
                for (int j = 0; j < pixM.getLines(); j++) {
                    Point3D near = near(new Point3D((double) i, (double) j, 0.0), points);
                    if (near != null) {
                        Point3D p = pixM.getP((int) (double) near.get(0), (int) (double) near.get(1));
                        pixMOut.setValues(i, j, p.getX(), p.getY(), p.getZ());
                    } else {
                        //System.out.println("Error near==null");
                    }
                }
            }


            ImageIO.write(pixMOut.getImage(), "jpg", out);

            return true;


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
