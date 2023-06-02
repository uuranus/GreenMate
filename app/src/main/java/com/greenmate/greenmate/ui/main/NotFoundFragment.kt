package com.greenmate.greenmate.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.databinding.FragmentNotFoundBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotFoundFragment : Fragment() {
    private var _binding: FragmentNotFoundBinding? = null
    private val binding: FragmentNotFoundBinding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

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

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.isDataLoaded.collectLatest {
                    if(it){
                        if (mainViewModel.isGreenMateEmpty().not()) {
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getAllGreenMates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}