<<<<<<<< HEAD:feature0/Mix.java
package one.empty3.feature;

import java.io.File;

========
package one.empty3.androidFeature;

import java.io.File;

import matrix.PixM;
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Mix.java
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
<<<<<<<< HEAD:feature0/Mix.java
            Image read1 = one.empty3.ImageIO.read(ins[0]);
            Image read2 = one.empty3.ImageIO.read(ins[1]);
            PixM pixMin1 = new PixM(read1);
            PixM pixMin2 = new PixM(read2);
            PixM outPixM = new PixM(pixMin1.getColumns(), pixMin1.getLines());
========
            Image read1 = ImageIO.read(ins[0]);
            Image read2 = ImageIO.read(ins[1]);
            matrix.PixM pixMin1 = new matrix.PixM(read1);
            matrix.PixM pixMin2 = new matrix.PixM(read2);
            matrix.PixM outPixM = new PixM(pixMin1.getColumns(), pixMin1.getLines());
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Mix.java

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
<<<<<<<< HEAD:feature0/Mix.java
            return one.empty3.ImageIO.write(outPixM.getBitmap(), "jpg", out);
========
            ImageIO.write(outPixM.getBitmap().getBitmap(), "jpg", out);
>>>>>>>> origin/newBranch3:app/src/main/java/one/empty3/androidFeature/Mix.java
        }
        return false;
    }
}
