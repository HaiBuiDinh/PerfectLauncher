package com.xxx.thachraucau.launcher.repository

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import com.xxx.thachraucau.launcher.model.AppInfo
import com.xxx.thachraucau.launcher.model.LibraryInfo
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

class AppDataSource @Inject constructor(val mContext: Context) {
    fun getListApp(): List<AppInfo> {
        val appsList = ArrayList<AppInfo>()
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val availableActivities: List<ResolveInfo> =
            mContext.packageManager.queryIntentActivities(i, 0)
        for (ri in availableActivities) {
            if (ri.activityInfo.packageName == mContext.packageName) continue
            val appInfo = AppInfo(
                ri.loadLabel(mContext.packageManager) as String,
                ri.activityInfo.packageName,
                ri.activityInfo.loadIcon(mContext.packageManager),
            )
            appsList.add(appInfo)
        }
        return appsList
    }


    // fake tam data
    fun getListLibraryInfo(): ArrayList<LibraryInfo> {
        val result = ArrayList<LibraryInfo>()
        val allApp = getListApp()
        var target = Random.nextInt(IntRange(2, 9))
        var listTemp = mutableListOf<AppInfo>()
        for (info in allApp) {
            if (listTemp.size == target) {
                result.add(LibraryInfo("test").apply {
                    mListApp.addAll(listTemp)
                })
                listTemp = mutableListOf()
                target = Random.nextInt(IntRange(2, 9))
                continue
            }
            listTemp.add(info)
        }
        if (listTemp.isNotEmpty()) {
            result.add(LibraryInfo("test").apply {
                mListApp.addAll(listTemp)
            })
        }
        return result
    }
}