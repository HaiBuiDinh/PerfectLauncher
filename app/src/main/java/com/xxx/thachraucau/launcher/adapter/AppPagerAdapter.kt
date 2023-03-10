package com.xxx.thachraucau.launcher.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.xxx.thachraucau.launcher.manager.GridManager
import com.xxx.thachraucau.launcher.model.AppInfo
import com.xxx.thachraucau.launcher.Logger
import com.xxx.thachraucau.launcher.fragment.AbsAppListFragment
import com.xxx.thachraucau.launcher.fragment.AppListFragment

class AppPagerAdapter(fragmentManager: FragmentManager, val mListItem: List<AppInfo>) :
    FragmentStatePagerAdapter(fragmentManager) {

    val mLogger by lazy {
        Logger(this.javaClass.simpleName)
    }

    val mCurrentGrid by lazy {
        GridManager.getInstance().currentGrid
    }

    val mListFragments by lazy {
        ArrayList<AbsAppListFragment>()
    }

    init {
        mLogger.d("size item = ${mListItem.size}")
    }

    fun onPageChanged(position: Int) {
        mListFragments.forEach { it.onPageChanged(position) }
    }

    fun dropHotSeatFull(appInfo: AppInfo) {
        mListFragments.forEach { it.dropHotSeatFull(appInfo) }
    }

    override fun getCount() =
        mListItem.size / mCurrentGrid.getCount() + if (mListItem.size % mCurrentGrid.getCount() == 0) 0 else 1

    override fun getItem(position: Int): Fragment {
        var fragment: AbsAppListFragment? = null
        for (index in 0..count) {
            if (index == position) {
                fragment = AppListFragment().apply {
                    mPageIndex = index
                    val startIndex = position * this@AppPagerAdapter.mCurrentGrid.getCount()
                    val remain = mListItem.size - startIndex - 1
                    val endIndex =
                        if (remain >= this@AppPagerAdapter.mCurrentGrid.getCount()) startIndex + this@AppPagerAdapter.mCurrentGrid.getCount() else startIndex + remain + 1
                    mListAppInfo = mListItem.subList(startIndex, endIndex).toMutableList()
                }
                break
            }
        }
        mListFragments.add(fragment!!)
        return fragment
    }
}