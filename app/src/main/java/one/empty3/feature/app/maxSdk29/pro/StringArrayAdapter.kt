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

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import one.empty3.apps.tree.altree.functions.ListMathDoubleFunction

class StringArrayAdapter() : RecyclerView.Adapter<StringArrayAdapter.ViewHolder>(), Parcelable {
    private var mathList: Array<String> = ListMathDoubleFunction.getList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //val item = itemView.findViewById(R.id.icon) as Icon
        val textView = itemView.findViewById(R.id.text_view_recyclerview_function) as TextView
        var name: String = textView.text.toString()
    }

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val viewItem = from.inflate(R.layout.item_view, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val function = mathList[position]
        holder.name = function
    }

    override fun getItemCount(): Int {
        return mathList.size
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<StringArrayAdapter> {
        override fun createFromParcel(parcel: Parcel): StringArrayAdapter {
            return StringArrayAdapter(parcel)
        }

        override fun newArray(size: Int): Array<StringArrayAdapter?> {
            return arrayOfNulls(size)
        }
    }


}