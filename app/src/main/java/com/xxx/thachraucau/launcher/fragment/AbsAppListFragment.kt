package com.xxx.thachraucau.launcher.fragment

import com.xxx.thachraucau.launcher.model.AppInfo

abstract class AbsAppListFragment: AbsFragment() {

    abstract fun onPageChanged(selectedPage: Int)
    abstract fun dropHotSeatFull(appInfo: AppInfo)
}