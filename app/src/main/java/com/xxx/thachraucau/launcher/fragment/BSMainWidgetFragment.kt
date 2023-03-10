package com.xxx.thachraucau.launcher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.adapter.WidgetNamesAdapter
import com.xxx.thachraucau.launcher.databinding.BottomSheetMainWidgetBinding


class BSMainWidgetFragment(private val listName: List<String>) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMainWidgetBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: WidgetNamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetMainWidgetBinding.inflate(inflater, container, false)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
//        val behavior = BottomSheetBehavior.from(view)
//        behavior.isHideable = false
//        behavior.setBottomSheetCallback(object : BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {}
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//            }
//        })

        mAdapter = WidgetNamesAdapter()
        binding.rcvListApp.apply {
            adapter = mAdapter
        }
        mAdapter.setItems(listName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}