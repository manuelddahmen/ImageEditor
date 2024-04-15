/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;

import java.io.File;

import one.empty3.feature20220726.kmeans.K_Clusterer;
import one.empty3.feature20220726.kmeans.MakeDataset;
import one.empty3.io.ProcessFile;


public class KMeansRandom extends ProcessFile {
    protected K_Clusterer kclusterer;

    public boolean process(File in, File out) {
        if (!isImage(in))
            return false;
        // init centroids with random colored
        // points.
        try {
            File inCsv = new File(out.getAbsolutePath() + ".csv");
            if(inCsv.exists()) {
                boolean deleted = inCsv.delete();
                if(deleted)
                    System.out.println("Fichier csv supprimé");
            }


            new MakeDataset(in, inCsv, maxRes);

            kclusterer = new K_Clusterer();
            kclusterer.setRandom(true);
            kclusterer.process(in, inCsv, out, maxRes);

            addSource(out);

            return true;


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
