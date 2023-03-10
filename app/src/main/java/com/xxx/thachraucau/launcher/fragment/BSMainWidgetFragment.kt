package com.xxx.thachraucau.launcher.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xxx.thachraucau.launcher.AppUtils
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.adapter.WidgetNamesAdapter
import com.xxx.thachraucau.launcher.databinding.BottomSheetMainWidgetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BSMainWidgetFragment() : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMainWidgetBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: WidgetNamesAdapter

    private val mWidgetViewModel: WidgetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetMainWidgetBinding.inflate(inflater, container, false)
        val view = binding.root
        initView()
        return view
    }

    private fun initView() {
        mAdapter = WidgetNamesAdapter()
        binding.rcvListApp.apply {
            adapter = mAdapter
        }

        mWidgetViewModel.mapWidgetInfo.observe(viewLifecycleOwner) { data ->
            mAdapter.setItems(data.keys.toList())
        }

        mAdapter.setOnItemClick(object : WidgetNamesAdapter.ClickItem {
            override fun onClickItem(packageName: String) {
                Log.d(TAG, packageName)
                val bundle = Bundle()
                bundle.putString(AppUtils.WidgetData.WIDGET_PACKAGE_NAME, packageName)
                val bsDetailWidgetFragment = BSDetailWidgetFragment()
                bsDetailWidgetFragment.arguments = bundle
                bsDetailWidgetFragment.show(
                    requireActivity().supportFragmentManager,
                    bsDetailWidgetFragment.tag
                )
            }

        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "BSMainWidgetFragment"
    }
}