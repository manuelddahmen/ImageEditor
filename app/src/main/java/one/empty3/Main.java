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

package one.empty3;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import one.empty3.feature20220726.*;
import one.empty3.feature20220726.histograms.Histogram1;
import one.empty3.io.ProcessFile;

public class Main {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<ProcessFile> initListProcesses() {
        final ArrayList<ProcessFile> listProcessClasses = new ArrayList<>();
        try {

            listProcessClasses.add(Classification.class.newInstance());
            //listProcessClasses.add(CornerDetectProcess.class.newInstance());
            //listProcessClasses.add(CurveFitting.class.newInstance());
            listProcessClasses.add(DBScanProcess.class.newInstance());
            //listProcessClasses.add(DericheFilterProcess.class.newInstance());
            //listProcessClasses.add(DiffEnergy.class.newInstance());
            //listProcessClasses.add(Draw.class.newInstance());
            listProcessClasses.add(ExtremaProcess.class.newInstance());
            listProcessClasses.add(GaussFilterProcess.class.newInstance());
            listProcessClasses.add(GradProcess.class.newInstance());
            listProcessClasses.add(HarrisProcess.class.newInstance());
            //listProcessClasses.add(Histogram.class.newInstance());
            listProcessClasses.add(Histogram1.class.newInstance());
            listProcessClasses.add(Histogram2.class.newInstance());
            listProcessClasses.add(Histogram3.class.newInstance());
            listProcessClasses.add(HoughTransform.class.newInstance());
            //listProcessClasses.add(HoughTransformCircle.class.newInstance());
            listProcessClasses.add(IdentNullProcess.class.newInstance());
            //listProcessClasses.add(IsleProcess.class.newInstance());
            listProcessClasses.add(KMeans.class.newInstance());
            listProcessClasses.add(Lines.class.newInstance());
            listProcessClasses.add(Lines3.class.newInstance());
            listProcessClasses.add(Lines4.class.newInstance());
            listProcessClasses.add(Lines5.class.newInstance());
            listProcessClasses.add(Lines5colors.class.newInstance());
            //listProcessClasses.add(Lines6.class.newInstance());
            //listProcessClasses.add(LocalExtremaProcess.class.newInstance());
            listProcessClasses.add(MagnitudeProcess.class.newInstance());
            listProcessClasses.add(ProxyValue.class.newInstance());
            listProcessClasses.add(ProxyValue2.class.newInstance());
            //listProcessClasses.add(RegionLineCorner.class.newInstance());
            listProcessClasses.add(Transform1.class.newInstance());
            listProcessClasses.add(TrueHarrisProcess.class.newInstance());
            listProcessClasses.add(Voronoi.class.newInstance());

            listProcessClasses.forEach(processFile -> {
            }/*comboBox1.addItem(processFile)*/);
            ;

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return listProcessClasses;
    }
}
