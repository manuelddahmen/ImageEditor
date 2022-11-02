package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class TransformColor extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        try {

            PixM pix = PixM.getPixM(ImageIO.read(in), maxRes);

            for (int i = 0; i < pix.getColumns(); i++) {
                for (int j = 0; j < pix.getLines(); j++) {
                    for (int c = 0; c < pix.getCompNo(); c++) {
                        pix.setCompNo(c);
                        pix.set(i, j, 1 - pix.get(i, j));
                    }
                }
            }

            ImageIO.write(pix.normalize(0.0, 1.0).getImage(), "jpg", out);

            return true;
        } catch (IOException e) {

            e.printStackTrace();

            return false;
        }
    }
}
