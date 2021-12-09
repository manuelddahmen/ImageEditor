package one.empty3.feature;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import one.empty3.feature.gui.DirestEffect;
import one.empty3.feature.motion.Motion;
import one.empty3.io.ProcessFile;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;


public class ThreadEffectDisplay extends Thread {
    public ProcessFile effect;
    private BufferedImage image;
    private JPanel jPanel;
    private ClassSchemaBuilder main;
    private BufferedImage imageIn;
    private BufferedImage imageMotion;
    public Webcam webcam;
    private DirestEffect directEffect;
    public Motion motion = null;
    protected boolean busy;
    private RunEffect runEffect;
    private String tempDir;

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    @Override
    public void run() {
        if (webcam != null && webcam.isOpen())
            Webcam.getDefault().close();

        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
        webcam.setImageTransformer(bufferedImage -> {

            BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < bufferedImage.getWidth(); i++)
                for (int j = 0; j < bufferedImage.getHeight(); j++) {
                    bufferedImage1.setRGB(bufferedImage.getWidth() - i - 1, j,
                            bufferedImage.getRGB(i, j));
                }
            return bufferedImage1;
        });

        do {
            image = webcam.getImage();


            try {
                if (image != null)
                    ImageIO.write(image, "jpg", new File(tempDir + "webcam.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*while (!main.processed) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            main.buttonGOActionPerformed(null);

            if (motion != null && imageIn != null) {
                motion.addFrame(imageIn);
                imageIn = motion.process();
            } else
                imageIn = image;
            while (isBusy()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            busy = true;

            Graphics graphics = jPanel.getGraphics();
            if (imageIn != null) {
                graphics.drawImage(imageIn, 0, 0, jPanel.getWidth(), jPanel.getHeight(), null);
            } /*else if (image != null)

                graphics.drawImage(image, 0, 0, jPanel.getWidth(), jPanel.getHeight(), null);
*/
        } while (directEffect.threadEffectDisplay.busy);

        busy = false;

        if (webcam != null && webcam.isOpen())
            Webcam.getDefault().close();


        System.out.printf("End of loop 'webcam draw'");
    }

    private boolean isBusy() {
        return busy;
    }

    public void run2() {
        if (webcam != null && webcam.isOpen())
            Webcam.getDefault().close();

        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
        webcam.setImageTransformer(new WebcamImageTransformer() {
            @Override
            public BufferedImage transform(BufferedImage bufferedImage) {

                BufferedImage bufferedImage1 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                for (int i = 0; i < bufferedImage.getWidth(); i++)
                    for (int j = 0; j < bufferedImage.getHeight(); j++) {
                        bufferedImage1.setRGB(bufferedImage.getWidth() - i - 1, j,
                                bufferedImage.getRGB(i, j));
                    }
                return bufferedImage1;
            }
        });

        busy = true;
        do {
            image = webcam.getImage();

            runEffect.runEffect(motion.frames, main);
        } while (directEffect.threadEffectDisplay.busy);

        busy = false;

        if (webcam != null && webcam.isOpen())
            Webcam.getDefault().close();


        System.out.printf("End of loop 'webcam draw'");
    }


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.imageIn = image;
    }

    public void setJpanel(JPanel panel1) {
        this.jPanel = panel1;
    }

    public void setMain(ClassSchemaBuilder main) {
        this.main = main;
    }

    public void setImageIn(BufferedImage read) {
        imageIn = read;
    }

    public void setImageMotion(BufferedImage process) {
        imageMotion = process;
    }

    public void setDirectEffect(DirestEffect direstEffect) {
        this.directEffect = direstEffect;
    }

    public void setRunEffect(RunEffect runEffect) {
        this.runEffect = runEffect;
    }

    public void setBusy(boolean b) {
        this.busy = b;
    }
}
