package com.xxx.thachraucau.launcher.model

abstract class AbsInfo(val mInfoType: InfoType): LocationInfo() {
    var mRow: Int = -1
    var mCol: Int = -1
    var mPageNumber: Int = -1

    open fun getSize() = 1
}