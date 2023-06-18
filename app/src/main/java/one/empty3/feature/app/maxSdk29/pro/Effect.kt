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

package one.empty3.feature.app.maxSdk29.pro

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import one.empty3.library.Representable
import one.empty3.library.StructureMatrix
import java.io.Serializable
import java.util.ArrayList

class Effect() : Parcelable, Serializable {
    lateinit var classname: String
    lateinit var parameters: Representable
    lateinit var preview: Bitmap;

    constructor(parcel: Parcel) : this() {
        classname = parcel.readString().toString()
        var parametersRead0: ArrayList<String>? = parcel.createStringArrayList()

        if (parametersRead0 != null)
            parametersRead0.forEach {
                var str: String = it
                val representable = Representable()
                if (str != null) {
                    val split = str.split("=")
                    val sm: StructureMatrix<Any> = StructureMatrix<Any>(0, String.javaClass)
                    sm.add(split[1])
                    representable.getDeclaredDataStructure().put(split[0], sm)
                    parameters.getDeclaredDataStructure().put(split[0], sm)
                }
            }
    }
    /*
        fun previewEffect(fileJpg : File ) {
            var forName  = Class.forName(classname)
            var process : ProcessFile = forName as? Class extends ProcessFile
            process.setMaxRes(200)
            process.process(fileJpg, fileJpg2)
            val read = ImageIO.read(fileJpg2)
            this.preview = read
        }
    */


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(classname)
        var i: Int
        val representable = Representable()

        parameters.getDeclaredDataStructure().keys.forEach {
            val get: StructureMatrix<Any> = parameters.getDeclaredDataStructure().get(it) as StructureMatrix<Any>

            parcel.writeString(it + "=" + get.toString())
        }

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Effect> {
        override fun createFromParcel(parcel: Parcel): Effect {
            val effect = Effect(parcel)
            //effect.preview = effect.preview
            return effect

        }

        override fun newArray(size: Int): Array<Effect?> {
            return arrayOfNulls(size)
        }
    }


}
