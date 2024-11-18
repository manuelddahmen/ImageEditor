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

package one.empty3.feature.app.maxSdk29.pro;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import one.empty3.androidFeature.GoogleFaceDetection;

public class AppData {
    private static final String[] cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    private String[] cordsValues = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};

    private File currentBitmap;
    private File currentFile;

    public AppData() {
    }

    private GoogleFaceDetection googleFaceDetection = GoogleFaceDetection.getInstance(false, null);

    private void putExtra(AppCompatActivity activity, Intent calculatorIntent, String cord) {
    }

    public String[] getCordsValues() {
        return cordsValues;
    }

    public void setCordsValues(String[] cordsValues) {
        this.cordsValues = cordsValues;
    }

    public GoogleFaceDetection getGoogleFaceDetection() {
        return googleFaceDetection;
    }

    public void setGoogleFaceDetection(GoogleFaceDetection googleFaceDetection) {
        this.googleFaceDetection = googleFaceDetection;
    }
}
