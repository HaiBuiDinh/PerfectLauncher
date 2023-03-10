package com.xxx.thachraucau.launcher.fragment

import android.content.ClipData
import android.view.DragEvent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.adapter.AppPagerAdapter
import com.xxx.thachraucau.launcher.controller.AppController
import com.xxx.thachraucau.launcher.databinding.CustomAppListBinding
import com.xxx.thachraucau.launcher.databinding.FragmentAllAppBinding
import com.xxx.thachraucau.launcher.getLocationOnScreen
import com.xxx.thachraucau.launcher.getLogger
import com.xxx.thachraucau.launcher.getScreenSize
import com.xxx.thachraucau.launcher.manager.GridManager
import com.xxx.thachraucau.launcher.model.AppInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllAppFragment : AbsFragment() {

    lateinit var mBinding: FragmentAllAppBinding

    val mController by viewModels<AppController>()

    val mScreenSize by lazy {
        getScreenSize()
    }

    val mCurrentGrid by lazy {
        GridManager.getInstance().currentGrid
    }

    override fun getLayoutIds() = R.layout.fragment_all_app

    override fun initView(rootView: View) {
        mBinding = FragmentAllAppBinding.bind(rootView)
        mBinding.viewpagerGrid.layoutParams.apply {
            height = mScreenSize.mHeight * 85 / 100
        }

        mBinding.hotseat.layoutParams.apply {
            height = mScreenSize.mHeight * 15 / 100
        }

        mBinding.hotseat.setOnDragListener(object : View.OnDragListener {
            override fun onDrag(v: View?, event: DragEvent?): Boolean {
                when (event?.action) {
                    DragEvent.ACTION_DROP -> {
                        val dragView = event.localState as View?
                        val tagInfo = dragView?.getTag(R.string.app_name) as AppInfo?
                        getLogger().d("ACTION_DROP childcount = ${mBinding.hotseat.childCount} tagInfo = $tagInfo")
                        tagInfo?.let {
                            if (mBinding.hotseat.childCount < 4) {
                                mBinding.hotseat.addView(getViewToAdd(tagInfo))
                            } else {
                                (mBinding.viewpagerGrid.adapter as AppPagerAdapter).dropHotSeatFull(
                                    it
                                )
                            }
                        }
                    }
                    DragEvent.ACTION_DRAG_ENTERED -> {
                    }
                    DragEvent.ACTION_DRAG_ENDED -> {
                    }
                    DragEvent.ACTION_DRAG_EXITED -> {
                    }
                    DragEvent.ACTION_DRAG_STARTED -> {
                    }
                    DragEvent.ACTION_DRAG_LOCATION -> {
                    }
                }
                return true
            }
        })

        mController.listData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val adapter = AppPagerAdapter(requireFragmentManager(), it)
                mBinding.viewpagerGrid.apply {
                    this.adapter = adapter
                    offscreenPageLimit = adapter.count
                }
                adapter.onPageChanged(0)
//                đổi page
//                viewpager_grid. ()
                mBinding.viewpagerGrid.addOnPageChangeListener(object :
                    ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        getLogger().d("onPageSelected position = $position")
                        adapter.onPageChanged(position)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }
                })

                mBinding.viewpagerGrid.setPageTransformer(false, object :
                    ViewPager.PageTransformer {
                    override fun transformPage(page: View, position: Float) {
//                        if (currentIndex == 0) {
//                            if (position < -1) {    // [-Infinity,-1)
//                                // This page is way off-screen to the left.
//                                page.setAlpha(0f)
//                            } else if (position <= 0) {
//                                page.setAlpha(1f)
//                                page.setTranslationX(0f)
//                                page.setScaleX(1f)
//                                page.setScaleY(1f)// [-1,0]
//                            } else if (position <= 1) {    // (0,1]
//                                page.setTranslationX(-position * page.getWidth())
//                                page.setAlpha(1 - Math.abs(position))
//                                page.setScaleX(1 - Math.abs(position))
//                                page.setScaleY(1 - Math.abs(position))
//                            } else {    // (1,+Infinity]
//                                // This page is way off-screen to the right.
//                                page.setAlpha(0f)
//                            }
//                        } else if (currentIndex == total - 1) {
//                            if (position < -1) {    // [-Infinity,-1)
//                                // This page is way off-screen to the left.
//                                page.setAlpha(0f)
//                            } else if (position <= 0) {    // [-1,0]
//                                page.setTranslationX(-position * page.getWidth())
//                                page.setAlpha(1 - Math.abs(position))
//                                page.setScaleX(1 - Math.abs(position))
//                                page.setScaleY(1 - Math.abs(position))
//                            } else if (position <= 1) {    // (0,1]
//                                page.setAlpha(1f)
//                                page.setTranslationX(0f)
//                                page.setScaleX(1f)
//                                page.setScaleY(1f)
//                            } else {    // (1,+Infinity]
//                                // This page is way off-screen to the right.
//                                page.setAlpha(0f)
//                            }
//                        }
//                        if (position < -1) {    // [-Infinity,-1)
//                            // This page is way off-screen to the left.
//                            page.setAlpha(0f)
//                        } else if (position <= 0) {    // [-1,0]
//                            page.setAlpha(1f)
//                            page.setTranslationX(0f)
//                            page.setScaleX(1f)
//                            page.setScaleY(1f)
//                        } else if (position <= 1) {    // (0,1]
//                            page.setTranslationX(-position * page.getWidth())
//                            page.setAlpha(1 - Math.abs(position))
//                            page.setScaleX(1 - Math.abs(position))
//                            page.setScaleY(1 - Math.abs(position))
//
//                        } else {    // (1,+Infinity]
//                            // This page is way off-screen to the right.
//                            page.setAlpha(0f)
//                        }
                    }
                })
            }
        }
    }

    fun getViewToAdd(appInfo: AppInfo): View {
        val view =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.custom_app_list, mBinding.hotseat, false)
        val binding: CustomAppListBinding = CustomAppListBinding.bind(view)
        appInfo.apply {
            binding.tvCustom.text = name
            binding.imCustom.setImageDrawable(icon)
        }
        view.setTag(R.string.app_name, appInfo)
        view.setOnLongClickListener {
            val data = ClipData.newPlainText("", "test")
            val shadowBuilder = View.DragShadowBuilder(view)
            view.startDragAndDrop(data, shadowBuilder, view, 0)
            mBinding.hotseat.removeView(it)
            true
        }
        val param = GridLayout.LayoutParams().apply {
            height = (mScreenSize.mHeight * 85 / 100) / mCurrentGrid.mRow
            width = mScreenSize.mWidth / mCurrentGrid.mCol
//        param.rightMargin = 5
//        param.topMargin = 5
            setGravity(Gravity.CENTER)
            // xử lý resize
//        if (position == 8) {
//            param.setGravity(Gravity.FILL_HORIZONTAL or Gravity.FILL_VERTICAL)
//            param.columnSpec = GridLayout.spec(0, 2)
//            param.rowSpec = GridLayout.spec(2, 2)
//            param.height *= 2
//            param.width *= 2
//        }
        }

        val imageParam = binding.imCustom.layoutParams
        imageParam.height = param.width * 3 / 5
        imageParam.width = param.width * 3 / 5
        binding.imCustom.layoutParams = imageParam


        view.layoutParams = param
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                val tagApp = v!!.getTag(R.string.app_name) as AppInfo
//                view.removeOnLayoutChangeListener(this)
                val location = view.getLocationOnScreen()
                tagApp.mX = location.mX
                tagApp.mY = location.mY
                tagApp.mWidth = view.width
                tagApp.mHeight = view.height
                getLogger().d("onLayoutChange : ${tagApp.name}, x = ${tagApp.mX}, y = ${tagApp.mY}, width = ${tagApp.mWidth}, height = ${tagApp.mHeight}")
            }
        })
        view.setOnClickListener {
            val tagApp = it.getTag(R.string.app_name) as AppInfo
            val launchIntent =
                requireActivity().packageManager.getLaunchIntentForPackage(tagApp.packageName)
            startActivity(launchIntent)
        }
        return view
    }
}