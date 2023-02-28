package com.xxx.thachraucau.launcher.fragment
import androidx.fragment.app.Fragment
import com.xxx.thachraucau.launcher.model.AppInfo

abstract class AbsFragment: Fragment() {
    abstract fun onPageChanged(selectedPage: Int)
    abstract fun dropHotSeatFull(appInfo: AppInfo)
}