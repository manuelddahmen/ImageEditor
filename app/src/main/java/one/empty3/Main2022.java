/*
 * Copyright (c) 2024.
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

package one.empty3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import one.empty3.androidFeature.DBScanProcess;
import one.empty3.androidFeature.ExtremaProcess;
import one.empty3.androidFeature.GaussFilterProcess;
import one.empty3.androidFeature.GradAddProcess;
import one.empty3.androidFeature.GradMultProcess;
import one.empty3.androidFeature.GradProcess;
import one.empty3.androidFeature.HarrisProcess;
import one.empty3.androidFeature.Histogram0;
import one.empty3.androidFeature.Histogram2;
import one.empty3.androidFeature.Histogram3;
import one.empty3.androidFeature.IdentNullProcess;
import one.empty3.androidFeature.KMeans;
import one.empty3.androidFeature.KMeansRandom;
import one.empty3.androidFeature.LocalExtremaProcess;
import one.empty3.androidFeature.ProxyValue;
import one.empty3.androidFeature.ProxyValue2;
import one.empty3.androidFeature.TrueHarrisProcess;
import one.empty3.androidFeature.histograms.Hist1Votes;
import one.empty3.androidFeature.histograms.Hist4Contour;
import one.empty3.androidFeature.histograms.Hist4Contour2;
import one.empty3.androidFeature.histograms.Hist4Contour3;
import one.empty3.androidFeature.histograms.Hist4Contour4colors;
import one.empty3.androidFeature.histograms.Histogram1;
import one.empty3.io.ProcessFile;

public class Main2022 {
    public static HashMap<String, ProcessFile> listProcessClasses;
    public static ArrayList<String> effects;
    public static HashMap<String, Integer> effectsFactors;

    public static ArrayList<String> indices;
    private static HashMap<String, ProcessFile> listEffects;
    private static File currentFile;
    private static File outputFile;

    public static HashMap<String, ProcessFile> initListProcesses() {
        //if(listProcessClasses==null) {
            listProcessClasses = new HashMap<>();
            indices = new ArrayList<>();
            try {

                //listProcessClasses.put("", CornerDetectProcess.class.newInstance());
                //listProcessClasses.put("", CurveFitting.class.newInstance());
                listProcessClasses.put("DBScanProcess", DBScanProcess.class.newInstance());
//            listProcessClasses.put("DericheFilterProcess", DericheFilterProcess.class.newInstance());
//            listProcessClasses.put("DiffEnergy", DiffEnergy.class.newInstance());
//            listProcessClasses.put("Draw", Draw.class.newInstance());
                listProcessClasses.put("ExtremaProcess", ExtremaProcess.class.newInstance());
                listProcessClasses.put("GaussFilterProcess", GaussFilterProcess.class.newInstance());
                listProcessClasses.put("GradProcess", GradProcess.class.newInstance());
//                listProcessClasses.put("GradAddProcess", GradAddProcess.class.newInstance());
                listProcessClasses.put("GradMultProcess", GradMultProcess.class.newInstance());
                listProcessClasses.put("HarrisProcess", HarrisProcess.class.newInstance());
                listProcessClasses.put("Histogram0", Histogram0.class.newInstance());
                listProcessClasses.put("Histogram1", Histogram1.class.newInstance());
                listProcessClasses.put("Histogram2", Histogram2.class.newInstance());
                listProcessClasses.put("Histogram3", Histogram3.class.newInstance());
                listProcessClasses.put("Hist4Contour", Hist4Contour.class.newInstance());
                listProcessClasses.put("Hist4Contour2", Hist4Contour2.class.newInstance());
                listProcessClasses.put("Hist4Contour3", Hist4Contour3.class.newInstance());
                listProcessClasses.put("Hist1Votes", Hist1Votes.class.newInstance());
                //listProcessClasses.put("", HoughTransformCircle.class.newInstance());
                listProcessClasses.put("IdentNullProcess", IdentNullProcess.class.newInstance());
                //listProcessClasses.put("", IsleProcess.class.newInstance());
                listProcessClasses.put("KMeans", KMeans.class.newInstance());
                listProcessClasses.put("KMeansRandom", KMeansRandom.class.newInstance());
                //listProcessClasses.put("Lines", Lines.class.newInstance());
                //listProcessClasses.put("Lines3", Lines3.class.newInstance());
                //listProcessClasses.put("Lines4", Lines4.class.newInstance());
                //listProcessClasses.put("Lines5", Lines5.class.newInstance());
                //listProcessClasses.put("Lines5colors", Lines5colors.class.newInstance());
                //listProcessClasses.put("Lines6", Lines6.class.newInstance());
                listProcessClasses.put("ProxyValue", ProxyValue.class.newInstance());
                listProcessClasses.put("ProxyValue2", ProxyValue2.class.newInstance());
                //listProcessClasses.put("Transform1", Transform1.class.newInstance());
                //listProcessClasses.put("MagnitudeProcess", MagnitudeProcess.class.newInstance());
                listProcessClasses.put("TrueHarrisProcess", TrueHarrisProcess.class.newInstance());
                listProcessClasses.put("LocalExtremaProcess", LocalExtremaProcess.class.newInstance());
                listProcessClasses.put("Hist4Contour4colors", Hist4Contour4colors.class.newInstance());


            /*
            listProcessClasses.put("Lines7luckyLinesOutline<>", Lines7luckyLinesOutline.class.newInstance());
            listProcessClasses.put("Classification<>", Classification.class.newInstance());
            listProcessClasses.put("HoughTransform<>", HoughTransform.class.newInstance());
            */
                indices.clear();
                listProcessClasses.forEach((s, processFile) -> {
                    indices.add(s);
                });


            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        //}
        return listProcessClasses;
    }

    public static ArrayList<String> listOfEffects() {
        if (effects == null)
            effects = new ArrayList<String>();
        return effects;
    }
    public static Map<String, Integer> listOfFactors() {
        if(effectsFactors==null) {
            effectsFactors = new HashMap<>();
        }
        return effectsFactors;
    }

    public static void setListEffects(@Nullable HashMap<String, ProcessFile> listEffects) {
        Main2022.listEffects = listEffects;
    }

    @NotNull
    public static HashMap<String, ProcessFile> getListEffects() {
        return listEffects;
    }

    public static void setCurrentFile(File currentFile) {
        Main2022.currentFile = currentFile;
    }

    public static File getCurrentFile() {
        return currentFile;
    }

    public static void setOutputFile(File currentOutputFileFinal) {
        Main2022.outputFile = currentOutputFileFinal;
    }

    public static File getOutputFile() {
        return outputFile;
    }
}


