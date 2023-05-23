package com.greenmate.greenmate.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.databinding.FragmentNotFoundBinding

class NotFoundFragment : Fragment() {
    private var _binding: FragmentNotFoundBinding? = null
    private val binding: FragmentNotFoundBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotFoundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            addModuleButton.setOnClickListener {
                val action =
                    NotFoundFragmentDirections.actionNotFoundFragmentToAddGreenMateActivity(1)
                findNavController().navigate(action)
            }

            addPlantButton.setOnClickListener {
                val action =
                    NotFoundFragmentDirections.actionNotFoundFragmentToAddGreenMateActivity(0)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}