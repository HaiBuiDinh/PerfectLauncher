package com.xxx.thachraucau.launcher.controller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxx.thachraucau.launcher.model.LibraryInfo
import com.xxx.thachraucau.launcher.repository.AppLibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppLibraryController @Inject constructor(val mRepository: AppLibraryRepository) :
    ViewModel() {
    val mListData = MutableLiveData<ArrayList<LibraryInfo>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mRepository.getListLibraryInfo()
            withContext(Dispatchers.Main) {
                mListData.value = result
            }
        }
    }
}