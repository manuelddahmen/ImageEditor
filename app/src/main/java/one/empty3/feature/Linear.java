package one.empty3.feature;


import java.util.Arrays;
import java.util.function.Consumer;

import one.empty3.feature.M3;
import one.empty3.feature.PixM;
import one.empty3.feature.app.replace.java.awt.image.BufferedImage;

public class Linear {
    private int type = 0;
    public static final int TYPE_2D = 0;
    public static final int TYPE_2D_2D = 1;

    private one.empty3.feature.PixM[] images;
    private one.empty3.feature.M3[] imagesM;

    public Linear(one.empty3.feature.PixM... images) {
        type = TYPE_2D_2D;
        this.imagesM = null;
        this.images = images;
    }

    public Linear(M3... imagesM) {
        type = TYPE_2D;
        this.images = null;
        this.imagesM = imagesM;
    }

    public Linear(BufferedImage... bufferedImages) {
        type = TYPE_2D_2D;
        this.imagesM = null;
        images = new one.empty3.feature.PixM[bufferedImages.length];
        final int[] i = {0};
        Arrays.stream(bufferedImages).forEach(new Consumer<BufferedImage>() {
            @Override
            public void accept(BufferedImage bufferedImage) {
                images[i[0]] = new one.empty3.feature.PixM(bufferedImage);
                i[0]++;
            }
        });
        this.imagesM = null;
        this.images = images;

    }

    public boolean op2d2d(char[] op, int[][] index, int[] indexRes) {
        one.empty3.feature.PixM[] workingImages = null;
        assert images != null;
        for (int x = 0; x < op.length; x++) {
            workingImages = new one.empty3.feature.PixM[images.length];//.??N
            //new PixM[index[x].length];//.??N
            for (int j = 0; j < index[x].length; j++) {
                workingImages[j] = images[index[x][j]];
            }
            one.empty3.feature.PixM pixM = new one.empty3.feature.PixM(workingImages[0].columns,
                    workingImages[0].lines);
            for (int m = 0; m < index[x].length; m++) {
                assert workingImages[m] != null;
                for (int comp = 0; comp < workingImages[m].getCompCount(); comp++) {
                    workingImages[m].setCompNo(comp);
                    pixM.setCompNo(comp);
                    for (int i = 0; i < workingImages[m].columns; i++)
                        for (int j = 0; j < workingImages[m].lines; j++)
                            switch (op[x]) {
                                case '+':
                                    if (m == 0)
                                        pixM.set(i, j, workingImages[0].get(i, j));
                                    pixM.set(i, j, pixM.get(i, j) + workingImages[m].get(i, j));
                                    break;
                                case '-':
                                    if (m == 0)
                                        pixM.set(i, j, workingImages[0].get(i, j));
                                    else
                                        pixM.set(i, j, pixM.get(i, j) - workingImages[m].get(i, j));
                                    break;
                                case '*':
                                    if (m == 0)
                                        pixM.set(i, j, workingImages[0].get(i, j));
                                    pixM.set(i, j, pixM.get(i, j) * workingImages[m].get(i, j));
                                    break;
                                case '/'://divide M1/M2/M3
                                    if (m == 0)
                                        pixM.set(i, j, workingImages[0].get(i, j));
                                    pixM.set(i, j, pixM.get(i, j) / workingImages[m].get(i, j));
                                    break;
                                case '~': //average
                                    if (m == 0)
                                        pixM.set(i, j, workingImages[0].get(i, j));
                                    pixM.set(i, j, pixM.get(i, j) + workingImages[m].get(i, j) / workingImages.length);
                                    break;
                                case '%':
                                    if (m == 0)
                                        pixM.set(i, j, workingImages[0].get(i, j));
                                    pixM.set(i, j, Math.IEEEremainder(pixM.get(i, j), workingImages[m].get(i, j)));
                                    break;
                                case '|':
                                    // Norme
                                    break;
                            }
                }
            }
            pixM.normalize(0.0, 1.0);
            workingImages[indexRes[x]] = pixM;
        }
        this.images = workingImages == null ? images : workingImages;
        return true;
    }

    public int getType() {
        return type;
    }

    public PixM[] getImages() {
        return images;
    }
}
