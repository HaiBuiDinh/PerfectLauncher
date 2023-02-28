package com.xxx.thachraucau.launcher.activity

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.xxx.thachraucau.launcher.*
import com.xxx.thachraucau.launcher.adapter.PagerGridAdapter
import com.xxx.thachraucau.launcher.controller.MainController
import com.xxx.thachraucau.launcher.databinding.ActivityMainBinding
import com.xxx.thachraucau.launcher.databinding.CustomAppListBinding
import com.xxx.thachraucau.launcher.manager.GridManager
import com.xxx.thachraucau.launcher.model.AppInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mLogger by lazy {
        Logger(this.javaClass.simpleName)
    }

    val mController: MainController by viewModels()

    val mScreenSize by lazy {
        getScreenSize()
    }

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

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
                        mLogger.d("ACTION_DROP childcount = ${mBinding.hotseat.childCount} tagInfo = $tagInfo")
                        tagInfo?.let {
                            if (mBinding.hotseat.childCount < 4) {
                                mBinding.hotseat.addView(getViewToAdd(tagInfo))
                            } else {
                                (mBinding.viewpagerGrid.adapter as PagerGridAdapter).dropHotSeatFull(
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

        mController.listData.observe(this) {
            if (it.isNotEmpty()) {
                val adapter = PagerGridAdapter(supportFragmentManager, it)
                mBinding.viewpagerGrid.apply {
                    this.adapter = adapter
                    offscreenPageLimit = adapter.count
                }
                adapter.onPageChanged(0)
//                đổi page
//                viewpager_grid.setCurrentItem()
                mBinding.viewpagerGrid.addOnPageChangeListener(object :
                    ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        mLogger.d("onPageSelected position = $position")
                        adapter.onPageChanged(position)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                })
            }
        }
    }

    fun getViewToAdd(appInfo: AppInfo): View {
        val view =
            LayoutInflater.from(this)
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
        val param = GridLayout.LayoutParams()
        val currentGrid = GridManager.getInstance().currentGrid
        param.height = (mScreenSize.mHeight * 85 / 100) / currentGrid.mRow
        param.width = mScreenSize.mWidth / currentGrid.mCol
//        param.rightMargin = 5
//        param.topMargin = 5
        param.setGravity(Gravity.CENTER)
        // xử lý resize
//        if (position == 8) {
//            param.setGravity(Gravity.FILL_HORIZONTAL or Gravity.FILL_VERTICAL)
//            param.columnSpec = GridLayout.spec(0, 2)
//            param.rowSpec = GridLayout.spec(2, 2)
//            param.height *= 2
//            param.width *= 2
//        }
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
                mLogger.d("onLayoutChange : ${tagApp.name}, row = ${tagApp.mRow}, col = ${tagApp.mCol}, x = ${tagApp.mX}, y = ${tagApp.mY}, width = ${tagApp.mWidth}, height = ${tagApp.mHeight}")
            }
        })
        view.setOnClickListener {
            val tagApp = it.getTag(R.string.app_name) as AppInfo
            val launchIntent = packageManager.getLaunchIntentForPackage(tagApp.packageName)
            startActivity(launchIntent)
        }
        return view
    }
}