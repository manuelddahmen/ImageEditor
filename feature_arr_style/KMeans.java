package one.empty3.feature_arr_style;

import one.empty3.io.ProcessFile;

import java.io.File;

import one.empty3.feature_arr_style.kmeans.*;


public class KMeans extends ProcessFile {
    public boolean process(File in, File out) {

        // init centroids with random colored
        // points.
        try {
            new MakeDataset(in,
                    new File(out.getAbsolutePath() + ".csv"), maxRes);

            K_Clusterer k_clusterer = new K_Clusterer();
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
