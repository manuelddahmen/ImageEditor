/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*

 Vous êtes libre de :

 */
package one.empty3.library;

import java.awt.*;

public class AntiAliasing {

    public static Image antiAliasing(Image image, int echelle) {
        return image;
    }

    public static Image antiAliasing(ZBuffer z, int echelle, int id) {
        int dimx = z.resX();
        int dimy = z.resY();
        ECBufferedImage img = z.image();
        Image scaled = new ECBufferedImage(dimx / echelle, dimy / echelle,
                ECBufferedImage.TYPE_INT_RGB);
        Graphics g = scaled.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, dimx / echelle, dimy / echelle);

        int[][] colors = new int[dimx * dimy / echelle / echelle][5];

        for (int i = 0; i < dimx; i++) {
            for (int j = 0; j < dimy; j++) {
                colors[(dimx / echelle) * (j / echelle) + (i / echelle)][0]
                        += new Color(img.getRGB(i, j)).getRed();
                colors[(dimx / echelle) * (j / echelle) + (i / echelle)][1]
                        += new Color(img.getRGB(i, j)).getGreen();
                colors[(dimx / echelle) * (j / echelle) + (i / echelle)][2]
                        += new Color(img.getRGB(i, j)).getBlue();
                // colors[(dimx/echelle)*(j/echelle)+(i/echelle)][4] ++;
            }
        }

        g = scaled.getGraphics();

        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < 3; j++) {
                // colors[i][j] /= colors[i][4];
                colors[i][j] /= (echelle * echelle);

            }
            g.setColor(new Color(colors[i][0], colors[i][1], colors[i][2]));
            g.drawLine(i % (dimx / echelle), i / (dimx / echelle), i
                    % (dimx / echelle), i / (dimx / echelle));
        }
        return scaled;
    }
}
