package com.ahmedsallam.pager

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * View pager with index indicator
 */
class PagerIndicatorView(context: Context, attrs: AttributeSet) :
    LinearLayoutCompat(context, attrs) {
    /**
     * ViewPager2 instance
     */
    private lateinit var mPager: ViewPager2

    /**
     * Indicator view
     */
    private lateinit var mIndicatorView: IndicatorView

    /**
     * Index change callback listener
     */
    private var mOnIndexChangeListener: OnIndexChangeListener? = null

    /**
     * Pager instance
     */
    val viewPager get() = mPager

    /**
     * Indicates that indicator should auto scroll after [scrollDuration]
     */
    var autoScroll: Boolean = true

    /**
     * Auto scroll duration
     */
    var scrollDuration: Long = 5000L


    init {
        val arr = context.theme.obtainStyledAttributes(attrs, R.styleable.PagerIndicatorView, 0, 0)
        initView()
        initPager(arr)
        initIndicator(arr)
        arr.recycle()
    }

    /**
     * Initialize layout
     */
    private fun initView() {
        orientation = VERTICAL
        gravity = Gravity.CENTER

    }

    /**
     * Initialize view pager
     * @param arr Parameters container
     */
    private fun initPager(arr: TypedArray) {
        val height = arr.getDimensionPixelSize(
            R.styleable.PagerIndicatorView_pagerHeight, LayoutParams.MATCH_PARENT
        )
        mPager = ViewPager2(context)
        mPager.id = generateViewId()
        mPager.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, height)
        mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mIndicatorView.updateIndex(position)
                mOnIndexChangeListener?.onIndexChanged(position)
            }
        })
        addView(mPager)
    }

    /**
     * Initialize indicator
     * @param arr Parameters container
     */
    private fun initIndicator(arr: TypedArray) {
        val topMargin = arr.getDimensionPixelSize(
            R.styleable.PagerIndicatorView_indicatorMarginTop, Constants.INDICATOR_TOP_MARGIN
        )
        mIndicatorView = IndicatorView(context)
        mIndicatorView.initializeView(arr)
        mIndicatorView.layoutParams = (mIndicatorView.layoutParams as LayoutParams).apply {
            updateMargins(top = topMargin)
        }
        addView(mIndicatorView)
    }

    /**
     * Set view pager adapter
     * @param adapter Pager adapter
     */
    fun <T : RecyclerView.ViewHolder> setPagerAdapter(adapter: RecyclerView.Adapter<T>) {
        mPager.adapter = adapter
        mIndicatorView.initializeIndicator(adapter.itemCount)
    }


    /**
     * Register index listener callback
     * @param listener OnIndexChangeListener that is called when pager index changed
     */
    fun setOnIndexChangeListener(listener: OnIndexChangeListener) {
        mOnIndexChangeListener = listener
    }
}