package one.empty3.feature20220726;

import javaAnd.awt.image.BufferedImage;

public class VectorsImage extends M3 {
    public VectorsImage(int columns, int lines, int columnsIn, int linesIn) {
        super(columns, lines, 2, 1);
    }

    public VectorsImage(BufferedImage image1) {
        this(image1.getWidth(), image1.getHeight(), 2, 1);
        PixM pixM = new PixM(image1);
        //M3 gradientDoubleValuesMap =pixM.applyFilter(new GradientFilter(image1));
        // construire les vecteurs
    }


}
