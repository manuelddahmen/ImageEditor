package one.empty3.feature.app.maxSdk29.pro;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.test.rule.GrantPermissionRule;

import com.google.firebase.BuildConfig;
import com.google.firebase.crashlytics.internal.Logger;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.logging.Level;

import one.empty3.Main2022;
import one.empty3.io.ProcessFile;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest2 {
    int countTestsProcessFiles = 0;
    private int nonApplicable = 0;
    private int errors = 0;
    int maxRes = 340;


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
            if(!out.exists()) {
                out.mkdir();
            }
            out = new File("./out/" + out.getName());
            Assert.assertTrue(processFile.process(in, out));
            countTestsProcessFiles++;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("ProcessFile throws exception\n" + processFile.getClass());
            errors++;
        }

    }

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_MEDIA_IMAGES",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void testAllTestInMain2022() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Main2022.initListProcesses().forEach((s, processFile) -> {
                File ins = new File("/storage/170E-321D/Podcasts/images/m");
                java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins=" + ins.getAbsolutePath());
                java.util.logging.Logger.getAnonymousLogger().log(Level.SEVERE, "ins?" + ins.exists());
                File[] files = ins.listFiles();
                if (files != null) {
                    for (File in : files) {
                        if (!in.getName().endsWith(".jpg") && !in.getName().endsWith(".png"))
                            continue;
                        if (in.exists()) {
                            File dir = new File(ins.getParent() + File.separator + "imagesOut");
                            boolean mkdirs = true;
                            if (!dir.exists()) {
                                mkdirs = dir.mkdirs();
                            }
                            File out = new File(dir.getAbsolutePath() + File.separator + s + "-" + in.getName());
                            if (mkdirs) {
                                String inFilename = in.getName();
                                effect(processFile, in,
                                        out);
                            }
                        }
                    }
                } else
                    System.err.println("Error : files==null");
                    Assert.assertTrue(false);

            });


            System.out.println("Count success=" + countTestsProcessFiles);
            System.out.println("Count non applicable = " + nonApplicable);
            System.out.println("Count errors = " + errors);
        }
    }

}