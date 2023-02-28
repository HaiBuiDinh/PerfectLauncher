package com.xxx.thachraucau.launcher.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xxx.thachraucau.launcher.model.AppInfo
import com.xxx.thachraucau.launcher.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainController @Inject constructor(val mRepository: AppRepository): ViewModel() {
    val listData: MutableLiveData<List<AppInfo>> = MutableLiveData()

    init {
        listData.value = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            val appList = mRepository.getAppList()
            withContext(Dispatchers.Main) {
                listData.value = appList
            }
        }
    }
}