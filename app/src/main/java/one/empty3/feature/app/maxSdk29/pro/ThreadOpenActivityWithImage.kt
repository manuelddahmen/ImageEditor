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

package one.empty3.feature.app.maxSdk29.pro

import android.app.Activity
import java.io.File

class ThreadOpenActivityWithImage : Runnable {

    protected lateinit var activityToLaunch: Class<Activity>
    protected lateinit var inputFile: File
    protected lateinit var outputFile: File

    fun params(inputFile: File, outputFile: File, activityToLaunch: Class<Activity>) {
        this.inputFile = inputFile
        this.outputFile = outputFile
        this.activityToLaunch = activityToLaunch
    }

    override fun run() {
    }
}