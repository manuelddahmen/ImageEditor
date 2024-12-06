/*
package one.empty3.feature20220726;


import one.empty3.ImageIO;
import one.empty3.libs.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class Main2 {
    private File directory;

    */
/*
    public static void makeGoodOutput(File original, File folderOutput) {
        try {
            Path source = FileSystems.getDefault().getPath(original.getAbsolutePath());
            Path newDir = FileSystems.getDefault().getPath(folderOutput.getAbsolutePath());
            Files.copy(source, newDir.resolve(source.getFileName()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
*//*

    public static File work(File dir, one.empty3.libs.Color imageToWrite, String outputFilename) throws IOException {
        File dir1 = new File(dir.getAbsolutePath() + "/" + outputFilename.substring(0,
                outputFilename.lastIndexOf("/")));
        File file = new File(dir.getAbsolutePath() + "/" + outputFilename);
        if (dir1.mkdirs())
            System.out.println(dir.getAbsolutePath() + " created");
        System.out.print("\n(width, height) = " + imageToWrite.getWidth() +
                ", " + imageToWrite.getHeight() + ")");

        if (!one.empty3.ImageIO.write(imageToWrite, "jpg", file)) {
            System.out.println("Error inappropriate writer or not found " + "JEG");
            System.exit(-2);
        } else {
            System.out.println("Done writing : " + outputFilename);

        }
        return file;
    }

    public static void main(String[] args) {
        new Main2().exec();
    }

    public void exec() {

        Arrays.stream(one.empty3.ImageIO.getWriterFormatNames()).forEach(s1 ->
                System.out.println("Format name : \"" + s1 + "\""));
        directory = new File("outputFiles/_" + "__" +

                Time.from(Instant.now()).toString().replace(' ', '_').replace('|', '_')
                        .replace('\\', '_').replace('/', '_').replace(':', '_')
                + "/");
        for (String s : Objects.requireNonNull(new File("resources").list())) {
            String s0 = s.substring(s.lastIndexOf(".") + 1);
            String ext = s0.equals("jpg") || s0.equals("jpg") ? "jpg" : s0;
            if (Arrays.asList(one.empty3.ImageIO.getWriterFormatNames()).contains(ext)) {
                try {

                    if (directory.mkdirs())
                        System.out.println("Directory created" + directory.getAbsolutePath());
                    System.out.println("format name image " + ext + " found");

                    Image image = one.empty3.ImageIO.read(new File("resources/" + s));

                    GradientFilter gradientMask = new GradientFilter(image.getWidth(), image.getHeight());
                    matrix.PixM matrix.PixMOriginal = matrix.PixM.getmatrix.PixM(image, 300);
                    M3 imgFprGrad = new M3(image, 300, 300, 2, 2);
                    M3 filter = gradientMask.filter(imgFprGrad);
                    matrix.PixM[][] imagesMatrix = filter.getImagesMatrix();//.normalize(0, 1);


//                    image = null;

                    // Zero. +++Zero orientation variation.
                    Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                            new matrix.PixM(matrix.PixMOriginal.getColumns(), matrix.PixMOriginal.getLines()));
                    linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
                    matrix.PixM smoothedGrad = linear.getImages()[2]; //.applyFilter(new GaussFilterPixGMatrix(4, sigma));
                    int itereAngleGrad = 12;
                    M3 filter3 = new AfterGradientBeforeExtremum(itereAngleGrad).filter(new M3(smoothedGrad, 1, 1));

                    work(directory, matrix.PixMOriginal.getImage().getBitmap(), s + "/original.jpg");

                    for (double angle = 0.8;
                         angle < 2 * Math.PI; angle += 2 * Math.PI / itereAngleGrad) {
                        stream(filter3, angle, s);
                        System.gc();
                    }

                    for (double sigma = 0.8; sigma < 2.0; sigma += 0.2) {
                        matrix.PixM matrix.PixM = smoothedGrad.applyFilter(new GaussFilterPixGMatrix(smoothedGrad, 4, sigma));


                        for (int size = 1; size < 16; size *= 2) {
                            //
                            M3 smoothedGradM3 = new M3(matrix.PixM.subSampling(size), 1, 1);
                            // Search local maximum
                            LocalExtrema localExtrema = new LocalExtrema(smoothedGradM3.getColumns(), smoothedGradM3.getLines(), 3, 2);
                            matrix.PixM[][] filter2 = localExtrema.filter(smoothedGradM3).normalize(0.0, 1.0);
                            matrix.PixM filter1 = filter2[0][0];
                            Image image1 = filter1.getImage().getBitmap();
                            System.out.println("Original read image");
                            work(directory, imagesMatrix[0][0].getImage().getBitmap(), s + "/1/sigma" + sigma + "/size" + size + "gradient.jpg");
                            System.out.println("oriented grad extremum search (max==1.0) ");
                            work(directory, filter1.getImage().getBitmap(), s + "/2/smoothed_grad-" + sigma + "/size" + size + ".jpg");
                            System.out.println("oriented grad extremum search (max==1.0) ");
                            work(directory, image1, s + "/3/extremum_search" + sigma + "/size" + size + ".jpg");

                            System.gc();
                        }
                    }

                    System.out.println("Thread terminated without exception");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void stream(M3 smoothedGradM3, double angle, String s) {
        //int[] i = {0};
        Arrays.stream(smoothedGradM3.getImagesMatrix()).forEach(matrix.PixMS -> Arrays.stream(matrix.PixMS).forEach(matrix.PixM1 -> {
                    LocalExtrema localExtrema1 = new LocalExtrema(smoothedGradM3.getColumns(), smoothedGradM3.getLines(), 3, 0);
                    M3 extremaOrientedGrad = localExtrema1.filter(new M3(matrix.PixM1, 1, 1));
                    try {
                        System.out.println("Gradient (gx,gy).(nx,ny)");
                        work(directory, matrix.PixM1.getImage().getBitmap(), s + "/4/OrientedGradExtremum_1_" + angle + ".jpg");
                        System.gc();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("oriented grad extremum search (max==1.0) ");
                    Arrays.stream(extremaOrientedGrad.getImagesMatrix()).forEach(matrix.PixMS1 -> Arrays.stream(matrix.PixMS1).forEach(matrix.PixM -> {
                        try {
                            String sub = s + "/4/OrientedGradExtremum_2_" +
                                    +angle + ".jpg";
                            File image = work(directory, matrix.PixM.getImage().getBitmap(), sub);
                            Image image1 = matrix.PixM.getImage().getBitmap();
                            Histogram.testCircleSelect(image1, new File("resources"), 10, 0.3, matrix.PixM.getColumns() / 10.0);
                            //i[0]++;
                            System.gc();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }));

                })
        );
    }

}
*/
