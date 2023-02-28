package com.xxx.thachraucau.launcher.model

enum class Grid(val mCol: Int, val mRow: Int) {
    GRID4x5(4, 5), GRID4x6(4, 6), GRID5x5(5, 5), GRID5x6(5, 6);

    fun getCount() = mRow * mCol
}