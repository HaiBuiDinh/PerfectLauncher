package com.xxx.thachraucau.launcher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xxx.thachraucau.launcher.model.AppInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class AbsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutIds(), container, false)
        initView(view)
        return view
    }

    abstract fun getLayoutIds(): Int
    abstract fun initView(rootView: View)
}