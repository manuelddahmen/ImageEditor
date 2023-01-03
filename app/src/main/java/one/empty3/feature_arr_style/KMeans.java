/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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
