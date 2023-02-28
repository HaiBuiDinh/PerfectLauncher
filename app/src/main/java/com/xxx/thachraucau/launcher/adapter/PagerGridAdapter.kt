package com.xxx.thachraucau.launcher.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.xxx.thachraucau.launcher.GridManager
import com.xxx.thachraucau.launcher.model.AppInfo
import com.xxx.thachraucau.launcher.Logger
import com.xxx.thachraucau.launcher.fragment.AbsFragment
import com.xxx.thachraucau.launcher.fragment.AppListGridFragment

class PagerGridAdapter(fragmentManager: FragmentManager, val mListItem: List<AppInfo>) :
    FragmentStatePagerAdapter(fragmentManager) {

    val logger by lazy {
        Logger(this.javaClass.simpleName)
    }

    val currentGird by lazy {
        GridManager.getInstance().currentGrid
    }

    val fragmentList by lazy {
        ArrayList<AbsFragment>()
    }

    init {
        logger.d("size item = ${mListItem.size}")
    }

    fun onPageChanged(position: Int){
        fragmentList.forEach { it.onPageChanged(position) }
    }

    fun dropHotSeatFull(appInfo: AppInfo){
        fragmentList.forEach { it.dropHotSeatFull(appInfo) }
    }

    override fun getCount() =
        mListItem.size / currentGird.getCount() + if (mListItem.size % currentGird.getCount() == 0) 0 else 1

    override fun getItem(position: Int): Fragment {
        var fragment: AbsFragment? = null
        for (index in 0..count) {
            if (index == position) {
                fragment = AppListGridFragment().apply {
                    mPageIndex = index
                    val startIndex = position * currentGird.getCount()
                    val remain = mListItem.size - startIndex - 1
                    val endIndex =
                        if (remain >= currentGird.getCount()) startIndex + currentGird.getCount() else startIndex + remain + 1
                    mListAppInfo = mListItem.subList(startIndex, endIndex).toMutableList()
                }
                break
            }
        }
        fragmentList.add(fragment!!)
        return fragment
    }
}