package com.xxx.thachraucau.launcher

import com.xxx.thachraucau.launcher.model.Grid

class GridManager {
    companion object {
        var INSTACE: GridManager? = null
        fun getInstance(): GridManager {
            if (INSTACE == null) {
                synchronized(this) {
                    INSTACE = GridManager()
                }
            }
            return INSTACE!!
        }
    }

    var currentGrid: Grid = Grid.GRID4x6

    fun setGrid(grid: Grid) {
        currentGrid = grid
    }
}