package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.util.logging.Logger;

public class Transform1 extends ProcessFile {


    static Logger logger;

    static {
        logger

                = Logger.getLogger(Transform1.class.getName());

    }

    public boolean process(File in, File out) {

        if (!in.getName().endsWith(".jpg"))
            return false;
        File file = in;
        PixM pixMOriginal = null;
        try {
            pixMOriginal = PixM.getPixM(ImageIO.read(file), 500.0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
            // assertTrue(false);

        }
        logger.info("file loaded");
        GradientFilter gradientMask = new GradientFilter(pixMOriginal.getColumns(), pixMOriginal.getLines());
        M3 imgForGrad = new M3(pixMOriginal, 2, 2);
        M3 filter = gradientMask.filter(imgForGrad);
        PixM[][] imagesMatrix = filter.getImagesMatrix();//.normalize(0, 1);
        logger.info("gradient computed");

//                    image1 = null;

        // Zero. +++Zero orientation variation.
        Linear linear = new Linear(imagesMatrix[1][0], imagesMatrix[0][0],
                new PixM(pixMOriginal.getColumns(), pixMOriginal.getLines()));
        linear.op2d2d(new char[]{'*'}, new int[][]{{1, 0}}, new int[]{2});
        PixM smoothedGrad = linear.getImages()[2].normalize(0., 1.);
        logger.info("dot outter product");
        // PixM pext = pixMOriginal;
        LocalExtrema le =
                new LocalExtrema(imagesMatrix[1][0].getColumns(),
                        imagesMatrix[1][0].getLines(),
                        3, 1);
        le.setSetMin(false);
        PixM plext3 = le.filter(new M3(smoothedGrad,
                1, 1)
        ).getImagesMatrix()[0][0].normalize(0., 1.);
        logger.info("local maximum");
     
      
      
       /*
      
     pext = pixMOriginal;
     LocalExtrema le2 =
         new  LocalExtrema( imagesMatrix[1][0].getColumns(), 
                    imagesMatrix[1][0].getLines(),
                      5, 0);
     PixM plext2 = le2.filter(new M3(pext,
                      1, 1)
            ).getImagesMatrix()[0][0].normalize(0.,1.);
     logger.info("local maximum 5x5");
      
      /*
      LocalExtrema le3 =
         new  LocalExtrema( imagesMatrix[1][0].getColumns(), 
                      imagesMatrix[1][0].getLines(),
                      19
                           , 3);
      PixM plext3 = le3.filter(new M3(smoothedGrad,
                      1, 1)
            ).getImagesMatrix()[0][0].normalize(0.,1.);
     logger.info("local maximum 20x20");
      
      */


        //    AfterGradientBeforeExtremum a
        //      = new AfterGradientBeforeExtremum(3);

        //      M3 anglesTangente = a.filter(new M3(

        //     new PixM[][]
        //     {{
        //        pext, imagesMatrix[0][0], imagesMatrix[1][0]
        //      }}
        //   ));
//logger.info("angles tangentes");
        //PixM pix = smoothedGrad;
        //  IntuitiveRadialGradient i
        //   = new IntuitiveRadialGradient(pix);
        //  i.setMax(2., 5., 2, 4);
        ///   PixM rad = i.filter(pix);
        //   logger.info("radial orientation");
        /*WriteFile.writeNext("reduite"+file.getName(), pixMOriginal.normalize(0.,1.).getImage());
            WriteFile.writeNext("gradient gx"+file.getName(), imagesMatrix[0][0].normalize(0.,1.).getImage());
      WriteFile.writeNext("gradient gy"+file.getName(), imagesMatrix[1][0].normalize(0.,1.).getImage());
      WriteFile.writeNext("gradient phase x"+file.getName(), imagesMatrix[0][1].normalize(0.,1.).getImage());
      WriteFile.writeNext("gradient phase y"+file.getName(), imagesMatrix[1][1].normalize(0.,1.).getImage());
   WriteFile.writeNext("gradients dot"+file.getName(), smoothedGrad.normalize(0.,1.).getImage());
     WriteFile.writeNext("extrema 3x3"+file.getName(), plext.normalize(0.,1.).getImage());
     WriteFile.writeNext("angles"+file.getName(), anglesTangente.getImagesMatrix()[0][0].normalize(0.,1.).getImage());
     WriteFile.writeNext("radial grad"+file.getName(), rad.normalize(0.,1.).getImage());
     */
        try {
            ImageIO.write(plext3.getImage(), "jpg", out);
        } catch (Exception ex) {
            return false;
        }

        System.gc();

        return true;
    }
}
