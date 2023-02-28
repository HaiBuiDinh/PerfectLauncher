package com.xxx.thachraucau.launcher.fragment

import android.content.ClipData
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.View.OnLayoutChangeListener
import android.widget.GridLayout
import com.xxx.thachraucau.launcher.GridManager
import com.xxx.thachraucau.launcher.Logger
import com.xxx.thachraucau.launcher.model.AppInfo
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.databinding.CustomAppListBinding
import com.xxx.thachraucau.launcher.databinding.FragmentAppListGridBinding

class AppListGridFragment : AbsFragment() {

    var mPageIndex: Int = -1
    var mListAppInfo: MutableList<AppInfo> = mutableListOf()
    lateinit var mGridLayout: GridLayout

    val mLogger by lazy {
        Logger(this.javaClass.simpleName)
    }

    val mCurrentGrid by lazy {
        GridManager.getInstance().currentGrid
    }

    var mSelectedPage = -1

    val mNeedCheckGrid = false


    // 0: vị trí x
    // 1: vị trí y
    // 2: width
    // 3: height
    val gridlayoutSize = IntArray(4)

    lateinit var mBinding: FragmentAppListGridBinding


    override fun onPageChanged(selectedPage: Int) {
        mLogger.d("check equal size grid.count = ${mGridLayout.childCount}, list.size = ${mListAppInfo.size}")
        mSelectedPage = selectedPage
    }

    override fun dropHotSeatFull(appInfo: AppInfo) {
        if (mPageIndex == mSelectedPage) {
            mListAppInfo.add(appInfo)
            mGridLayout.addView(getViewToAdd(mListAppInfo.size - 1).apply {
                setTag(R.string.app_name, mListAppInfo.last())
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_app_list_grid, container, false)
        mBinding = FragmentAppListGridBinding.bind(rootView)
        mGridLayout = mBinding.gridAppList
        mGridLayout.rowCount = mCurrentGrid.mRow
        mGridLayout.columnCount = mCurrentGrid.mCol

        mGridLayout.addOnLayoutChangeListener(object : OnLayoutChangeListener {
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
                mGridLayout.removeOnLayoutChangeListener(this)
                val gridLocation = IntArray(2)
                mGridLayout.getLocationOnScreen(gridLocation)
                gridlayoutSize[0] = gridLocation[0]
                gridlayoutSize[1] = gridLocation[1]
                gridlayoutSize[2] = mGridLayout.width
                gridlayoutSize[3] = mGridLayout.height
                mLogger.d("gird x = ${gridLocation[0]} y = ${gridLocation[1]}, widht = ${mGridLayout.width} height = ${mGridLayout.height}")
                Handler(Looper.getMainLooper()).post {
                    initAppList()
                }
            }
        })
        return rootView
    }

    fun initAppList() {
        for (index in 0 until mListAppInfo.size) {
            mGridLayout.addView(getViewToAdd(index))
        }

        mGridLayout.setOnDragListener(object : View.OnDragListener {
            override fun onDrag(v: View?, event: DragEvent?): Boolean {
                when (event?.action) {
                    DragEvent.ACTION_DROP -> {
                        val dragView = event.localState as View?
                        val tagInfo = dragView?.getTag(R.string.app_name) as AppInfo?
                        val position =
                            findDropPosition(event.x + mPageIndex * gridlayoutSize[2], event.y)
                        if (position != null) {
                            mLogger.d("target position between ${position[0]} and ${position[1]}")
                            tagInfo?.let {
                                mListAppInfo.add(position[1], it)
                                mGridLayout.addView(getViewToAdd(position[1]).apply {
                                    setTag(R.string.app_name, it)
                                }, position[1])
                            }
                        } else {
                            // add vao cuoi
                            tagInfo?.let {
                                mListAppInfo.add(it)
                                mGridLayout.addView(getViewToAdd(mListAppInfo.size - 1).apply {
                                    setTag(R.string.app_name, mListAppInfo.last())
                                })
                            }
                        }
                        if (mNeedCheckGrid) {
                            for (index in 0 until mGridLayout.childCount) {
                                val info = mGridLayout.getChildAt(index)
                                    .getTag(R.string.app_name) as AppInfo
                                mLogger.d("afterDrop view = $info")
                            }
                            mLogger.d("after drop list ===================================")
                            for (info in mListAppInfo) {
                                mLogger.d("afterDrop info = $info")
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
    }

    fun findDropPosition(x: Float, y: Float): IntArray? {
        mLogger.d("findDropPosition x = $x, y = $y")
        val result = IntArray(2)
        var targetApp: AppInfo? = null
        var targetIndex = -1
        for (index in 0 until mListAppInfo.size) {
            val info = mListAppInfo.get(index)
            mLogger.d("findDropTarget ${info.name} index = $index from x = ${info.mX} to ${info.mX + info.mWidth} and from y = ${info.mY} to ${info.mY + info.mHeight}")
            if (info.mX <= x && info.mX + info.mWidth >= x && info.mY <= y && info.mY + info.mHeight >= y) {
                targetApp = info
                targetIndex = index
                break
            }
        }
        if (targetApp != null) {
            mLogger.d("founded!! targetApp = $targetApp, targetIndex = $targetIndex")
            if (x <= targetApp.mX + targetApp.mWidth / 2) {
                result[0] = targetIndex - 1
                result[1] = targetIndex
            } else {
                result[0] = targetIndex
                result[1] = targetIndex + 1
            }
            return result
        }
        mLogger.d("not found")
        return null
    }

    fun getViewToAdd(position: Int): View {
        val view =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.custom_app_list, mGridLayout, false)
        val customBinding: CustomAppListBinding = CustomAppListBinding.bind(view)
        val appInfo = mListAppInfo.get(position).apply {
            customBinding.tvCustom.text = name
            customBinding.imCustom.setImageDrawable(icon)
            mPageNumber = mPageIndex
            mRow = position / mCurrentGrid.mCol
            mCol = position % mCurrentGrid.mCol
        }
        view.setTag(R.string.app_name, appInfo)
        view.setOnLongClickListener {
            val tagApp = it.getTag(R.string.app_name) as AppInfo
            val data = ClipData.newPlainText("", "test")
            val shadowBuilder = View.DragShadowBuilder(view)
            view.startDragAndDrop(data, shadowBuilder, view, 0)
            view.setTag(R.string.app_name, AppInfo(tagApp.name, tagApp.packageName, tagApp.icon))
            val currentPosition = mListAppInfo.indexOf(tagApp)
            mLogger.d("currentPosition = $currentPosition, app = ${tagApp.name}")
            for (index in mListAppInfo.size - 1 downTo currentPosition + 1) {
                mListAppInfo[index].mX = mListAppInfo[index - 1].mX
                mListAppInfo[index].mY = mListAppInfo[index - 1].mY
                mListAppInfo[index].mRow = mListAppInfo[index - 1].mRow
                mListAppInfo[index].mCol = mListAppInfo[index - 1].mCol
                mListAppInfo[index].mWidth = mListAppInfo[index - 1].mWidth
                mListAppInfo[index].mHeight = mListAppInfo[index - 1].mHeight
            }
            mListAppInfo.remove(tagApp)
            mGridLayout.removeView(it)

            if (mNeedCheckGrid) {
                for (index in 0 until mGridLayout.childCount) {
                    val test = mGridLayout.getChildAt(index).getTag(R.string.app_name)
                    mLogger.d("afterDrag view = $test")
                }
                mLogger.d("======================")
                for (index in 0 until mListAppInfo.size) {
                    mLogger.d("afterDrag info = ${mListAppInfo.get(index)}")
                }
            }

            true
        }
        val param = GridLayout.LayoutParams()
        param.height = gridlayoutSize[3] / mCurrentGrid.mRow
        param.width = gridlayoutSize[2] / mCurrentGrid.mCol
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
        view.addOnLayoutChangeListener(object : OnLayoutChangeListener {
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
                val location = IntArray(2)
                view.getLocationOnScreen(location)
                tagApp.mX =
                    location[0] + if (mPageIndex == mSelectedPage) mPageIndex * gridlayoutSize[2] else 0
                tagApp.mY = location[1]
                tagApp.mWidth = view.width
                tagApp.mHeight = view.height
                mLogger.d("onLayoutChange : ${tagApp.name}, row = ${tagApp.mRow}, col = ${tagApp.mCol}, x = ${tagApp.mX}, y = ${tagApp.mY}, width = ${tagApp.mWidth}, height = ${tagApp.mHeight}")
            }
        })
        view.setOnClickListener {
            val tagApp = it.getTag(R.string.app_name) as AppInfo
            val launchIntent =
                requireContext().packageManager.getLaunchIntentForPackage(tagApp.packageName)
            requireContext().startActivity(launchIntent)
        }
        return view
    }
}