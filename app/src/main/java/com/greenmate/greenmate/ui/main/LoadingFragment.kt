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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.greenmate.greenmate.databinding.DialogYesOrNoBinding
import com.greenmate.greenmate.databinding.FragmentLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding: FragmentLoadingBinding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    private val id = "11111"
    private val password = "11111"
    private lateinit var dialogView: DialogYesOrNoBinding
    private lateinit var againAlertDialog: androidx.appcompat.app.AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        dialogView = DialogYesOrNoBinding.inflate(requireActivity().layoutInflater).apply {
            titleTextView.text = "네트워크가 불안정합니다.\n다시 시도하시겠습니까?"
            yesButton.setOnClickListener {
                findNavController().navigateUp()
            }
            noButton.setOnClickListener {
                requireActivity().finish()
            }
        }
        againAlertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView.root)
            .setCancelable(true)
            .create()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.login(id, password)
        mainViewModel.getAllGreenMates()

        lifecycleScope.launch {
            delay(5000)
            againAlertDialog.show()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isDataLoaded.collectLatest {
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