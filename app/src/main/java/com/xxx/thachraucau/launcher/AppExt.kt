package com.xxx.thachraucau.launcher

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import androidx.fragment.app.Fragment
import com.xxx.thachraucau.launcher.model.LocationInfo

val sLoggerMap = HashMap<String, Logger>()

fun Activity.getScreenSize(): LocationInfo {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return LocationInfo().apply {
        mWidth = displayMetrics.widthPixels
        mHeight = displayMetrics.heightPixels
    }
}

fun Fragment.getScreenSize(): LocationInfo = requireActivity().getScreenSize()

fun View.getLocationOnScreen(): LocationInfo {
    val result = IntArray(2)
    this.getLocationOnScreen(result)
    return LocationInfo().apply {
        mX = result[0]
        mY = result[1]
    }
}

fun Any.getLogger(): Logger {
    val name = this.javaClass.simpleName
    val result = sLoggerMap.getOrDefault(name, Logger(name))
    sLoggerMap.put(name, result)
    return result
}
