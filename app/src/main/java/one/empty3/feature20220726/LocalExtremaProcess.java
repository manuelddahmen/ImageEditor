package one.empty3.feature20220726;

import one.empty3.io.ProcessFile;

import java.io.File;

public class LocalExtremaProcess extends ProcessFile {
    ExtremaProcess extremaProcess = new ExtremaProcess();

    public LocalExtremaProcess() {

    }

    @Override
    public boolean process(File in, File out) {
        return extremaProcess.process(in, out);
    }
}
