package com.xxx.thachraucau.launcher.model

import android.graphics.drawable.Drawable

class AppInfo(var name: String, var packageName: String, var icon: Drawable) :
    AbsInfo(InfoType.APP) {
    override fun toString(): String {
        return "(name = $name, packageName = $packageName)"
    }
}