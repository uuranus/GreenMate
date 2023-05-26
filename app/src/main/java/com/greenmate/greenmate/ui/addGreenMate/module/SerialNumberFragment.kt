package com.greenmate.greenmate.ui.addGreenMate.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.FragmentSerialNumberBinding
import com.greenmate.greenmate.ui.addGreenMate.AddGreenMateViewModel

class SerialNumberFragment : Fragment() {
    private var _binding: FragmentSerialNumberBinding? = null
    private val binding: FragmentSerialNumberBinding get() = _binding!!
    private val addGreenMateViewModel: AddGreenMateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_serial_number, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vm = addGreenMateViewModel
            lifecycleOwner = this@SerialNumberFragment.viewLifecycleOwner

            continueButton.setOnClickListener {
                if (addGreenMateViewModel.isModuleAdded()) {
                    findNavController().navigate(R.id.action_serialNumberFragment_to_findModuleFragment)
                } else {
                    findNavController().navigate(R.id.action_serialNumberFragment2_to_findModuleFragment2)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}