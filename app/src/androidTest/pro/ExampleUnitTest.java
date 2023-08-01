package one.empty3.feature.app.maxSdk29.pro;

import one.empty3.Main2022;
import one.empty3.io.ProcessFile;
import org.junit.Test;

import java.io.File;
import java.util.Objects;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    int countTestsProcessFiles = 0;
    private int nonApplicable = 0;
    private int errors = 0;

    @Test
    public void addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2);
    }

    public void effect(ProcessFile processFile, File in, File out) {
        System.out.println("ProcessFile : " + processFile.getClass());
        System.out.println("in : " + in.getAbsolutePath());
        System.out.println("out: " + out.getAbsolutePath());
        try {
            processFile.setMaxRes(1200);
            processFile.shouldOverwrite = true;
            if (processFile.isImage(in)) {
                Assert.assertTrue(processFile.process(in, out));
                countTestsProcessFiles++;
            } else {
                System.err.println("ProcessFile returns false\nor in.isImage==false\nor in not exist\nor in is not a file\n" + processFile.getClass());
                nonApplicable++;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("ProcessFile throws exception\n" + processFile.getClass());
            errors++;
        }

    }

    @Test
    public void testAllTestInMain2022() {
        Main2022.initListProcesses().forEach(new BiConsumer<String, ProcessFile>() {
            @Override
            public void accept(String s, ProcessFile processFile) {
                File ins = new File("C:/Users/manue/EmptyCanvasTest/images/m");
                String[] files = ins.list();
                for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                    File in = new File(ins.getAbsoluteFile()+"/"+files[i]);
                    ///++File in = new File(ins.getAbsolutePath()+File.separator+files[i]);
                    File dir = new File(ins.getParent() + "/" + "imagesOut");
                    boolean mkdirs = true;
                    if (!dir.exists()) {
                        mkdirs = dir.mkdirs();
                    }
                    if(mkdirs) {
                        String inFilename = in.getName();
                        effect(processFile, in,
                                new File(dir.getAbsolutePath() + "/" + s + "-" + inFilename));
                    }
                }
            }
        });


        System.out.println("Count success=" + countTestsProcessFiles);
        System.out.println("Count non applicable = " + nonApplicable);
        System.out.println("Count errors = " + errors);
    }

}