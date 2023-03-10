package com.xxx.thachraucau.launcher.fragment

import android.appwidget.AppWidgetProviderInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xxx.thachraucau.launcher.AppUtils
import com.xxx.thachraucau.launcher.PackageMgr
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.adapter.WidgetDetailAdapter
import com.xxx.thachraucau.launcher.databinding.BottomSheetDetailWidgetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BSDetailWidgetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDetailWidgetBinding? = null
    private val binding get() = _binding!!

    private val mWidgetViewModel: WidgetViewModel by activityViewModels()

    private var mArgs: Bundle? = null
    private var mPackagerName: String? = ""

    private val mAdapter: WidgetDetailAdapter by lazy { WidgetDetailAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mArgs = arguments
        mPackagerName = mArgs?.getString(AppUtils.WidgetData.WIDGET_PACKAGE_NAME)

        _binding = BottomSheetDetailWidgetBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        return view
    }

    private fun init() {
        mPackagerName?.let {
            binding.tvAppName.text = PackageMgr.getAppName(requireContext(), it)
            binding.appIcon.setImageDrawable(PackageMgr.getAppIcon(requireContext(), it))
        }

        binding.rcvDetailWidget.apply {
            adapter = mAdapter
        }

        mWidgetViewModel.mapWidgetInfo.value.let {
            val listInfo: List<AppWidgetProviderInfo>? = it!!.get(mPackagerName)
            Log.d(TAG, "Size = ${listInfo?.size.toString()}")
            mAdapter.setData(listInfo!!)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), mPackagerName, Toast.LENGTH_SHORT).show()
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
        const val TAG = "BSDetailWidgetFragment"
    }
}