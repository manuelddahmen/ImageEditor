/*
 * Copyright (c) 2024.
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

package one.empty3.feature.app.maxSdk29.pro;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.datatransport.BuildConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import one.empty3.Polygon1;
import one.empty3.androidFeature.GoogleFaceDetection;
import one.empty3.featureAndroid.PixM;
import one.empty3.library.Point3D;
import one.empty3.libs.Image;

public class GoogleFaceDetectTest {
    private String path = "/data/data/one.empty3.feature.app.maxSdk29.pro";
    private String[] modelsFiles = new String[]{
        "/data/data/one.empty3.feature.app.maxSdk29.pro/model.fac (10)",
            "/data/data/one.empty3.feature.app.maxSdk29.pro.test/model.v4.fac"};
    private String instantFile;

    @Before
    public void setup() {
        String permission;
        permission = Manifest.permission.READ_MEDIA_IMAGES;
        InstrumentationRegistry.getInstrumentation().getUiAutomation().grantRuntimePermission(BuildConfig.APPLICATION_ID, permission);
        permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        //InstrumentationRegistry.getInstrumentation().getUiAutomation().grantRuntimePermission(BuildConfig.APPLICATION_ID, permission);
        permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        InstrumentationRegistry.getInstrumentation().getUiAutomation().grantRuntimePermission(BuildConfig.APPLICATION_ID, permission);
        instantFile = path+"/model-1.fac";
    }

    @Test
    public void testLoadSaveInt() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        GoogleFaceDetection googleFaceDetection = new GoogleFaceDetection(new Image(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)));
        googleFaceDetection.getDataFaces().add(new GoogleFaceDetection.FaceData());


        String filename = path+"/model-" + UUID.randomUUID() + ".fac";
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filename));
            googleFaceDetection.encode(dataOutputStream);
            Assert.assertTrue(true);
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
            return;
        }
        GoogleFaceDetection googleFaceDetection1 = new GoogleFaceDetection(new Image(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)));

        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filename));
            googleFaceDetection1 = (GoogleFaceDetection) googleFaceDetection1.decode(dataInputStream);
            dataInputStream.close();
            Assert.assertTrue(true);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
            return;
        }

        Assert.assertEquals(googleFaceDetection.getDataFaces().size(), googleFaceDetection1.getDataFaces().size());
    }

    @Test
    public void testLoadSavePixM() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        int imageManu = R.drawable.imagemanu;
        Drawable drawable = getDrawable(applicationContext, imageManu);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                try {
                    String filename = path+"/model-" + UUID.randomUUID() + "-pixm.fac";
                    Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                    bitmap.reconfigure(bitmap.getWidth()/4, bitmap.getHeight()/4, Bitmap.Config.ARGB_8888);
                    PixM pixM = new PixM(new Image(bitmap));
                    PixM pixReloaded = new PixM(1, 1);
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filename));
                    pixM.encode(dataOutputStream);
                    Assert.assertTrue(true);
                    dataOutputStream.close();
                    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filename));
                    pixReloaded = (PixM) pixReloaded.decode(dataInputStream);
                    dataInputStream.close();
                    Assert.assertTrue(true);

                    Assert.assertEquals(pixReloaded, pixM);

                } catch (IOException e) {
                    e.printStackTrace();
                    Assert.fail();
                    return;
                }
            }
        }
    }
    @Test
    public void testLoadSaveGoogleFaceDetection() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        int imageManu = R.drawable.imagemanu;
        Drawable drawable = getDrawable(applicationContext, imageManu);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                try {
                    String filename = path+"/model-" + UUID.randomUUID() + "-pixm.fac";
                    Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap bitmap1 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    bitmap1.reconfigure(200, 200, Bitmap.Config.ARGB_8888);
                    PixM pixM = PixM.getPixM(new Image(bitmap1), 0.0);

                    Polygon1 polygon1 = new Polygon1();
                    int pointsSize = (int) (Math.random() * 20);
                    for(int i=0; i<pointsSize; i++) {
                        polygon1.getPoints().add(Point3D.random(400.0));
                    }

                    GoogleFaceDetection googleFaceDetection = new GoogleFaceDetection(new Image(bitmap1));

                    googleFaceDetection.getDataFaces().add(new GoogleFaceDetection.FaceData());

                    googleFaceDetection.getDataFaces().get(0).setPhoto(pixM.copy());

                    googleFaceDetection.getDataFaces().get(0).getFaceSurfaces().add(
                            new GoogleFaceDetection.FaceData.Surface(0, polygon1, pixM, 10, 111, 838, pixM.copy(), false));

                    GoogleFaceDetection googleFaceDetection1 = new GoogleFaceDetection(new Image(bitmap1));
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filename));
                    googleFaceDetection.encode(dataOutputStream);
                    Assert.assertTrue(true);
                    dataOutputStream.close();
                    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filename));
                    googleFaceDetection1 = (GoogleFaceDetection) googleFaceDetection1.decode(dataInputStream);
                    dataInputStream.close();
                    Assert.assertTrue(true);

                    Assert.assertTrue(googleFaceDetection1.equals(googleFaceDetection));
                    System.err.println("Number of faces read: " + googleFaceDetection1.getDataFaces().size());

                } catch (IOException e) {
                    e.printStackTrace();
                    Assert.fail();
                    return;
                }
            }
        }
    }

    @Test
    public void testLoadSavePolygon() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        int imageManu = R.drawable.imagemanu;
        Drawable drawable = getDrawable(applicationContext, imageManu);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                try {
                    String filename = path+"/model-" + UUID.randomUUID() + "-polygon.fac";
                    Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                    bitmap.reconfigure(bitmap.getWidth()/4, bitmap.getHeight()/4, Bitmap.Config.ARGB_8888);
                    Polygon1 polygon = new Polygon1();
                    int pointsSize = (int) (Math.random() * 20);
                    for(int i=0; i<pointsSize; i++) {
                        polygon.getPoints().add(Point3D.random(400.0));
                    }
                    Polygon1 polygonReloaded = new Polygon1();
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(filename));
                    polygon.encode(dataOutputStream);
                    Assert.assertTrue(true);
                    dataOutputStream.close();
                    DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filename));
                    polygonReloaded = (Polygon1) polygonReloaded.decode(dataInputStream);
                    dataInputStream.close();
                    Assert.assertTrue(true);

                    Assert.assertEquals(polygon, polygonReloaded);

                } catch (IOException e) {
                    e.printStackTrace();
                    Assert.fail();
                    return;
                }
            }
        }
    }
    @Test
    public void loadSaveTest() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
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

                    applicationContext.setTheme(R.attr.theme);

                    faceOverlayView.setBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10000000);

                    GoogleFaceDetection googleFaceDetection;
                    if(faceOverlayView.getGoogleFaceDetection()==null && GoogleFaceDetection.getInstance(false, null)==null)
                        Assert.fail();
                    googleFaceDetection = GoogleFaceDetection.getInstance(false, null);

                    if (googleFaceDetection == null) {
                        System.err.println("GoogleFaceDetection == null");
                        Assert.fail();
                    } else {
                    }
                    googleFaceDetection.encode(new DataOutputStream(
                            new FileOutputStream(instantFile)));
                    byteArrayOutputStream.close();

                    System.err.println("Number of faces 1 : " + googleFaceDetection.getDataFaces().size());

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
            Context applicationContext = ApplicationProvider.getApplicationContext();
            int imageManu = R.drawable.imagemanu;
            Drawable drawable = getDrawable(applicationContext, imageManu);
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    File currentRelativePath = new File(instantFile);
                    String s = currentRelativePath.toString();
                    System.out.println("Current absolute path is: " + s);

                    while(instantFile==null){
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    File file = new File(instantFile);
                    while(!file.exists()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    InputStream inputStream = new FileInputStream(instantFile);
                    GoogleFaceDetection googleFaceDetection = new GoogleFaceDetection(new Image(bitmap));
                    googleFaceDetection
                            = (GoogleFaceDetection) googleFaceDetection.decode(new DataInputStream(inputStream));

                    if (googleFaceDetection == null) {
                        Assert.fail();
                    } else {
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1000000);

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

                }
            }
        } catch (IOException e6) {
            e6.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testGoogleFaceDetectCopyPart() {

    }
}

