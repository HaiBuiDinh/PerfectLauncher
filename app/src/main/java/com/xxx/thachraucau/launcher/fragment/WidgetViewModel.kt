package com.xxx.thachraucau.launcher.fragment

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WidgetViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val _mapWidgetInfo =
        MutableLiveData<Map<String, List<AppWidgetProviderInfo>>>(emptyMap())
    val mapWidgetInfo get() = _mapWidgetInfo

    init {
        val manager = AppWidgetManager.getInstance(context)
        val listWidget: List<AppWidgetProviderInfo> = manager.installedProviders
        val tempMap = listWidget.groupBy { info -> info.provider.packageName }
        _mapWidgetInfo.value = tempMap

    }
}