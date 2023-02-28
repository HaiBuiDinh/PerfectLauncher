package com.xxx.thachraucau.launcher.model

abstract class AbsInfo(val mInfoType: InfoType) {
    var mRow: Int = -1
    var mCol: Int = -1
    var mPageNumber: Int = -1
    var mX: Int = -1
    var mY: Int = -1
    var mWidth: Int = -1
    var mHeight: Int = -1

    open fun getSize() = 1
}