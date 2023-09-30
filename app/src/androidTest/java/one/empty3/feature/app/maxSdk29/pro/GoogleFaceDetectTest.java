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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import one.empty3.feature20220726.GoogleFaceDetection;

public class GoogleFaceDetectTest {
    @Test
    public void loadSaveTest() {
        Context applicationContext = ApplicationProvider.getApplicationContext().getApplicationContext();
        int imageManu = R.drawable.imagemanu;
        Drawable drawable = getDrawable(applicationContext, imageManu);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                try {
                    Bitmap bitmap = bitmapDrawable.getBitmap();

                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                    bitmap.reconfigure(bitmap.getWidth()/4, bitmap.getHeight()/4,
                            Bitmap.Config.ARGB_8888);

                    System.out.println("Input Bitmap = (" + bitmap.getWidth()+","+bitmap.getHeight()+")");

                    FaceOverlayView faceOverlayView = new FaceOverlayView(applicationContext);

                    faceOverlayView.setBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10000000);

                    GoogleFaceDetection googleFaceDetection;
                    if(faceOverlayView.getGoogleFaceDetection()==null && GoogleFaceDetection.getInstance(false)==null)
                        Assert.fail();
                    googleFaceDetection = GoogleFaceDetection.getInstance(false);

                    if (googleFaceDetection == null) {
                        System.err.println("GoogleFaceDetection == null");
                        Assert.fail();
                    } else {
                    }
                    googleFaceDetection.encode(new DataOutputStream(byteArrayOutputStream));
                    byteArrayOutputStream.close();

                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
                    GoogleFaceDetection googleFaceDetection1 =
                            (GoogleFaceDetection) googleFaceDetection.decode(new DataInputStream(byteArrayInputStream));
                    byteArrayInputStream.close();

                    System.err.println("Number of faces 1 : " + googleFaceDetection.getDataFaces().size());
                    System.err.println("Number of faces 2 : " + googleFaceDetection1.getDataFaces().size());


                    assert (googleFaceDetection1.equals(googleFaceDetection));

                } catch (IOException e6) {
                    e6.printStackTrace();
                    Assert.fail();
                }
            }
        }
    }

    @Test
    public void loadModel() {
        try {
            Context applicationContext = ApplicationProvider.getApplicationContext().getApplicationContext();
            InputStream inputStream = getClass().getResourceAsStream("model.fac");
            GoogleFaceDetection googleFaceDetection = new GoogleFaceDetection();
            googleFaceDetection
                    = (GoogleFaceDetection) googleFaceDetection.decode(new DataInputStream(inputStream));

            if (googleFaceDetection == null) {
                googleFaceDetection = new GoogleFaceDetection();
                System.err.println("GoogleFaceDetection == null");
            } else {
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100000000);

            googleFaceDetection.encode(new DataOutputStream(byteArrayOutputStream));
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.close();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            GoogleFaceDetection googleFaceDetection1 =
                    (GoogleFaceDetection) googleFaceDetection.decode(new DataInputStream(byteArrayInputStream));
            byteArrayInputStream.close();

            System.err.println("Number of faces 1 : " + googleFaceDetection.getDataFaces().size());
            System.err.println("Number of faces 2 : " + googleFaceDetection1.getDataFaces().size());


            assert (googleFaceDetection1.equals(googleFaceDetection));


        } catch (IOException e6) {
            e6.printStackTrace();
            Assert.fail();
        }
    }
}

