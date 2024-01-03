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

package one.empty3.feature.app.maxSdk29.pro.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SaveImageToFileWorker extends Worker {
    public SaveImageToFileWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private static final String TAG = SaveImageToFileWorker.class.getSimpleName();

    private static final String TITLE = "Effects applied image";
    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault());

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();

        // Makes a notification when the work starts and slows down the work so that it's easier to
        // see each WorkRequest start, even on emulated devices
        WorkerUtils.makeStatusNotification("Saving image", applicationContext);
        WorkerUtils.sleep();

        Data outputData = new Data.Builder().build();

        return Result.success(outputData);

    }
}
