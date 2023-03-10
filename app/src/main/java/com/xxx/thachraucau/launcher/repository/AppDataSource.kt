package com.xxx.thachraucau.launcher.repository

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import com.xxx.thachraucau.launcher.model.AppInfo
import javax.inject.Inject

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
}