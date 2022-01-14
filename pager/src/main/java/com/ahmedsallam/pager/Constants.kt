package com.ahmedsallam.pager

object Constants {
    const val ITEM_SIZE = 32
    const val ITEM_MARGIN = 16
    const val INDICATOR_TOP_MARGIN = 32
}

enum class IndicatorShape { CIRCLE, SQUARE, CUSTOM }

enum class IndicatorAlignment { START, CENTER, END }

enum class IndicatorScaleType { CENTER, CENTER_INSIDE, CENTER_CROP, FIT_CENTER, FIT_XY }