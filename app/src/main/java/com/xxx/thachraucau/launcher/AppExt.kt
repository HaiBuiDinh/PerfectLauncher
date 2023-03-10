package com.xxx.thachraucau.launcher

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import com.xxx.thachraucau.launcher.model.LocationInfo

fun Activity.getScreenSize(): LocationInfo {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return LocationInfo().apply {
        mWidth = displayMetrics.widthPixels
        mHeight = displayMetrics.heightPixels
    }
}

fun View.getLocationOnScreen(): LocationInfo {
    val result = IntArray(2)
    this.getLocationOnScreen(result)
    return LocationInfo().apply {
        mX = result[0]
        mY = result[1]
    }
}
