//package one.empty3.feature_arr_style;
//
//import one.empty3.feature_arr_style.jviolajones.Detector;
//import one.empty3.io.ProcessFile;
//
//import java.io.*;
//import java.util.List;
//
//public class FaceDetect extends ProcessFile {
//    public boolean process(File in, File out) {
//
//        File img = in;
//        InputStream haarXml = Detector.class.getResourceAsStream("/haarcascade_frontalface_alt2.xml");
//
//        Detector detector = new Detector(haarXml);
//        List<Rectangle> res = null;
//        try {
//            PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(out.getAbsolutePath() + "-faces.csv")));
//            res = detector.getFaces(img.getAbsolutePath(), 1, 1.25f, 0.1f, 1, true);
//            if (res.size() == 0) {
//                System.out.printf("No face\n");
//            } else {
//                for (Rectangle re : res) {
//                    System.out.printf("Face %d\n%d\n%d\n%d\n",
//                            re.x, re.y, re.width, re.height);
//                    printWriter.println(String.format("%s, %d, %d, %d, %d", out.getName(), re.x, re.y, re.width, re.height));
//                }
//            }
//            printWriter.close();
//            return res.size() > 0;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//}
