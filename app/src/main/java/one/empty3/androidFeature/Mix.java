package one.empty3.androidFeature;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import matrix.PixM;
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
        double ratio = 1.0 * progress / MAX_PROGRESS;
        boolean catched = false;
        if (ins.length > 1 && ins[0] != null && isImage(ins[0]) && ins[1] != null && isImage(ins[1])) {
            Image read1 = Image.loadFile(ins[0]);
            Image read2 = Image.loadFile(ins[1]);
            PixM pixMin1 = new PixM(read1);
            PixM pixMin2 = null;
            PixM outPixM = null;
            try {
                pixMin2 = new PixM(read2);
                outPixM = new PixM(pixMin1.getColumns(), pixMin1.getLines());
            } catch (RuntimeException ex) {
                Logger.getAnonymousLogger().log(Level.INFO, "Failed to load image #2", ex);
                catched = true;
            }
            if(!catched) {
                for (int i = 0; i < outPixM.getColumns(); i++) {
                    for (int j = 0; j < outPixM.getLines(); j++) {
                        for (int c = 0; c < 3; c++) {
                            pixMin1.setCompNo(c);
                            pixMin2.setCompNo(c);
                            outPixM.setCompNo(c);

                            outPixM.set(i, j, pixMin1.get(i, j) * (1 - ratio) + pixMin2.get(i, j) * (ratio));
                        }
                    }
                }
                outPixM.getImage().saveFile( out);
                return true;
            }
        }
        if((ins.length==1 && isImage(ins[0])) || catched ) {
            Image image = Image.loadFile(ins[0]);
            image.saveFile( out);
            return true;
        }
        return false;
    }
}
