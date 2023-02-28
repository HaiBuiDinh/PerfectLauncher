package com.xxx.thachraucau.launcher.controller

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.lifecycle.MutableLiveData
import com.xxx.thachraucau.launcher.model.AppInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainController(val mContext: Context) {
    val liveData: MutableLiveData<List<AppInfo>> = MutableLiveData()

    init {
        liveData.value = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            var appsList = ArrayList<AppInfo>()
            val i = Intent(Intent.ACTION_MAIN, null)
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            val availableActivities: List<ResolveInfo> =
                mContext.packageManager.queryIntentActivities(i, 0)
            for (ri in availableActivities) {
                if(ri.activityInfo.packageName == mContext.packageName) continue
                val appInfo = AppInfo(
                    ri.loadLabel(mContext.packageManager) as String,
                    ri.activityInfo.packageName,
                    ri.activityInfo.loadIcon(mContext.packageManager),
                )
                appsList.add(appInfo)
            }
            withContext(Dispatchers.Main) {
                liveData.value = appsList
            }
        }
    }
}