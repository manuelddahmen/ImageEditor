package one.empty3.androidFeature;

import java.io.File;

import matrix.matrix.PixM;
import one.empty3.libs.Image;
import one.empty3.ImageIO;
import one.empty3.io.ProcessNFiles;

public class Mix extends ProcessNFiles {
    public static final int MAX_PROGRESS = 256;
    private int progress = MAX_PROGRESS;

    public void setProgressColor(int progress) {
        this.progress = progress;
    }

    public int getProgressColor() {
        return progress;
    }

    @Override
    public boolean processFiles(File out, File... ins) {
        super.processFiles(out, ins);

        double ratio = 1.0 * progress / MAX_PROGRESS;

        if (ins.length > 1 && ins[0] != null && isImage(ins[0]) && ins[1] != null && isImage(ins[1])) {
            Image read1 = ImageIO.read(ins[0]);
            Image read2 = ImageIO.read(ins[1]);
            one.empty3.featureAndroid.matrix.PixM matrix.PixMin1 = new one.empty3.featureAndroid.matrix.PixM(read1);
            one.empty3.featureAndroid.matrix.PixM matrix.PixMin2 = new one.empty3.featureAndroid.matrix.PixM(read2);
            one.empty3.featureAndroid.matrix.PixM outmatrix.PixM = new matrix.PixM(matrix.PixMin1.getColumns(), matrix.PixMin1.getLines());

            for (int i = 0; i < outmatrix.PixM.getColumns(); i++) {
                for (int j = 0; j < outmatrix.PixM.getLines(); j++) {
                    for (int c = 0; c < 3; c++) {
                        matrix.PixMin1.setCompNo(c);
                        matrix.PixMin2.setCompNo(c);
                        outmatrix.PixM.setCompNo(c);

                        outmatrix.PixM.set(i, j, matrix.PixMin1.get(i, j) * (1 - ratio) + matrix.PixMin2.get(i, j) * (ratio));
                    }
                }
            }
            ImageIO.write(outmatrix.PixM.getBitmap().getImage(), "jpg", out);
        }
        return false;
    }
}
