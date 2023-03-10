package com.xxx.thachraucau.launcher

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object PackageMgr {
    private const val TAG = "PackageMgr"

    fun getAppName(context: Context, packageName: String) =
        PackageMgr.getAppName(context.packageManager, packageName)

    fun getAppName(pm: PackageManager, packageName: String): String {
        return try {
            val info = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(info).toString()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get Appname: $e")
            getDefaultAppName(packageName)
        } catch (e: Error) {
            Log.e(TAG, "Failed to get Appname: $e")
            getDefaultAppName(packageName)
        }
    }

    private fun getDefaultAppName(packageName: String): String {
        val index = packageName.lastIndexOf(".")
        return if (index >= 0) packageName.substring(index + 1) else packageName
    }

    fun getAppIcon(context: Context, packageName: String): Drawable? {
        val packageManager = context.packageManager
        return try {
            val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            packageManager.getApplicationIcon(info)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load icon: $e")
            null
        }
    }
}