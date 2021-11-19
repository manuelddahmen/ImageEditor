package one.empty3;

import one.empty3.io.ProcessFile;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class FFMpeg {
    private File inputFile;
    private final String subdirTemp;
    private final File ffmpegExe;
    private File tempDir;

    private final Properties appSettings;
    private final CharSequence filenamePattern;

    private final String encodeArgs;

    private final String decodeArgs;
    private String fileNameStr;
    private int indexAuto = 1;
    private String simpleName;
    private File[] list0;

    {
        appSettings = new Properties();
        try {
            appSettings.load(new FileInputStream("process-suite.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ffmpegExe = new File(appSettings.getProperty("ffmpegExe"));
        tempDir = new File(appSettings.getProperty("tempDir"));
        filenamePattern = appSettings.getProperty("ffmpeg.filenamePattern");
        encodeArgs = appSettings.getProperty("encodeArgs");
        decodeArgs = appSettings.getProperty("decodeArgs");
    }

    public static void config(File tempDir, File ffmpegExe) {

    }

    public FFMpeg(String dirTemp, File inputFile) {
        this.tempDir = new File(tempDir.getAbsolutePath() + File.separator + dirTemp);
        this.inputFile = inputFile;
        this.subdirTemp = createTemp();
        this.simpleName = inputFile.getName();
        tempDir.mkdirs();
    }

    private String createTemp() {
        String s = tempDir.getAbsolutePath() + File.separator + indexAuto;
        boolean mkdirs = new File(s).mkdirs();
        return s;
    }

    /**
     * @return temp out dir (decoded frames)
     */
    public File[] extraireImagesJpg() {
        String fileNameMPG = inputFile.getAbsolutePath();

        String s2 = fileNameStr = createTemp();

        System.out.printf("\nExtract file ...\n%s\n to temp dir\n\n%s\n", fileNameMPG, s2);
        String filenameOutputFfmpeg = //fileNameMPG + File.separator +
                s2 + File.separator + inputFile.getName() + "--%04d.jpg";
        String command = ffmpegExe + (" -y -i " + fileNameMPG + " \"" + filenameOutputFfmpeg + "\"");

        System.out.printf("Commande: %s\n", command);

        String cmd = command;
        Runtime run = Runtime.getRuntime();
        Process pr = null;
        try {
            execCommand(cmd, run);

            System.out.printf("Extracted files count:  %d\n", Objects.requireNonNull(
                    new File(s2).listFiles()).length);

            return new File(s2).listFiles();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        inputFile = new File(s2);

        list0 = new File(s2).listFiles();

        return inputFile.listFiles();
    }

    private void execCommand(String cmd, Runtime run) throws IOException, InterruptedException {
        Process pr;
        pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

        // Read any errors from the attempted command:
        System.out.println("Here is the standard error of the command (if any):\n");
        String s;
        while ((s = stdError.readLine()) != null)
            System.out.println(s);
        String line = "";
        while ((line = buf.readLine()) != null) {
            System.out.println(line);

        }
        // Read any errors from the attempted command:
        System.out.println("Here is the standard error of the command (if any):\n");
        s = null;
        while ((s = stdError.readLine()) != null)
            System.out.println(s);
        line = null;
        while ((line = buf.readLine()) != null) {
            System.out.println(line);
        }

    }

    private void incr() {
        indexAuto++;
    }

    public File[] forEach(File fileNameStr) {
        File dir = new File(createTemp());
        if (dir.exists() && dir.isDirectory())
            return Objects.requireNonNull(dir.listFiles());
        else
            System.out.printf("Error forEach, file %s doesn't exist or is not a directory", dir.getAbsolutePath());
        return null;
    }

    /***
     *
     * extracted farmes
     * @return File out
     */
    public boolean encoderImagePngMpg() {
        String fileNameMPG = inputFile.getAbsolutePath() + ".out.mp4";

        String command = String.format("%s -y %s \"%s/%s\"", ffmpegExe, encodeArgs,
                inputFile.getAbsolutePath(), fileNameMPG);
        String s2 = getInputFolder();

        String cmd = command;
        Runtime run = Runtime.getRuntime();
        Process pr = null;
        try {
            execCommand(cmd, run);
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getInputFolder() {
        return createTemp();
    }


    public void process(ProcessFile processInstance) throws IOException {
        File frame = inputFile;
        String s = inputFile.getAbsolutePath();
        File file = new File(s.substring(0, s.lastIndexOf(File.separator)) + File.separator
                + simpleName + processInstance.getClass().getSimpleName() + ".jpg");
        System.out.printf("Input file :    %s\n" +
                "Output file :   %s\n" +
                "Process class : %s\n", inputFile.getAbsolutePath(), file.getAbsolutePath(), processInstance.getClass().getSimpleName());
        processInstance.process(frame, file);
        inputFile = file;
        incr();
    }

    public File getInputFile() {
        return inputFile;
    }

    public File[] getOutputFolder() {
        return list0;
    }
}
