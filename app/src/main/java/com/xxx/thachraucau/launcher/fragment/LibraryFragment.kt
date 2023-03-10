package com.xxx.thachraucau.launcher.fragment

import android.view.View
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.databinding.FragmentLibraryBinding

class LibraryFragment : AbsFragment() {

    lateinit var mBinding: FragmentLibraryBinding

    override fun getLayoutIds() = R.layout.fragment_library

    override fun initView(rootView: View) {
        mBinding = FragmentLibraryBinding.bind(rootView)
    }
}