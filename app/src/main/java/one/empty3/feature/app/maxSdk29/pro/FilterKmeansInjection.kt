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

import one.empty3.feature20220726.KMeans
import one.empty3.feature20220726.kmeans.K_Clusterer
import one.empty3.feature20220726.kmeans.MakeDataset
import one.empty3.io.ProcessFile
import java.io.File

public open class FilterKMeansInjection : ProcessFile() {
    protected var kClusterer: K_Clusterer? = null
    fun process0(fileIn: File, fileOut: File): Boolean {
        return if (!fileIn.absolutePath.endsWith("jpg")) false else try {
            MakeDataset(fileIn, File(fileOut.absolutePath + ".csv"), maxRes)
            kClusterer = K_Clusterer()
            kClusterer!!.process(fileIn, File(fileOut.absolutePath + ".csv"), fileOut, maxRes)
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
        // init centroids with random colored
        // points.
    }

    @Override
    override fun process(fileIn: File, fileOut: File): Boolean {
        return process0(fileIn, fileOut)
    }
}