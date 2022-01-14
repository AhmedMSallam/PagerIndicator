package com.ahmedsallam.pagerindicator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedsallam.pagerindicator.databinding.ItemImageBinding

class DummyAdapter : RecyclerView.Adapter<DummyAdapter.ItemViewHolder>() {

    class ItemViewHolder(binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Do nothing
    }

    override fun getItemCount(): Int = 5
}