package com.xxx.thachraucau.launcher.repository

import javax.inject.Inject

class AppLibraryRepository @Inject constructor(val mAppDataSource: AppDataSource){
    fun getListLibraryInfo() = mAppDataSource.getListLibraryInfo()
}