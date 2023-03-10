package com.xxx.thachraucau.launcher.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.xxx.thachraucau.launcher.adapter.MainPagerAdapter
import com.xxx.thachraucau.launcher.controller.MainController
import com.xxx.thachraucau.launcher.databinding.ActivityMainBinding
import com.xxx.thachraucau.launcher.getLogger
import com.xxx.thachraucau.launcher.sLoggerMap
import com.xxx.thachraucau.launcher.model.Grid
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mController: MainController by viewModels()

    @Inject
    lateinit var mCurrentGrid: Grid

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val adapter = MainPagerAdapter(supportFragmentManager)
        mBinding.viewpagerHome.apply {
            this.adapter = adapter
            offscreenPageLimit = adapter.count
        }
        mBinding.viewpagerHome.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                getLogger().d("onPageSelected position = $position")
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        mBinding.viewpagerHome.setCurrentItem(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        sLoggerMap.clear()
    }
}