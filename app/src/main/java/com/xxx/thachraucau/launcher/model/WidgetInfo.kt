package com.xxx.thachraucau.launcher.model

class WidgetInfo : AbsInfo(InfoType.WIDGET) {
    val mSpanRow: Int = 1
    val mSpanCol: Int = 1

    override fun getSize() = mSpanCol * mSpanRow
}