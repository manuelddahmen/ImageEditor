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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val TAG = "one.empty3.feature.app.maxSdk29.pro.MyCameraActivity"
private const val directory_file_history = "/Android/data/$TAG/fileDataHistory.txt"

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "fileHistoryImages"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentNavigation.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentNavigation : Fragment() {
    // TODO: Rename and change types of parameters
    private var fileHistoryImages: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fileHistoryImages = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param fileHistoryImages Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_navigation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(fileHistoryImages: String, param2: String) =
            FragmentNavigation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, fileHistoryImages)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}