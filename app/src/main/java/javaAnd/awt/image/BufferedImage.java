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

package javaAnd.awt.image;

//import android.graphics.Bitmap;

import android.graphics.Bitmap;

import one.empty3.library.ITexture;

public class BufferedImage {


    public static final int TYPE_INT_RGB = 0;
    public static final int TYPE_INT_ARGB = 1;
    public static final int TYPE_BYTE_GRAY = 2;
    public Bitmap bitmap;

    public BufferedImage() {
    }

    public BufferedImage(int columns, int lines, int typeIntRgb) {
        this();
        this.bitmap = Bitmap.createBitmap(columns, lines, Bitmap.Config.RGB_565);
    }

    public BufferedImage(Bitmap decodeStream) {
        this.bitmap = decodeStream;
    }

    public void setRGB(int i, int j, int anInt) {
        bitmap.setPixel(i, j, anInt);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public int getRGB(int x, int y) {
        return bitmap.getPixel(x, y);
    }

    public void drawImage(BufferedImage img, int x, int y, int w, int h) {
    }

    public BufferedImage getGraphics() {
        return null;
    }

    public Graphics2D createGraphics() {
        return null;
    }

    public void drawImage(BufferedImage total, int x, int y) {
    }

    public void setColor(int x, int y, int color) {
        bitmap.setPixel(x, y, color);
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        double dist = Math.sqrt((x1 - x2) * (x2 - y2) + (y1 - y2) * (y1 - y2));
        for (double i = 0; i < 1.0; i += 1. / dist) {
            double x = x1 + i * (x2 - x1);
            double y = y1 + i * (y2 - y1);
            bitmap.setPixel((int) x, (int) y, color);
        }
    }

    public void drawOval(int i, int i1, int i2, int i3) {
    }

    public void drawRect(int x, int y1, int width, int height, ITexture texture) {
    }
}
