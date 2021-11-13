package com.android.example.app.model;

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import one.empty3.io.ProcessFile

public class ImageItemModel : ViewModel() {
        private val mutableSelectedItem = MutableLiveData<ClipData.Item>()
        val selectedItem: LiveData<ClipData.Item> get() = mutableSelectedItem

        fun selectItem(item: ClipData.Item) {
            mutableSelectedItem.value = item
        }

    fun selectItem(item: ImageItemModel) {
        TODO("Not yet implemented")
    }
    fun setEffect(effects : List<ProcessFile> ) {

    }
    fun applyEffect() {

    }
}

