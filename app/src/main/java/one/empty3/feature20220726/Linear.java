package one.empty3.feature20220726;

import javaAnd.awt.image.BufferedImage;

import java.util.Arrays;
import java.util.function.Consumer;

public class Linear {
    private int type = 0;
    public static final int TYPE_2D = 0;
    public static final int TYPE_2D_2D = 1;

    private PixM[] images;
    private M3[] imagesM;

    public Linear(PixM... images) {
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
        images = new PixM[bufferedImages.length];
        final int[] i = {0};
        Arrays.stream(bufferedImages).forEach(new Consumer<BufferedImage>() {
            @Override
            public void accept(BufferedImage bufferedImage) {
                images[i[0]] = new PixM(bufferedImage);
                i[0]++;
            }
        });
        this.imagesM = null;
        this.images = images;

    }

    public boolean op2d2d(char[] op, int[][] index, int[] indexRes) {
        PixM[] workingImages = null;
        assert images != null;
        for (int x = 0; x < op.length; x++) {
            workingImages = new PixM[images.length];//.??N
            //new PixM[index[x].length];//.??N
            for (int j = 0; j < index[x].length; j++) {
                workingImages[j] = images[index[x][j]];
            }
            PixM pixM = new PixM(workingImages[0].getColumns(),
                    workingImages[0].getLines());
            for (int m = 0; m < index[x].length; m++) {
                assert workingImages[m] != null;
                for (int comp = 0; comp < workingImages[m].getCompCount(); comp++) {
                    workingImages[m].setCompNo(comp);
                    pixM.setCompNo(comp);
                    for (int i = 0; i < workingImages[m].getColumns(); i++)
                        for (int j = 0; j < workingImages[m].getLines(); j++)
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
