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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

//import org.monte.media.avi.AVIReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/*__
 * @author manu
 */
public class ImageTexture extends ITexture {

    private StructureMatrix<ECBufferedImage> ecBufferedImageStructureMatrix = new StructureMatrix<>(0, ECBufferedImage.class);
    private ECBufferedImage image;

    private String nom = "texture";

    private String nomFichier = "ecBufferedImageStructureMatrix.png";

    private Scene scene;
   // private AVIReader reader;
    private int track = 0;
    private File avifile = null;
    private int transparent = 0xFFFFFF00;

    public ImageTexture(ECBufferedImage bi) {
        this.ecBufferedImageStructureMatrix.setElem(bi);
        image = bi;
    }

    @Override
    public void iterate() throws EOFVideoException {
        
    }

    @Override
    public int getColorAt(double x, double y) {
        Point2D trans = getCoord(x, y);
        return couleur(trans.x / ecBufferedImageStructureMatrix.getElem().getWidth(), trans.y / ecBufferedImageStructureMatrix.getElem().getHeight()).getRGB();
    }

    protected Color couleur(double rx, double ry) {
        int x = (int) (rx * ecBufferedImageStructureMatrix.getElem().getWidth());
        int y = (int) (ry * ecBufferedImageStructureMatrix.getElem().getHeight());
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x >= ecBufferedImageStructureMatrix.getElem().getWidth()) {
            x = ecBufferedImageStructureMatrix.getElem().getWidth() - 1;
        }
        if (y >= ecBufferedImageStructureMatrix.getElem().getHeight()) {
            y = ecBufferedImageStructureMatrix.getElem().getHeight() - 1;
        }


        int c = ecBufferedImageStructureMatrix != null ? ecBufferedImageStructureMatrix
                .getElem().getRGB(x, y)
                :
                transparent;
        if (

                c

                        ==

                        transparent

                )
            return new Color(transparent);
        else
            return new Color(c);
    }


    public BufferedImage getEcBufferedImageStructureMatrix() {
        return ecBufferedImageStructureMatrix.getElem();
    }

    public void setEcBufferedImageStructureMatrix(ECBufferedImage ecBufferedImageStructureMatrix) {
        this.ecBufferedImageStructureMatrix.setElem(ecBufferedImageStructureMatrix);
        image = ecBufferedImageStructureMatrix;
    }

    public ECBufferedImage getImage() {
        return image;
    }

    public void setImage(ECBufferedImage bi) {
        image = bi;
        ecBufferedImageStructureMatrix.setElem(bi);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    void scene(Scene scene) {
        this.scene = scene;
    }

    public void setTransparent(Color tr) {
        this.transparent = tr.getRGB();
    }

    public void timeNext() {
    }

    public void timeNext(long milli) {
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return ecBufferedImageStructureMatrix;
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }


    /*__
     * Quadrilatère numQuadX = 1, numQuadY = 1, coordArr, y 3----2 ^2y |\ | | 4 |
     * 0--\-1 1 -> 2x
     *
     * @param numQuadX nombre de maillage en coordArr
     * @param numQuadY nombre de maillage en y
     * @param x        valeur de coordArr
     * @param y        valeur de y
     * @return
     */
/*    public Color getMaillageTexturedColor(int numQuadX, int numQuadY, double x,
                                          double y) {
        Point2D p = getCoord(x, y);

        int xi = ((int) (1d * ecBufferedImageStructureMatrix.getWidth() * p.x));
        int yi = ((int) (1d * ecBufferedImageStructureMatrix.getHeight() * p.y));


        if (xi >= ecBufferedImageStructureMatrix.getWidth()) {
            xi = ecBufferedImageStructureMatrix.getWidth() - 1;
        }
        if (yi >= ecBufferedImageStructureMatrix.getHeight()) {
            yi = ecBufferedImageStructureMatrix.getHeight() - 1;
        }
        Color c = new Color(ecBufferedImageStructureMatrix.getRGB(xi, yi));
        if (c.equals(transparent)) {
            return new Color(transparent);
        } else {
            return c;
        }
    }
*/
    /*__
     * +|--r11->/-----| y^r12^ 0/1 ^r12^ -|-----/<-r11--|+coordArr
     *
     * @param numQuadX
     * @param numQuadY
     * @param x
     * @param y
     * @param r11
     * @param r12
     * @param numTRI
     * @return
     */
  /*  public Color getMaillageTRIColor(int numQuadX, int numQuadY, double x,
                                     double y, double r11, double r12, int numTRI) {

        double dx = 0;
        double dy = 0;
        if (numTRI == 0) {
            dx = r11;
            dy = r12;
        } else if (numTRI == 1) {
            dx = 1 - r11;
            dy = r12;
        }
        int xi = ((int) ((((int) x + dx) / numQuadX + Math.signum(numTRI - 0.5)
                * ecBufferedImageStructureMatrix.getWidth())));
        int yi = ((int) ((((int) y + dy) / numQuadY * ecBufferedImageStructureMatrix.getHeight())));
        Point2D p = getCoord(xi / (double) ecBufferedImageStructureMatrix.getWidth(), yi / (double) ecBufferedImageStructureMatrix.getHeight());

        int x1 = (int) p.x;
        int y1 = (int) p.y;

        Color c = new Color(ecBufferedImageStructureMatrix.getRGB(x1, y1));
        if (c.equals(transparent)) {
            return new Color(transparent);
        } else {
            return c;
        }
    }
*/
}
