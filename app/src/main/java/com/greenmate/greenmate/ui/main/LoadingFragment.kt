package com.greenmate.greenmate.ui.main

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
import com.greenmate.greenmate.databinding.FragmentLoadingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding: FragmentLoadingBinding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    private val id = "11111"
    private val password = "11111"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.login(id, password)
        mainViewModel.getAllGreenMates()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isDataLoaded.collectLatest {
                    println("it TTTT$it")
                    if (it) {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}