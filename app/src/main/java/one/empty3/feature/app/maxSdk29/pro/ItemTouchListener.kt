/*
 * Copyright (c) 2023.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ItemTouchListener : RecyclerView.OnItemTouchListener {
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

}
