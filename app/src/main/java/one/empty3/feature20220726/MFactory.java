/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

package one.empty3.feature20220726;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.MappedByteBuffer;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;

public class MFactory {
//
//    public static InterfaceMatrix getInstance(Bitmap bitmap) {
//        return new M(bitmap);
//    }
//    public static InterfaceMatrix getInstance(Bitmap bitmap, int resMax) {
//        return new MBitmap(bitmap, resMax);
//    }
//    public static InterfaceMatrix getInstance(int lines, int columns, boolean isBitmap) {
//        if(isBitmap) {
//            return new MBitmap(lines, columns);
//        } else {
//            return new M(lines, columns);
//        }
//
//    }
//
//    public static InterfaceMatrix getInstance(File in, boolean isBitmap) {
//        if(isBitmap)  {
//            FileInputStream fileInputStream = null;
//            try {
//                fileInputStream = new FileInputStream(in);
//                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
//                return new MBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//
//                e.printStackTrace();
//            }
//            return null;
//        } else {
//            BufferedImage read = ImageIO.read(in);
//            assert read != null;
//            return new PixM(read);
//        }
//    }
}
