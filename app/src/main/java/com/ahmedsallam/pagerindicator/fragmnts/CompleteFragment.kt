package com.ahmedsallam.pagerindicator.fragmnts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahmedsallam.pager.OnIndexChangeListener
import com.ahmedsallam.pagerindicator.DummyAdapter
import com.ahmedsallam.pagerindicator.databinding.FragmentCompleteBinding

/**
 * Complete usage fragment
 */
class CompleteFragment : Fragment() {
    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerIndicator.setPagerAdapter(DummyAdapter())
        binding.pagerIndicator.setOnIndexChangeListener(object : OnIndexChangeListener {
            override fun onIndexChanged(index: Int) {
                Log.d("PagerIndicator", "Current index => $index")
            }
        })

        binding.pagerIndicator2.setPagerAdapter(DummyAdapter())
        binding.pagerIndicator2.setOnIndexChangeListener(object : OnIndexChangeListener {
            override fun onIndexChanged(index: Int) {
                Log.d("PagerIndicator", "Current index => $index")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}