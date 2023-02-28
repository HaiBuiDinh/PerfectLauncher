package com.xxx.thachraucau.launcher.model

enum class InfoType {
    APP, FOLDER, WIDGET, EMPTY;

    fun isWidget() = this == WIDGET
    fun isEmpty() = this == EMPTY
}