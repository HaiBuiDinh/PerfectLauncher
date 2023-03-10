package com.xxx.thachraucau.launcher.activity

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.xxx.thachraucau.launcher.R
import com.xxx.thachraucau.launcher.adapter.WidgetNamesAdapter
import com.xxx.thachraucau.launcher.databinding.ActivityMain2Binding
import com.xxx.thachraucau.launcher.fragment.BSMainWidgetFragment


class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = AppWidgetManager.getInstance(this)
        val listWidget: List<AppWidgetProviderInfo> = manager.installedProviders
        val temListWidget = listWidget.asSequence().filter { it -> it.previewImage != 0 }
            .groupBy { info -> info.provider.packageName }
        val tempMap = listWidget.groupBy { info -> info.provider.packageName }

        tempMap.forEach { (t, u) -> Log.d(TAG, "$t --- size = ${u.size}") }
        val listName: List<String> = tempMap.keys.toList()
//        val drawable = listWidget[19].loadPreviewImage(this, 0)
//        val grid = findViewById<GridLayout>(R.id.grid_main)
//        grid.addView(LayoutInflater.from(this).inflate(R.layout.custom_app_list, grid, false).apply {
//            findViewById<ImageView>(R.id.im_custom).setImageDrawable(drawable)
//        })

        binding.btnBottomSheet.setOnClickListener {
            val bsFragment = BSMainWidgetFragment(listName)
            bsFragment.show(supportFragmentManager, bsFragment.tag)
        }

    }

    companion object {
        private const val TAG = "MainActivity2"
    }
}