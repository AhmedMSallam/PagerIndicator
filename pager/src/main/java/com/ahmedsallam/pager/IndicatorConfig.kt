package com.ahmedsallam.pager

import android.graphics.drawable.Drawable
import android.widget.ImageView

class IndicatorConfig(
    val colorSelected: Int,
    val colorUnselected: Int,
    val itemWidth: Int,
    val itemHeight: Int,
    val itemMargin: Int,
    val itemSelected: Drawable,
    val itemUnselected: Drawable,
    val itemScaleType: ImageView.ScaleType
)