package one.empty3.feature20220726.violajonesclassifier;

import one.empty3.feature20220726.GaussFilterPixM;
import one.empty3.feature20220726.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point2D;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.*;
import javaAnd.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartMatch extends ProcessFile {
    List<PixM> featuresDescriptors = new ArrayList<>();
    /// Partitioning searches for features.
    double featureMaxSize;//%original
    double featureMinSize;//PX
    double incrXY;// size matrices
    double nOrient;// #angles

    public PartMatch() {
        int N = 128;
        for (int n = 4; n <= N; n *= 2) {
            for (double a = 0; a < 1.; a += 1 / 16.) {

                PixM pixM = new PixM(n, n);

                double lineAx = (Math.cos(Math.PI * 2 * a) + 0.5) * n;
                double lineAy = (Math.sin(Math.PI * 2 * a) + 0.5) * n;
                double lineBx = n / 2. - lineAx / 2.;
                double lineBy = n / 2. - lineAy / 2.;

                Point2D pa = new Point2D(lineAx, lineAy);
                Point2D pb = new Point2D(lineBx, lineBy);

                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) {
                        Point2D p = new Point2D(i, j);
                        double sign = Math.signum(prod2(pb.moins(pa), pb.moins(p)));
                        pixM.setValues(i, j, sign, sign, sign);
                    }
                try {
                    ImageIO.write(pixM.normalize(0, 1).getImage(), "jpg", new File("features/featureDesc_"
                            + n + "_angle_" + a + ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                featuresDescriptors.add(pixM);
            }
        }
    }

    public double prodScalaire(Point2D v1, Point2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public Point2D prodVect(Point2D v1, Point2D v2) {
        return new Point2D(v2.getY() - v1.getX(), v1.getY() - v2.getX());

    }

    public double prod2(Point2D v1, Point2D v2) {
        return v1.getX() * v2.getX()
                - v1.getY() * v2.getY();

    }

    public double computeScore(PixM image, int x, int y, int n, PixM match) {
        double score = 0.0;
        for (int i = x; i < x + n; i++) {
            for (int j = y; j < y + n; j++) {
                score = image.luminance(i, j) * match.luminance(i, j);
            }
        }
        return Math.abs(score / n / n);
    }

    public double intensity(PixM image, int x, int y, int n) {
        double score = 0.0;
        for (int i = x; i < x + n; i++) {
            for (int j = y; j < y + n; j++) {
                score = image.luminance(i, j) * 1;
            }
        }
        return score / n / n;
    }

    @Override
    public boolean process(File in, File out) {
        featuresDescriptors = new ArrayList<>();

        try {
            PixM pix = PixM.getPixM(ImageIO.read(in), maxRes);

            BufferedImage outImg = pix.getImage();

            int[][] largeurs = new int[pix.getColumns()][pix.getLines()];
            double lastMatchScore = 0;
            for (int n = 4; n < 128; n *= 2) {
                double matchScoreMax;
                for (int i = 0; i < pix.getColumns(); i++) {
                    for (int j = 0; j < pix.getLines(); j++) {
                        double m;
                        double matchScoreMin = 0.6;
                        int index = 0;
                        for (index = 0; index < featuresDescriptors.size(); index++) {
                            double intensity = intensity(pix, i, j, n);
                            double intensityFD = intensity(featuresDescriptors.get(index), i, j, n);
                            double matchScore = computeScore(pix, i, j, n, featuresDescriptors.get(index));
                            if (matchScore >= Math.abs(intensity - intensityFD)
                                    && matchScore >= matchScoreMin) {

                                outImg.drawRect(i, j, n, n, null);

                                classify(matchScore, featuresDescriptors.get(index));
                                lastMatchScore = matchScore;
                            }
                        }
                    }
                }
            }

            ImageIO.write(outImg, "jpg", out);

            return true;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }

    private void classify(double m, PixM pixM) {

    }

    private double matchScore(PixM pix, int i, int j, int n, PixM pixM) {
        return 0;
    }

    public static void main(String[] args) {
        PartMatch partMatch = new PartMatch();

    }
}