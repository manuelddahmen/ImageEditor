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

package one.empty3.library;

import one.empty3.library.elements.PPMFileInputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ECBufferedImage {
    Bitmap bitmap;
    /*__
     *
     */
    private static final long serialVersionUID = 2739941470855574089L;
    private int pixelCountMax = 5;
    private int squarepixelCountMax = 25;

    public ECBufferedImage(Bitmap read) {
        bitmap = Bitmap.createBitmap(read.getWidth(),
                read.getHeight(),
                Bitmap.Config.RGB_565);
    }

    public ECBufferedImage(int width, int height, int imageType) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }


    public static ECBufferedImage ppm(byte[] bytes, String ppm) {
        return null;
    }

    public static Bitmap getFromFile(File url) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(url));
        return bitmap;
    }

    private void getRGBA(int rgba, int[] componentsRGBA) {
        int a = (rgba & 0xFF000000) >> 24;
        int r = (rgba & 0xFF000000) >> 16;
        int g = (rgba & 0xFF000000) >> 8;
        int b = (rgba & 0xFF000000) >> 0;

        componentsRGBA[0] = a;
        componentsRGBA[1] = r;
        componentsRGBA[2] = g;
        componentsRGBA[3] = b;


    }

    @Override
    public int hashCode() {
        int result = pixelCountMax;
        result = 31 * result + squarepixelCountMax;
        return result;
    }
}
