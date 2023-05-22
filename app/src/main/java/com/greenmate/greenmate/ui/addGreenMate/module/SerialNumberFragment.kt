package com.greenmate.greenmate.ui.addGreenMate.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.FragmentSerialNumberBinding

class SerialNumberFragment : Fragment() {
    private var _binding: FragmentSerialNumberBinding? = null
    private val binding: FragmentSerialNumberBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSerialNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            continueButton.setOnClickListener {
                findNavController().navigate(R.id.action_serialNumberFragment_to_findModuleFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}