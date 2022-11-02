package one.empty3.feature20220726;

import one.empty3.feature20220726.kmeans.K_Clusterer;
import one.empty3.feature20220726.kmeans.MakeDataset;
import one.empty3.io.ProcessFile;

import java.io.File;


public class KMeans extends ProcessFile {
    protected K_Clusterer k_clusterer;

    public boolean process(File in, File out) {
        if (!in.getAbsolutePath().endsWith("jpg"))
            return false;
        // init centroids with random colored
        // points.
        try {
            new MakeDataset(in, new File(out.getAbsolutePath() + ".csv"), maxRes);

            k_clusterer = new K_Clusterer();
            k_clusterer.process(in, new File(out.getAbsolutePath() + ".csv"), out, maxRes);


/*
            Paste paste = new Paste();

            paste.pasteList();
*/
            return true;


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
