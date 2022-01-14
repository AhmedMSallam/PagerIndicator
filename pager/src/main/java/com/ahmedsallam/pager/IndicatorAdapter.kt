package com.ahmedsallam.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import com.ahmedsallam.pager.databinding.ItemIndicatorBinding

/**
 * Indicator view item adapter
 * @param count Items count
 * @param config Indicator configuration
 */
class IndicatorAdapter(
    private val count: Int,
    private val config: IndicatorConfig,
) : RecyclerView.Adapter<IndicatorAdapter.IndicatorHolder>() {
    /**
     * Current selected index
     */
    private var mCurrentIndex = 0

    /**
     * Indicator view holder
     */
    class IndicatorHolder(binding: ItemIndicatorBinding) : RecyclerView.ViewHolder(binding.root) {
        val item: ImageView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorHolder {
        val binding = ItemIndicatorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        binding.root.layoutParams = (binding.root.layoutParams as RecyclerView.LayoutParams).apply {
            width = config.itemWidth
            height = config.itemHeight
            updateMargins(config.itemMargin / 2, 0, config.itemMargin / 2, 0)
        }
        binding.root.scaleType = config.itemScaleType
        return IndicatorHolder(binding)
    }

    override fun onBindViewHolder(holder: IndicatorHolder, position: Int) {
        if (position == mCurrentIndex) {
            holder.item.apply {
                setImageDrawable(config.itemSelected)
                if (config.colorSelected != 0) {
                    setColorFilter(config.colorSelected)
                }
            }
        } else {
            holder.item.apply {
                setImageDrawable(config.itemUnselected)
                if (config.colorUnselected != 0) {
                    setColorFilter(config.colorUnselected)
                }
            }
        }
    }

    override fun getItemCount(): Int = count

    /**
     * Update current selected index with given value
     * @param index New selected index
     */
    fun updateIndex(index: Int) {
        val temp = mCurrentIndex
        mCurrentIndex = -1
        notifyItemChanged(temp)
        mCurrentIndex = index
        notifyItemChanged(index)
    }
}