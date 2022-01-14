package com.ahmedsallam.pager

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updateMargins
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.util.*


/**
 * View pager with index indicator
 */
class PagerIndicatorView(context: Context, attrs: AttributeSet) :
    LinearLayoutCompat(context, attrs), DefaultLifecycleObserver {
    /**
     * ViewPager2 instance
     */
    private var mPager: ViewPager2? = null

    /**
     * Indicator view
     */
    private lateinit var mIndicatorView: IndicatorView

    /**
     * Index change callback listener
     */
    private var mOnIndexChangeListener: OnIndexChangeListener? = null

    /**
     * Timer to schedule auto scroll
     */
    private var mTimer: Timer? = null

    /**
     * Main looper handler
     */
    private val mMainHandler = Handler(Looper.getMainLooper())

    /**
     * Pager instance
     */
    val viewPager get() = mPager

    /**
     * Indicates that indicator should auto scroll after [scrollDuration]
     */
    var autoScroll: Boolean = false
        set(value) {
            if (value) {
                initializeTimer()
            } else {
                cancelTimer()
            }
            field = value
        }

    /**
     * Auto scroll duration
     */
    var scrollDuration: Long = 5000L
        set(value) {
            if (autoScroll) {
                initializeTimer()
            }
            field = value
        }

    /**
     * Current selected index
     */
    var currentIndex: Int = 0
        set(value) {
            viewPager?.setCurrentItem(value, true)
            field = value
        }


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
        autoScroll = arr.getBoolean(R.styleable.PagerIndicatorView_pagerScrollAuto, false)
        scrollDuration =
            arr.getInt(R.styleable.PagerIndicatorView_pagerScrollDuration, 5000).toLong()
        val height = arr.getDimensionPixelSize(R.styleable.PagerIndicatorView_pagerHeight, 0)
        mPager = ViewPager2(context)
        mPager?.id = generateViewId()
        mPager?.layoutParams = if (height > 0) {
            LayoutParams(LayoutParams.MATCH_PARENT, height)
        } else {
            LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)
        }
        mPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentIndex = position
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
        mPager?.adapter = adapter
        mIndicatorView.initializeIndicator(adapter.itemCount)
    }


    /**
     * Register index listener callback
     * @param listener OnIndexChangeListener that is called when pager index changed
     */
    fun setOnIndexChangeListener(listener: OnIndexChangeListener) {
        mOnIndexChangeListener = listener
    }

    override fun onStart(owner: LifecycleOwner) {
        if (autoScroll) {
            initializeTimer()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        mTimer?.cancel()
        mTimer = null
    }

    /**
     * Initialize auto scroll timer
     */
    private fun initializeTimer() {
        if ((mPager?.adapter?.itemCount ?: 0) <= 0) {
            return
        }
        mTimer?.cancel()
        mTimer = Timer()
        mTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Navigate between screens every [scrollDuration] seconds
                val index = if (currentIndex < (mPager?.adapter?.itemCount ?: 0) - 1) {
                    currentIndex + 1
                } else {
                    0
                }
                // Post index update to main thread
                mMainHandler.post {
                    currentIndex = index
                }
            }
        }, 0, scrollDuration)
    }

    /**
     * Cancel running timer
     */
    private fun cancelTimer() {
        mTimer?.cancel()
        mTimer = null
    }
}