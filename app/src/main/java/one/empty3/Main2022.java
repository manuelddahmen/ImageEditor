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

import java.util.ArrayList;
import java.util.HashMap;

import one.empty3.feature20220726.Classification;
import one.empty3.feature20220726.DBScanProcess;
import one.empty3.feature20220726.ExtremaProcess;
import one.empty3.feature20220726.GaussFilterProcess;
import one.empty3.feature20220726.GradAddProcess;
import one.empty3.feature20220726.GradMultProcess;
import one.empty3.feature20220726.GradProcess;
import one.empty3.feature20220726.HarrisProcess;
import one.empty3.feature20220726.Histogram0;
import one.empty3.feature20220726.Histogram2;
import one.empty3.feature20220726.Histogram3;
import one.empty3.feature20220726.HoughTransform;
import one.empty3.feature20220726.IdentNullProcess;
import one.empty3.feature20220726.KMeans;
import one.empty3.feature20220726.Lines7luckyLinesOutline;
import one.empty3.feature20220726.LocalExtremaProcess;
import one.empty3.feature20220726.ProxyValue;
import one.empty3.feature20220726.ProxyValue2;
import one.empty3.feature20220726.TrueHarrisProcess;
import one.empty3.feature20220726.histograms.Hist4Contour;
import one.empty3.feature20220726.histograms.Hist4Contour2;
import one.empty3.feature20220726.histograms.Histogram1;
import one.empty3.io.ProcessFile;

public class Main2022 {

    public static ArrayList<String> effects;
    public static ArrayList<Double> effectsFactors;

    public static ArrayList<String> indices = new ArrayList();

    public static HashMap<String, ProcessFile> initListProcesses() {
        final HashMap<String, ProcessFile> listProcessClasses = new HashMap<>();
        try {

            listProcessClasses.put("Classification", Classification.class.newInstance());
            //listProcessClasses.put("", CornerDetectProcess.class.newInstance());
            //listProcessClasses.put("", CurveFitting.class.newInstance());
            listProcessClasses.put("DBScanProcess", DBScanProcess.class.newInstance());
//            listProcessClasses.put("DericheFilterProcess", DericheFilterProcess.class.newInstance());
//            listProcessClasses.put("DiffEnergy", DiffEnergy.class.newInstance());
//            listProcessClasses.put("Draw", Draw.class.newInstance());
            listProcessClasses.put("ExtremaProcess", ExtremaProcess.class.newInstance());
            listProcessClasses.put("GaussFilterProcess", GaussFilterProcess.class.newInstance());
            listProcessClasses.put("GradProcess", GradProcess.class.newInstance());
            listProcessClasses.put("GradAddProcess", GradAddProcess.class.newInstance());
            listProcessClasses.put("GradMultProcess", GradMultProcess.class.newInstance());
            listProcessClasses.put("HarrisProcess", HarrisProcess.class.newInstance());
            listProcessClasses.put("Histogram0", Histogram0.class.newInstance());
            listProcessClasses.put("Histogram1", Histogram1.class.newInstance());
            listProcessClasses.put("Histogram2", Histogram2.class.newInstance());
            listProcessClasses.put("Histogram3", Histogram3.class.newInstance());
            listProcessClasses.put("Hist4Contour", Hist4Contour.class.newInstance());
            listProcessClasses.put("Hist4Contour2", Hist4Contour2.class.newInstance());
            listProcessClasses.put("HoughTransform", HoughTransform.class.newInstance());
            //listProcessClasses.put("", HoughTransformCircle.class.newInstance());
            listProcessClasses.put("IdentNullProcess", IdentNullProcess.class.newInstance());
            //listProcessClasses.put("", IsleProcess.class.newInstance());
            listProcessClasses.put("KMeans", KMeans.class.newInstance());
            //listProcessClasses.put("Lines", Lines.class.newInstance());
            //listProcessClasses.put("Lines3", Lines3.class.newInstance());
            //listProcessClasses.put("Lines4", Lines4.class.newInstance());
            //listProcessClasses.put("Lines5", Lines5.class.newInstance());
            //listProcessClasses.put("Lines5colors", Lines5colors.class.newInstance());
            //listProcessClasses.put("Lines6", Lines6.class.newInstance());
            listProcessClasses.put("Lines7luckyLinesOutline", Lines7luckyLinesOutline.class.newInstance());
            listProcessClasses.put("ProxyValue", ProxyValue.class.newInstance());
            listProcessClasses.put("ProxyValue2", ProxyValue2.class.newInstance());
            //listProcessClasses.put("Transform1", Transform1.class.newInstance());
            //listProcessClasses.put("MagnitudeProcess", MagnitudeProcess.class.newInstance());
            listProcessClasses.put("TrueHarrisProcess", TrueHarrisProcess.class.newInstance());
            listProcessClasses.put("LocalExtremaProcess", LocalExtremaProcess.class.newInstance());


            indices.clear();
            listProcessClasses.forEach((s, processFile) -> {
                indices.add(s);
            });


        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return listProcessClasses;
    }

    public ArrayList<String> listOfEffects() {
        if (effects == null)
            effects = new ArrayList<String>();
        return effects;
    }
    public ArrayList<Double> listOfFactors() {
        if (effectsFactors == null)
            effectsFactors = new ArrayList<Double>();
        return effectsFactors;
    }

}


