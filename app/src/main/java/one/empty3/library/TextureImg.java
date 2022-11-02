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

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;


/*__
 * @author manu
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class TextureImg extends ITexture {

    private StructureMatrix<Bitmap> image = new StructureMatrix<>(0, ECBufferedImage.class);

    private String nom = "texture";

    private String file = "image.png";

    //private AVIReader reader;
    private int track = 0;
    private File avifile = null;
    private int transparent = Color.valueOf(1, 1, 1, 1).toArgb();

    public TextureImg() {

    }

    public TextureImg(Bitmap bi) {
        this.image.setElem(bi);
    }

    public TextureImg(ECBufferedImage ecBufferedImage) {
        this(ecBufferedImage.bitmap);
    }

    @Override
    public void iterate() throws EOFVideoException {

    }

    @Override
    public int getColorAt(double rx, double ry) {
        Point2D trans = getCoord(rx, ry);
        return couleur(trans.x, trans.y);
    }

    protected int couleur(double rx, double ry) {
        int x = (int) (rx * image.getElem().getWidth());
        int y = (int) (ry * image.getElem().getHeight());
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x >= image.getElem().getWidth()) {
            x = image.getElem().getWidth() - 1;
        }
        if (y >= image.getElem().getHeight()) {
            y = image.getElem().getHeight() - 1;
        }


        int c = image != null ? image
                .getElem().getPixel(x, y)
                :
                transparent;

        return c;
    }


    public Bitmap getImage() {
        return image.getElem();
    }

    public void setImage(Bitmap image) {
        this.image.setElem(image);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTransparent(Color tr) {
        this.transparent = tr.toArgb();
    }

    public void timeNext() {
    }

    public void timeNext(long milli) {
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return image;
    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }


//    public String toString() {
//        String imageString = null;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//        try {
//            ImageIO.write(image.getElem(), "jpg", bos);
//            byte[] imageBytes = bos.toByteArray();
//
//            Base64.Encoder encoder =Base64.getEncoder();
//            imageString = encoder.encodeToString(imageBytes);
//
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String t  = "textureImg( filename:"+getFile()+"\n\tdata : { "+imageString+" } \n)";
//        return t;
//    }
//
}
