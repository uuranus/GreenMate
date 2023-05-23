package com.greenmate.greenmate.ui.addGreenMate.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.FragmentFindModuleBinding
import com.greenmate.greenmate.ui.addGreenMate.AddGreenMateViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FindModuleFragment : Fragment() {

    private var _binding: FragmentFindModuleBinding? = null
    private val binding: FragmentFindModuleBinding get() = _binding!!
    private val addGreenMateViewModel: AddGreenMateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFindModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            continueButton.setOnClickListener {
                if (addGreenMateViewModel.isModuleAdded()) {
                    findNavController().navigate(R.id.action_findModuleFragment_to_selectTypeFragment)
                } else {
                    addGreenMateViewModel.saveGreenMate()
                    requireActivity().finish()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}