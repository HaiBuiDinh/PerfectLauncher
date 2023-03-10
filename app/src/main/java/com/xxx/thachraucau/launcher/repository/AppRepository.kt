package com.xxx.thachraucau.launcher.repository

import javax.inject.Inject

class AppRepository @Inject constructor(val mAppDataSource: AppDataSource) {

    fun getAppList() = mAppDataSource.getListApp()
}