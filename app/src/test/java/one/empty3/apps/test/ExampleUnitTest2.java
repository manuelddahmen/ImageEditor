package one.empty3.apps.test;

import android.os.Build;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

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

    public void effect(ProcessFile processFile, File in, File out) {
        System.out.println("ProcessFile : " + processFile.getClass());
        System.out.println("in : " + in.getAbsolutePath());
        System.out.println("out: " + out.getAbsolutePath());
        try {
            processFile.setMaxRes(maxRes);
            processFile.shouldOverwrite = true;
                Assert.assertTrue(processFile.process(in, out));
                countTestsProcessFiles++;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("ProcessFile throws exception\n" + processFile.getClass());
            errors++;
        }

    }

    @Test
    public void testAllTestInMain2022() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Main2022.initListProcesses().forEach((s, processFile) -> {
                File ins = new File("D:\\Current\\EmptyCanvasTest\\images\\m\\");
                File[] files = ins.listFiles();
                if (files != null) {
                    for (File in : files) {
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

            });


            System.out.println("Count success=" + countTestsProcessFiles);
            System.out.println("Count non applicable = " + nonApplicable);
            System.out.println("Count errors = " + errors);
        }
    }

}