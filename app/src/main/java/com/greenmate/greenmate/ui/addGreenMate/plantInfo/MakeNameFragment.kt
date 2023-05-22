package com.greenmate.greenmate.ui.addGreenMate.plantInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.greenmate.greenmate.databinding.FragmentMakeNameBinding


class MakeNameFragment : Fragment() {

    private var _binding: FragmentMakeNameBinding? = null
    private val binding: FragmentMakeNameBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMakeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            continueButton.setOnClickListener {
                requireActivity().finish()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}