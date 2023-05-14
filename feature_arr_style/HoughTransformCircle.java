package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;

import javaAnd.awt.image.imageio.ImageIO;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class HoughTransformCircle extends ProcessFile {

    private int imgHeight;
    private int imgWidth;
    private final double threshold = 0.3;

    public boolean process(File in, File out) {
        try {

            Bitmap grey = toGrayScale(in);

            HoughTransformOutput.imgWidth = imgWidth = grey.getWidth();
            HoughTransformOutput.imgHeight = imgHeight = grey.getHeight();

            //convert image to array
            double[][] imgArray = new double[imgWidth][imgHeight];
            for (int x = 0; x < imgWidth; x++) {
                for (int y = 0; y < imgHeight; y++) {
                    imgArray[x][y] = (Lumiere.getDoubles(grey.getPixel(x, y))[0]);
                }
            }

            List<CircleHit> hits = circleDetection(imgArray, out);

            //runs circle detection
            hits.sort(Collections.reverseOrder());

            //outputs an image of the original with the circle detected superimposed in red
            HoughTransformOutput.superimposeCircles(hits, grey, new File(out.getAbsolutePath() + "_superImpose.png"));


            File file = new File(out.getAbsolutePath() + ".csv");

            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            hits.forEach(new Consumer<CircleHit>() {
                @Override
                public void accept(CircleHit circleHit) {
                    pw.printf("%d, %d, %d, %d", circleHit.x, circleHit.y, circleHit.r, circleHit.AhitMag);
                }
            });
            pw.close();


            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private double[][] blur(double[][] imgArray) {
        double sigma = 1;    //must be >0
        int width = 15;      //must be odd

        int pad = (int) Math.floor(width / 2.);
        double[][] base = new double[width][width];

        //build gaussian kernel
        double scaling = 1 / (2 * Math.PI * Math.pow(sigma, 2));

        for (int x = -pad; x <= pad; x++) {
            for (int y = -pad; y <= pad; y++) {
                double components = (Math.pow(x, 2) + Math.pow(y, 2));
                double exp = -(components / (2 * Math.pow(sigma, 2)));
                base[x + pad][y + pad] = scaling * Math.exp(exp);
            }
        }

        return KernelSweep(imgArray, base);
    }

    //converts given file into a grayscale image
    private Bitmap toGrayScale(File f) throws Exception {
        Bitmap img = Objects.requireNonNull(ImageIO.read(f)).bitmap;
        Bitmap grey = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
        //grey.getGraphics().drawImage(img, 0, 0, null);

        return grey;
    }

    private double[][] KernelSweep(double[][] base, double[][] kernel) {
        int paddingX = (int) Math.floor((double) kernel.length / 2);
        int paddingY = (int) Math.floor((double) kernel[0].length / 2);

        double[][] result = new double[imgWidth][imgHeight];
        double[][] padded = new double[imgWidth + paddingX][imgHeight + paddingY];

        //pad base into padded
        for (int x = paddingX; x < imgWidth; x++) {
            if (imgHeight - paddingY >= 0)
                System.arraycopy(base[x - paddingX], 0, padded[x], paddingY, imgHeight - paddingY);
        }

        //sweep over every value in padded
        for (int x = paddingX; x < imgWidth; x++) {
            for (int y = paddingY; y < imgHeight; y++) {

                //sweep kernel through point
                for (int i = -paddingX; i <= paddingX; i++) {
                    for (int j = -paddingY; j <= paddingY; j++) {
                        result[x - paddingX][y - paddingY] += padded[x + i][y + j] * kernel[j + paddingY][i + paddingX];
                    }
                }
            }
        }

        return result;
    }

    private List<CircleHit> circleDetection(double[][] imgPixels, File out) throws Exception {
        //sets the max radius of a circle, default 0 == min of height or width (biggest circle able to be displayed)
        int radius = Integer.min(imgHeight, imgWidth);

        //sets a 3D space array of ints to hold 'hits' in x, y, and r planes
        int[][][] A = new int[imgWidth][imgHeight][radius];
        int maxCol = 0;

        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                //if the given pixel is above the threshold, a circle will be drawn at radius rad around it and if it
                //is a valid coordinate it will be accumulated in the A array and plotted in the pointSpace image
                if (imgPixels[x][y] > threshold) {
                    for (int rad = 1; rad < radius; rad++) {
                        for (int t = 0; t <= 360; t++) {
                            int a = (int) Math.floor(x - rad * Math.cos(t * Math.PI / 180));
                            int b = (int) Math.floor(y - rad * Math.sin(t * Math.PI / 180));

                            //if a or b is outside the bounds of the image ignore
                            if (!((0 > a || a > imgWidth - 1) || (0 > b || b > imgHeight - 1))) {
                                A[a][b][rad] += 1;
                                if (A[a][b][rad] > maxCol) {
                                    maxCol = A[a][b][rad];
                                }
                            }

                        }
                    }
                }
            }
        }
        int[][] houghSpace = new int[imgWidth][imgHeight];
        List<CircleHit> AList = new ArrayList<>();
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                int minRad = 10;
                for (int rad = minRad; rad < radius; rad++) {
                    AList.add(new CircleHit(x, y, rad, A[x][y][rad]));

                    houghSpace[x][y] = (int) Math.floor(HoughTransformOutput.map(A[x][y][rad], 0, maxCol, 0, 1));
                }
            }
        }

        HoughTransformOutput.writeImage(houghSpace, out);

        return AList;
    }
}

class CircleHit implements Comparable<CircleHit> {

    short x;
    short y;
    short r;
    short AhitMag;

    public CircleHit(int x, int y, int r) {
        this.x = (short) x;
        this.y = (short) y;
        this.r = (short) r;
        this.AhitMag = 1;
    }

    public CircleHit(int x, int y, int r, int A) {
        this.x = (short) x;
        this.y = (short) y;
        this.r = (short) r;
        this.AhitMag = (short) A;
    }

    public Short getAhitMag() {
        return AhitMag;
    }

    @Override
    public int compareTo(CircleHit o) {
        return Integer.compare(getAhitMag(), o.getAhitMag());
    }
}