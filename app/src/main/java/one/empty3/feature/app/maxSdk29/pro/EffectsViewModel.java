/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package one.empty3.feature.app.maxSdk29.pro;

import static one.empty3.feature.app.maxSdk29.pro.Constants.IMAGE_MANIPULATION_WORK_NAME;
import static one.empty3.feature.app.maxSdk29.pro.Constants.KEY_IMAGE_URI;
import static one.empty3.feature.app.maxSdk29.pro.Constants.TAG_OUTPUT;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import one.empty3.feature.app.maxSdk29.pro.workers.EffectWorker;
import one.empty3.feature.app.maxSdk29.pro.workers.SaveImageToFileWorker;
import one.empty3.io.ProcessFile;

public class EffectsViewModel extends ViewModel {

    private Uri mImageUri;
    private WorkManager mWorkManager;
    private LiveData<List<WorkInfo>> mSavedWorkInfo;
    private Uri mOutputUri;
    private File currentFile;
    private HashMap<String, ProcessFile> listEffects;

    // BlurViewModel constructor
    public EffectsViewModel(@NonNull Application application) {
        super();
        mWorkManager = WorkManager.getInstance(application);

        mImageUri = getImageUri(application.getApplicationContext());

        // This transformation makes sure that whenever the current work Id changes the WorkInfo
        // the UI is listening to changes
        mSavedWorkInfo = mWorkManager.getWorkInfosByTagLiveData(TAG_OUTPUT);
    }

    /**
     * Getter method for mSavedWorkInfo
     */
    LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return mSavedWorkInfo;
    }

    /**
     * Setter method for mOutputUri
     */
    void setOutputUri(String outputImageUri) {
        mOutputUri = uriOrNull(outputImageUri);
    }

    /**
     * Getter method for mOutputUri
     */
    Uri getOutputUri() {
        return mOutputUri;
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     */
    void applyEffect(File currentFile) {
        this.currentFile = currentFile;
        // Add WorkRequest to Cleanup temporary images
        WorkContinuation continuation = mWorkManager
                .beginUniqueWork(IMAGE_MANIPULATION_WORK_NAME,
                        ExistingWorkPolicy.REPLACE,
                        OneTimeWorkRequest.from(EffectWorker.class));

        // Add WorkRequests to blur the image the number of times requested
            OneTimeWorkRequest.Builder effectsBuilder =
                    new OneTimeWorkRequest.Builder(EffectWorker.class);

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
        effectsBuilder.setInputData(createInputDataForUri());



        continuation = continuation.then(effectsBuilder.build());

        // Create charging constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        // Add WorkRequest to save the image to the filesystem
        OneTimeWorkRequest save = new OneTimeWorkRequest.Builder(SaveImageToFileWorker.class)
                .setConstraints(constraints) // This adds the Constraints
                .addTag(TAG_OUTPUT) // This adds the tag
                .build();

        continuation = continuation.then(save);

        // Actually start the work
        continuation.enqueue();
    }

    /**
     * Cancel work using the work's unique name
     */
    void cancelWork() {
        mWorkManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME);
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     *
     * @return Data which contains the Image Uri as a String
     */
    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        if (currentFile != null) {
            builder.putString(KEY_IMAGE_URI, currentFile.getAbsolutePath());
        }
        return builder.build();
    }

    private Uri uriOrNull(String uriString) {
        if (!TextUtils.isEmpty(uriString)) {
            return Uri.parse(uriString);
        }
        return null;
    }

    /**
     * Setters
     */
    void setImageUri(String uri) {
        mImageUri = uriOrNull(uri);
    }

    private Uri getImageUri(Context context) {
        Resources resources = context.getResources();

        Uri imageUri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.android_cupcake))
                .appendPath(resources.getResourceTypeName(R.drawable.android_cupcake))
                .appendPath(resources.getResourceEntryName(R.drawable.android_cupcake))
                .build();

        return imageUri;
    }

    public void setListEffects(@Nullable HashMap<String, ProcessFile> listEffects) {
        this.listEffects = listEffects;
    }
}