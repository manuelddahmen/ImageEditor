package one.empty3.feature.app.maxSdk29.pro;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import one.empty3.Main2022;
import one.empty3.androidFeature.IdentNullProcess;
import one.empty3.featureAndroid.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.Lumiere;
import one.empty3.libs.Color;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest2 {
    int countTestsProcessFiles = 0;
    private int countNonApplicable = 0;
    private int errors = 0;
    int maxRes = 15;

    @Test
    public void testBitmapPixMColors( ) {
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
        try {
            File outputimage = new File("/storage/170E-321D/Pictures/testBitmapPixMColorRed100x100.jpg");
            if(outputimage.exists())
                outputimage.delete();
            outputimage = new File(outputimage.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
            pixM.getBitmap().saveFile(outputimage);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBitmapColors( ) {
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
        try {
            File outputimage = new File("/storage/170E-321D/Pictures/testBitmapColorRed100x100.jpg");
            if(outputimage.exists())
                outputimage.delete();
            outputimage = new File(outputimage.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPixMColors( ) {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        PixM pixM = new PixM(1000, 1000);
        Color color = new Color(0xFFFF0000);
        int width = pixM.getColumns();
        int height = pixM.getLines();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixM.set(pixM.index(i, j), color.getRGB());
            }
        }
        File outputimage = new File("/storage/170E-321D/Pictures/testPixMColorRed1000x1000.jpg");
        if (outputimage.exists())
            outputimage.delete();
        outputimage = new File(outputimage.getAbsolutePath());
        //FileOutputStream fileOutputStream = new FileOutputStream(outputimage);
        pixM.getBitmap().saveFile(outputimage);
        //fileOutputStream.close();
    }
    @Test
    public void addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2);
    }

    public void effect(@NonNull ProcessFile processFile, File in, File out) {
        System.out.println("ProcessFile : " + processFile.getClass());
        System.out.println("in : " + in.getAbsolutePath());
        System.out.println("out: " + out.getAbsolutePath());
        try {
            processFile.setMaxRes(maxRes);
            ProcessFile.shouldOverwrite = true;
            if(processFile.process(in, out)) {
                countTestsProcessFiles++;
            } else {
                countNonApplicable++;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("ProcessFile throws exception\n" + processFile.getClass());
            errors++;
        }

    }
/*
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_MEDIA_IMAGES",
                    "android.permission.WRITE_EXTERNAL_STORAGE");
*/
    @Test
    public void testAllTestInMain2022() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        System.out.println(appContext.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            HashMap<String, ProcessFile> stringProcessFileHashMap = Main2022.initListProcesses();
            stringProcessFileHashMap.forEach((s, processFile) -> {
                boolean mkdirs = true;
                File ins = new File("/storage/170E-321D/Pictures/m");
                java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins=" + ins.getAbsolutePath());
                java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins?" + ins.exists());
                java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins.isDirectory?" + ins.isDirectory());
                //File[] files = ins.listFiles();
                String[] files = ins.list();
                if (files != null) {
                    for (String inS : files) {
                        File in = new File(ins.getAbsolutePath() + File.separator + inS);
                        if ((!in.getName().endsWith(".jpg") && !in.getName().endsWith(".png")) || in.getName().endsWith("_1.jpg")) {
                        }
                        else if (in.exists()) {
                            try {
                                File dir0 =new File( ins.getParent() + File.separator + "imagesOut_resized/");
                                if(!dir0.exists() && mkdirs)
                                    dir0.mkdirs();
                                File out0 = new File(dir0.getAbsolutePath() + File.separator +in.getName() + "_1.jpg");
                                File dir = new File(ins.getParent() + File.separator + "imagesOut/" + processFile.getClass().getSimpleName());
                                if (!dir.exists() && mkdirs) {
                                    mkdirs = dir.mkdirs();
                                }
                                File out = new File(dir.getAbsolutePath() + File.separator + s + "-" + in.getName());
                                if (mkdirs) {
                                    String inFilename = in.getName();
                                    effect(new IdentNullProcess(), in,
                                            out0);
                                    effect(processFile, out0,
                                            out);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } else
                    System.err.println("Error : files==null");
            });

            System.out.println("Count success=" + countTestsProcessFiles);
            System.out.println("Count non applicable = " + countNonApplicable);
            System.out.println("Count errors = " + errors);
        }
    }

}