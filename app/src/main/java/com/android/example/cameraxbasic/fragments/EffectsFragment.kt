package com.android.example.cameraxbasic.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.example.cameraxbasic.R
import one.empty3.Main
import one.empty3.io.ProcessFile

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EffectsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EffectsFragment : AppCompatActivity() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var  effectList : ArrayList<ProcessFile>
    lateinit Var auto : AutoCompleteTextView
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val action: String? = intent?.action
        //val data: Uri? = intent?.data
// url scheme.? file? or https://empty3.one 
// settings.properties file. too massive
        setContentView(R.layout.select_effects)
        effectList = Main.initListProcesses()
        val l : List<String> = List<String>(effectList.size, init = {
            effectList[it].toString()
        })
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(this,
            android.R.layout.select_dialog_item, l)
        auto = findViewById(R.id.effectsAutoCompleteTextView)
        auto.setThreshold(1)
        auto.setAdapter(adapter)

    }

@Override
public void onSaveInstanceState(Bundle outState) {
outState.putString("classname", auto.text);
super.onSaveInstanceState(outState);
}

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState)
if (savedInstanceState != null) {
    String autoStr= savedInstanceState.getString("classname");
    auto.text = autoStr
}
}
public boolean process() {
//BitmapFactory.decodeFile(filePath)
} 
}
