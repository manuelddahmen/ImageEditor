package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TransformColor extends ProcessFile {
    @Override
    public boolean process(File in, File out) {

        PixM pix = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)).bitmap, maxRes);

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
    }
}
