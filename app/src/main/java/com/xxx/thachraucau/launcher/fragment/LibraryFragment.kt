package com.xxx.thachraucau.launcher.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.adapter.LibraryAdapter
import com.xxx.thachraucau.launcher.controller.AppLibraryController
import com.xxx.thachraucau.launcher.databinding.FragmentLibraryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : AbsFragment() {

    val mController by viewModels<AppLibraryController>()

    lateinit var mBinding: FragmentLibraryBinding

    override fun getLayoutIds() = R.layout.fragment_library

    override fun initView(rootView: View) {
        mBinding = FragmentLibraryBinding.bind(rootView)
        mController.mListData.observe(viewLifecycleOwner) {
            mBinding.rcLibrary.apply {
                layoutManager = GridLayoutManager(this@LibraryFragment.requireContext(), 2)
                adapter = LibraryAdapter(requireActivity()).apply {
                    setListItems(it)
                }
            }
        }
    }
}