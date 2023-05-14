package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GradProcess2 extends ProcessFile {
    @Override
    public boolean process(File in, File out) {
        PixM pixM = PixM.getPixM(Objects.requireNonNull(ImageIO.read(in)).bitmap, maxRes);
        PixM pixMout = new PixM(pixM.getColumns(), pixM.getLines());

        for (int x = 0; x < pixM.getColumns(); x++)
            for (int y = 0; y < pixM.getColumns(); y++)
                for (int c = 0; c < 3; c++) {
                    pixM.setCompNo(c);
                    pixMout.setCompNo(c);
                    pixMout.set(x, y, -
                            pixMout.get(x - 1, y) -
                            pixMout.get(x, y - 1) -
                            pixMout.get(x + 1, y) -
                            pixMout.get(x, y + 1)
                            + 4 * pixMout.get(x, y + 1));
                }

        ImageIO.write(pixMout.normalize(0, 1).getImage(), "jpg", out);
        return true;
    }
}
