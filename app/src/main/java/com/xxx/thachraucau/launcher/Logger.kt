package com.xxx.thachraucau.launcher

import android.util.Log

class Logger(val className : String) {
    val PREFIX = "long.vt "
    val TAG = "$PREFIX$className: "
    fun d(message: String){
        Log.d(TAG, message)
    }
}