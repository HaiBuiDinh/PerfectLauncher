package com.xxx.thachraucau.launcher.fragment

import android.view.View
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.databinding.FragmentUtilitiesBinding

class UtilitiesFragment : AbsFragment() {

    lateinit var mBinding: FragmentUtilitiesBinding
    override fun getLayoutIds() = R.layout.fragment_utilities

    override fun initView(rootView: View) {
        mBinding = FragmentUtilitiesBinding.bind(rootView)
    }
}