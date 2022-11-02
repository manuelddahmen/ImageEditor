package one.empty3.feature20220726.snakes;

import one.empty3.feature20220726.PixM;
import one.empty3.feature20220726.snakes.DipSnake;
import one.empty3.io.ProcessFile;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.*;

import java.io.File;
import java.io.IOException;

public class SnakeFinderProcess extends ProcessFile {
    public SnakeFinderProcess() {

    }

    @Override
    public boolean process(File in, File out) {
        DipSnake snake = new DipSnake();
        try {
            PixM image = PixM.getPixM(ImageIO.read(in), maxRes);
            for (int i = 0; i < 6; i++) {
                snake.add(i, new Point3D(image.getColumns() / 2 + 0.6 * image.getColumns() / 2 * Math.cos(2 * 3.1416),
                        image.getLines() / 2 - 0.6 * image.getLines() / 2 * Math.sin(2 * 3.1416),
                        0.0));
            }

            snake.energyMinimization(image);

            //image.fillIn(snake, new ColorTexture(Color.WHITE), new ColorTexture(Color.WHITE));

            ImageIO.write(image.normalize(0, 1).getImage(), "jpg", out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
