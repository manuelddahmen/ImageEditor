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

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.vertexai.FirebaseVertexAI;
import com.google.firebase.vertexai.GenerativeModel;

public class FeatureAppApplication extends Application implements Configuration.Provider {

    @NonNull
    public Configuration getWorkManagerConfiguration() {
        Configuration configuration = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.VERBOSE)
                .build();
        return configuration;
    }

    private int maxRes = R.string.maxRes_1200;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());
        // Initialize the Vertex AI service and the generative model
// Specify a model that supports your use case
// Gemini 1.5 Pro is versatile and can accept both text-only and multimodal prompt inputs
        //GenerativeModel generativeModel = FirebaseVertexAI.getInstance().generativeModel("gemini-1.5-pro-preview-0409");
    }

    public int getMaxRes() {
        return maxRes;
    }

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }
}
