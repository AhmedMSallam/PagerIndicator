package com.ahmedsallam.pagerindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ahmedsallam.pagerindicator.databinding.FragmentHomeBinding

/**
 * Home fragment
 */
class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bBasic.setOnClickListener(this)
        binding.bShape.setOnClickListener(this)
        binding.bDrawable.setOnClickListener(this)
        binding.bComplete.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        val action = when (v.id) {
            R.id.bBasic -> {
                HomeFragmentDirections.actionHomeFragmentToBasicFragment()
            }
            R.id.bShape -> {
                HomeFragmentDirections.actionHomeFragmentToShapeFragment()
            }
            R.id.bDrawable -> {
                HomeFragmentDirections.actionHomeFragmentToBasicFragment()
            }
            else -> {
                HomeFragmentDirections.actionHomeFragmentToBasicFragment()
            }
        }
        v.findNavController().navigate(action)
    }
}