package one.empty3.feature20220726;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point3D;

public class ClassificationAvgColors extends ProcessFile {
    public ClassificationAvgColors() {

    }

    @Override
    public boolean process(File in, File out) {
        KMeans classification = new KMeans();
        classification.process(in, out);
        // Processed by "classification
        // Non filtered image
        BufferedImage original = ImageIO.read(in);
        PixM pixMoriginal = new PixM(original);
        PixM toProcess = new PixM(original);
        Map<Integer, double[]> c = classification.k_clusterer.centroids;
        Map<Integer, Point3D> sum = new HashMap<>();
        Map<Integer, Integer> count = new HashMap<>();
        // Faire les moyennes des points de même groupe
        c.forEach(new BiConsumer<Integer, double[]>() {
            @Override
            public void accept(Integer integer, double[] doubles) {
                sum.putIfAbsent(integer, Point3D.O0);
                count.putIfAbsent(integer, 0);
                sum.put(integer, sum.get(integer).plus(pixMoriginal.getP((int) doubles[0], (int) doubles[1])));
                count.put(integer, count.get(integer) + 1);
            }
        });
        Point3D[] ps = new Point3D[c.keySet().size()];
        c.forEach(new BiConsumer<Integer, double[]>() {
            @Override
            public void accept(Integer integer, double[] doubles) {
                Integer countI = count.get(integer);
                Point3D sumI = sum.get(integer);
                sumI = sumI.mult(1. / countI);
                ps[integer] = sumI;
            }
        });
        c.forEach(new BiConsumer<Integer, double[]>() {
            @Override
            public void accept(Integer integer, double[] doubles) {
                Integer countI = count.get(integer);
                Point3D sumI = sum.get(integer);
                sumI = sumI.mult(1. / countI);
                for (int c = 0; c < 3; c++)
                    doubles[c + 2] = ps[integer].get(c);

                toProcess.setP((int) (doubles[0]), (int) (doubles[1]),
                        sumI);
            }
        });

        return super.process(in, out);
    }
}
