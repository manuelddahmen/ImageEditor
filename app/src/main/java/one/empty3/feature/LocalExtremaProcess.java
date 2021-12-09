package one.empty3.feature;

import one.empty3.feature.ExtremaProcess;
import one.empty3.io.ProcessFile;

import java.io.File;

public class LocalExtremaProcess extends ProcessFile {
    one.empty3.feature.ExtremaProcess extremaProcess = new ExtremaProcess();

    public LocalExtremaProcess() {

    }

    @Override
    public boolean process(File in, File out) {
        return extremaProcess.process(in, out);
    }
}
