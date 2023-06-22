/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ECBufferedImage {
    public static final int TYPE_INT_RGB = 1;
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
                Bitmap.Config.ARGB_8888);
    }

    public ECBufferedImage(int width, int height, int imageType) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
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

    public void setRGB(int i, int j, int elementCouleur) {
        bitmap.setPixel(i, j, elementCouleur);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
