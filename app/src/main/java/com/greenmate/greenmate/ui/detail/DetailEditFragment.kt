package com.greenmate.greenmate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.greenmate.greenmate.databinding.FragmentDetailEditBinding

class DetailEditFragment() : Fragment() {
    private var _binding: FragmentDetailEditBinding? = null
    private val binding: FragmentDetailEditBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.toolbar.setupWithNavController(findNavController())
        binding.toolbar.setTitleTextColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )
        _binding = null
    }
}