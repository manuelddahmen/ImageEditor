package one.empty3.feature20220726;
/*
import one.empty3.feature20220726.jviolajones.Detector;
import one.empty3.io.ProcessFile;

import javaAnd.awt.*;
import java.io.*;
import java.util.List;
/*
public class FaceDetect extends ProcessFile {
    public boolean process(File in, File out) {

        File img = in;
        InputStream haarXml = Detector.class.getResourceAsStream("/haarcascade_frontalface_alt2.xml");

        Detector detector = new Detector(haarXml);
        List<Rectangle> res = null;
        try {
            File file = new File(getOutputDirectory()
                    .getAbsolutePath() + File.separator + "faces.csv");
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
            res = detector.getFaces(img.getAbsolutePath(), 1, 1.25f, 0.1f, 1, true);
            if (res.size() == 0) {
                System.out.printf("No face\n");
            } else {
                for (Rectangle re : res) {
                    System.out.printf("Face %d\n%d\n%d\n%d\n",
                            re.x, re.y, re.width, re.height);
                    printWriter.println(String.format("%s, %d, %d, %d, %d", out.getName(), re.x, re.y, re.width, re.height));
                }
            }
            printWriter.close();

            addSource(file);

            return res.size() > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
*/