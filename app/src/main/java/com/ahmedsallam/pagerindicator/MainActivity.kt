package com.ahmedsallam.pagerindicator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ahmedsallam.pager.OnIndexChangeListener
import com.ahmedsallam.pagerindicator.databinding.ActivityMainBinding
import com.ahmedsallam.pagerindicator.databinding.ItemImageBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pagerIndicator.setPagerAdapter(Adapter())
        binding.pagerIndicator.setOnIndexChangeListener(object : OnIndexChangeListener {
            override fun onIndexChanged(index: Int) {
                Log.d("PagerIndicator", "Current index => $index")
            }
        })
    }

    class Adapter : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

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
}