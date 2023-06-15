package one.empty3.feature20220726;

import java.io.File;

import one.empty3.io.ProcessNFiles;

public class Mix extends ProcessNFiles {
    private int progress;

    public void setProgressColor(int progress) {
        this.progress = progress;
    }
    public int getProgressColor() {
        return progress;
    }
    @Override
    public boolean processFiles(File out, File... ins) {
        super.processFiles(out, ins);
        return false;
    }
}
