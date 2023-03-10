package com.xxx.thachraucau.launcher.model

abstract class ItemInfo(val mInfoType: InfoType): LocationInfo() {
    var mSpanRow = 1
    var mSpanCol = 1
    var mPageNumber: Int = -1

    open fun getSize() = mSpanCol * mSpanRow
}