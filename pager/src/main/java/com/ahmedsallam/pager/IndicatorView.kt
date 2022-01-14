package com.ahmedsallam.pager

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Pager indicator view
 */
class IndicatorView(context: Context) : RecyclerView(context) {
    /**
     * Indicator configuration
     */
    private lateinit var mIndicatorConfig: IndicatorConfig

    /**
     * Indicator items adapter
     */
    private lateinit var mIndicatorAdapter: IndicatorAdapter

    /**
     * Initialize recycler view
     * @param arr Parameters container
     */
    fun initializeView(arr: TypedArray) {
        initView(arr)
        initConfig(arr)
    }


    /**
     * Initialize recycler view
     * @param arr Parameters container
     */
    private fun initView(arr: TypedArray) {
        val top = arr.getDimensionPixelSize(R.styleable.PagerIndicatorView_indicatorPaddingTop, 0)
        val bottom =
            arr.getDimensionPixelSize(R.styleable.PagerIndicatorView_indicatorPaddingBottom, 0)
        val start =
            arr.getDimensionPixelSize(R.styleable.PagerIndicatorView_indicatorPaddingStart, 0)
        val end = arr.getDimensionPixelSize(R.styleable.PagerIndicatorView_indicatorPaddingEnd, 0)
        val background =
            arr.getColor(R.styleable.PagerIndicatorView_indicatorColorBackground, Color.TRANSPARENT)
        id = generateViewId()
        isNestedScrollingEnabled = false
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setPaddingRelative(start, top, end, bottom)
        }
        layoutManager = LinearLayoutManager(context)
        setBackgroundColor(background)
    }

    /**
     * Initialize indicator configurations
     * @param arr Parameters container
     */
    private fun initConfig(arr: TypedArray) {
        val shapeIndex = arr.getInteger(R.styleable.PagerIndicatorView_indicatorShape, 0)
        val shape = IndicatorShape.values()[shapeIndex]
        val colorSelected = arr.getColor(R.styleable.PagerIndicatorView_indicatorColorSelected, 0)
        val colorUnselected =
            arr.getColor(R.styleable.PagerIndicatorView_indicatorColorUnselected, 0)
        val itemWidth = arr.getDimensionPixelSize(
            R.styleable.PagerIndicatorView_indicatorItemWidth, Constants.ITEM_SIZE
        )
        val itemHeight =
            arr.getDimensionPixelSize(
                R.styleable.PagerIndicatorView_indicatorItemHeight, Constants.ITEM_SIZE
            )
        val itemMargin = arr.getDimensionPixelSize(
            R.styleable.PagerIndicatorView_indicatorItemMargin, Constants.ITEM_MARGIN
        )
        val selectedRes =
            arr.getResourceId(R.styleable.PagerIndicatorView_indicatorCustomSelected, 0)
        val itemSelected = getShapeDrawable(selectedRes, shape)
        val unselectedRes =
            arr.getResourceId(R.styleable.PagerIndicatorView_indicatorCustomUnelected, 0)
        val itemUnselected = getShapeDrawable(unselectedRes, shape)
        val scaleType =
            when (arr.getInt(R.styleable.PagerIndicatorView_indicatorItemScaleType, 0)) {
                IndicatorScaleType.CENTER.ordinal -> ImageView.ScaleType.CENTER
                IndicatorScaleType.CENTER_INSIDE.ordinal -> ImageView.ScaleType.CENTER_INSIDE
                IndicatorScaleType.CENTER_CROP.ordinal -> ImageView.ScaleType.CENTER_CROP
                IndicatorScaleType.FIT_CENTER.ordinal -> ImageView.ScaleType.FIT_CENTER
                else -> ImageView.ScaleType.FIT_XY
            }
        mIndicatorConfig = IndicatorConfig(
            colorSelected, colorUnselected, itemWidth, itemHeight, itemMargin, itemSelected,
            itemUnselected, scaleType
        )
    }

    /**
     * Get shape for the given resource or shape
     */
    private fun getShapeDrawable(res: Int, shape: IndicatorShape): Drawable {
        return when (shape) {
            IndicatorShape.CIRCLE -> ShapeDrawable(OvalShape())
            IndicatorShape.SQUARE -> ShapeDrawable(RectShape())
            else -> ContextCompat.getDrawable(context, res)!!
        }
    }

    /**
     * Initialize pager indicator with given count
     * @param itemsCount Pager items count
     */
    fun initializeIndicator(itemsCount: Int) {
        mIndicatorAdapter = IndicatorAdapter(itemsCount, mIndicatorConfig)
        adapter = mIndicatorAdapter
    }

    /**
     * Update current selected index with given value
     * @param index New selected index
     */
    fun updateIndex(index: Int) {
        mIndicatorAdapter.updateIndex(index)
    }
}