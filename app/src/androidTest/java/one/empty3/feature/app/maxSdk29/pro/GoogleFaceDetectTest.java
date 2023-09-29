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

package one.empty3.feature.app.maxSdk29.pro;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Application;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javaAnd.awt.image.BufferedImage;
import one.empty3.feature20220726.GoogleFaceDetection;

public class GoogleFaceDetectTest {
    @Test
    public void loadSaveTest()   {
        Application app =
                (Application) InstrumentationRegistry
                        .getTargetContext()
                        .getApplicationContext();
        int imageManu = R.mipmap.imagemanu;
        Drawable drawable = getDrawable(app.getApplicationContext(), imageManu);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                try {
                    BufferedImage image = new BufferedImage(bitmapDrawable.getBitmap());

                    FaceOverlayView faceOverlayView = new FaceOverlayView(app.getApplicationContext());

                    faceOverlayView.setBitmap(image.bitmap);
                    faceOverlayView.updateImage(image.bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100000000);

                    GoogleFaceDetection googleFaceDetection;
                    googleFaceDetection = faceOverlayView.getGoogleFaceDetection();

                    if(googleFaceDetection==null) {
                        googleFaceDetection = new GoogleFaceDetection();
                        System.err.println("GoogleFaceDetection == null");
                    } else {
                    }
                    googleFaceDetection.encode(new DataOutputStream(byteArrayOutputStream));
                    byteArrayOutputStream.close();

                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
                    GoogleFaceDetection googleFaceDetection1=
                            (GoogleFaceDetection) googleFaceDetection.decode(new DataInputStream(byteArrayInputStream));
                    byteArrayInputStream.close();

                    System.err.println("Number of faces 1 : "+googleFaceDetection.getDataFaces().size());
                    System.err.println("Number of faces 2 : "+googleFaceDetection1.getDataFaces().size());


                    assert(googleFaceDetection1.equals(googleFaceDetection));

                } catch (IOException e6) {
                    e6.printStackTrace();
                    Assert.fail();
                }
            }
        }
    }

}
