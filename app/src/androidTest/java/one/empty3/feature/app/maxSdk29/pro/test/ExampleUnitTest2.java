package one.empty3.feature.app.maxSdk29.pro.test;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import one.empty3.Main2022;
import one.empty3.androidFeature.IdentNullProcess;
import matrix.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.libs.Color;
import one.empty3.libs.Image;

@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest2 {
    int countTestsProcessFiles = 0;
    private int countNonApplicable = 0;
    private int errors = 0;
    int maxRes = 15;
    public String emulatorPhotosDirPath = "/storage/emulated/0/Download/";

    @Before
    public void perms() {
    }

    @Test
    public void testBitmapPixMColors() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Color color = new Color(0xFFFF0000);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bitmap.setPixel(i, j, color.getRGB());
            }
        }
        PixM pixM = new PixM(bitmap);
        File outputimage = new File(emulatorPhotosDirPath + "testBitmapPixMColorRed100x100.jpg");
        if (outputimage.exists())
            outputimage.delete();
        pixM.getImage().saveFile(outputimage);
    }

    @Test
    public void testBitmapColors() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Color color = new Color(0xFFFF0000);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bitmap.setPixel(i, j, color.getRGB());
            }
        }
        File outputimage = new File(emulatorPhotosDirPath + "testBitmapColorRed100x100.jpg");
        outputimage = new File(outputimage.getAbsolutePath());
        if (outputimage.exists())
            outputimage.delete();
        new Image(bitmap).saveFile(outputimage);
    }

    @Test
    public void testPixMColors() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        PixM pixM = new PixM(1000, 1000);
        Color color = new Color(0xFFFF0000);
        int width = pixM.getColumns();
        int height = pixM.getLines();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixM.setInt(pixM.index(i, j), color.getRGB());
            }
        }
        File outputimage = new File(emulatorPhotosDirPath + "testPixMColorRed1000x1000.jpg");
        if (outputimage.exists())
            outputimage.delete();
        outputimage = new File(outputimage.getAbsolutePath());
        if (outputimage.exists())
            outputimage.delete();
        //FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
        pixM.getImage().saveFile(outputimage);
        //fileOutputStream.close();
    }

    @Test
    public void testPixMColorsAndroidColorValueOf1() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        PixM pixM = new PixM(1000, 1000);
        Color color = new Color(Color.valueOf(0xFFFF0000));
        int width = pixM.getColumns();
        int height = pixM.getLines();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixM.setInt(pixM.index(i, j), color.getRGB());
            }
        }
        File outputimage = new File(emulatorPhotosDirPath + "testPixMColorsAndroidColorValueOf1.jpg");
        if (outputimage.exists())
            outputimage.delete();
        outputimage = new File(outputimage.getAbsolutePath());
        //FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
        pixM.getImage().saveFile(outputimage);
        //fileOutputStream.close();
    }

    @Test
    public void testPixMColorsAndroidColorValueOf2() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        PixM pixM = new PixM(1000, 1000);
        Color color = new Color(0xFFFF0000);
        int width = pixM.getColumns();
        int height = pixM.getLines();
        System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double[] d1 = new double[5];
                pixM.setValues(i, j, color.getRed() / 255d, color.getGreen() / 255d, color.getBlue() / 255d);
            }
        }
        File outputimage = new File(emulatorPhotosDirPath + "testPixMColorsAndroidColorValueOf2.jpg");
        if (outputimage.exists())
            outputimage.delete();
        outputimage = new File(outputimage.getAbsolutePath());
        if (outputimage.exists())
            outputimage.delete();
        //FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
        pixM.getImage().saveFile(outputimage);
        //fileOutputStream.close();
    }

    @Test
    public void testPixMColorsAndroidGetPsetPrgb() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());

        Color[] colors = new Color[3];
        colors[0] = new Color(0xFFFF0000);
        colors[1] = new Color(0xFF00FF00);
        colors[2] = new Color(0xFF0000FF);
        String[] colorsName = {"RED", "GREEN", "BLUE"};
        for (int k = 0; k < 3; k++) {
            PixM pixM = new PixM(1000, 1000);
            int width = pixM.getColumns();
            int height = pixM.getLines();
            Color color = colors[k];
            System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            pixM.setP(0, 0, new Point3D(color.getRed()/255d, color.getGreen()/255d, color.getBlue()/255d));
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    double[] d1 = new double[5];
                    Point3D p = pixM.getP(0, 0);
                    pixM.setP(i, j, p);
                }
            }
            File outputimage = new File(emulatorPhotosDirPath + "testPixMColorsAndroidGetPsetP-" + colorsName[k] + ".jpg");
            if (outputimage.exists())
                outputimage.delete();
            outputimage = new File(outputimage.getAbsolutePath());
            //FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
            pixM.getImage().saveFile(outputimage);
        }
    }
    @Test
    public void testPixMGetSetRGB() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());

        Color[] colors = new Color[3];
        colors[0] = new Color(0xFFFF0000);
        colors[1] = new Color(0xFF00FF00);
        colors[2] = new Color(0xFF0000FF);
        String[] colorsName = {"RED", "GREEN", "BLUE"};
        for (int k = 0; k < 3; k++) {
            PixM pixM = new PixM(1000, 1000);
            int width = pixM.getColumns();
            int height = pixM.getLines();
            Color color = colors[k];


            PixM pixM2 = new PixM(1000, 1000);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    for (int l = 0; l < 3; l++) {
                        pixM2.setValues(i, j, color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f);

                    }
                }
            }



            System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
            pixM.setP(0, 0, new Point3D(Lumiere.getDoubles(color.getColor())));

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    for (int l = 0; l < 3; l++) {
                            pixM.setCompNo(l);
                            pixM2.setCompNo(l);
                            pixM.set(i, j, pixM2.get(i, j));
                    }
                    Point3D p = pixM.getP(0, 0);
                    pixM.setP(i, j, p);
                }
            }
            File outputimage = new File(emulatorPhotosDirPath + "testPixMGetSetRGB-" + colorsName[k] + ".jpg");
            if (outputimage.exists())
                outputimage.delete();
            outputimage = new File(outputimage.getAbsolutePath());
            //FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
            pixM.getImage().saveFile(outputimage);
        }
    }

    @Test
    public void addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2);
    }

    public boolean effect(@NonNull ProcessFile processFile, File in, File out) {
        boolean ret = false;
        System.out.println("ProcessFile : " + processFile.getClass());
        System.out.println("in : " + in.getAbsolutePath());
        System.out.println("out: " + out.getAbsolutePath());
        try {
            processFile = processFile.getClass().getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            processFile.setMaxRes(maxRes);
            ProcessFile.shouldOverwrite = true;
            if (processFile.process(in, out)) {
                countTestsProcessFiles++;
                ret = true;
            } else {
                countNonApplicable++;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("ProcessFile throws exception\n" + processFile.getClass());
            errors++;
        }

        return ret;
    }

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_MEDIA_IMAGES",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.READ_MEDIA_IMAGES");

    @Test
    public void testAllTestInMain2022() {
        // Context of the app under test.
        File ins = new File(emulatorPhotosDirPath + "m");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            HashMap<String, ProcessFile> stringProcessFileHashMap = Main2022.initListProcesses();
            java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins=" + ins.getAbsolutePath());
            java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins?" + ins.exists());
            java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins.isDirectory?" + ins.isDirectory());
            //File[] files = ins.listFiles();
            String[] files = ins.list();
            if (files != null) {
                Object[] entries = stringProcessFileHashMap.entrySet().toArray();
                for (String inS : files) {
                    String newFileSuffix = ""+UUID.randomUUID();
                    int r =(int)( Math.random()*(files.length-1));
                    try {
                    Map.Entry<String, ProcessFile> entry = (Map.Entry<String, ProcessFile>) entries[r];
                        String s = entry.getKey();
                        ProcessFile processFile = entry.getValue();
                        File in = new File(ins.getAbsolutePath() + File.separator + inS);
                        if ((!in.getName().endsWith(".jpg") && !in.getName().endsWith(".png")) || in.getName().endsWith("_1.jpg")) {
                        } else if (in.exists()) {
                            try {
                                ContextWrapper cw = new ContextWrapper(appContext);
                                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                                File dir0 = new File(directory, processFile.getClass().getSimpleName()+"-"+ins.getName() + "-1.jpg");
                                if (!dir0.exists()) {
                                    dir0.mkdirs();
                                }
                                File out0 = new File(dir0.getAbsolutePath() + File.separator + in.getName()+"-"+newFileSuffix+".jpg");
                                File dir = new File(ins.getParent() + File.separator + "imagesOut/" + processFile.getClass().getSimpleName());
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                File out = new File(dir.getAbsolutePath() + File.separator +  in.getName()+"-"+newFileSuffix+".jpg");

                                if (out0.exists() && out0.isFile()) {
                                    if (!out0.delete()) {
                                        Logger.getAnonymousLogger().log(Level.SEVERE, "Error : out0.delete()");
                                        continue;
                                    }
                                }
                                if (out.exists() && out.isFile()) {
                                    if (!out.delete()) {
                                        Logger.getAnonymousLogger().log(Level.SEVERE, "Error : out.delete()");
                                        continue;
                                    }

                                }

                                if (effect(new IdentNullProcess(), in, out0))
                                    if (effect(processFile, out0, out))
                                        assertTrue(true);
                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }
                        }

                    } catch (RuntimeException ex) {

                    }
                }
            } else
                System.err.println("Error : files==null");

            System.out.println("Count success=" + countTestsProcessFiles);
            System.out.println("Count non applicable = " + countNonApplicable);
            System.out.println("Count errors = " + errors);
        }
    }

}