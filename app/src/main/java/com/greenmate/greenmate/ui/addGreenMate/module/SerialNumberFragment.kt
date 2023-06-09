package com.greenmate.greenmate.ui.addGreenMate.module

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.DialogLoadingBinding
import com.greenmate.greenmate.databinding.FragmentSerialNumberBinding
import com.greenmate.greenmate.ui.addGreenMate.AddGreenMateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SerialNumberFragment : Fragment() {
    private var _binding: FragmentSerialNumberBinding? = null
    private val binding: FragmentSerialNumberBinding get() = _binding!!
    private val addGreenMateViewModel: AddGreenMateViewModel by activityViewModels()
    private val progressDialog: AlertDialog by lazy {
        val dialogView = DialogLoadingBinding.inflate(requireActivity().layoutInflater)
        AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

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
                viewLifecycleOwner.lifecycleScope.launch {
                    progressDialog.show()
                    delay(1000)
                    addGreenMateViewModel.findSerialNumber()

                    lifecycleScope.launch {
                        delay(5000)
                        progressDialog.dismiss()
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addGreenMateViewModel.isSerialNumberFound.collectLatest {
                    progressDialog.dismiss()
                    if (it) {
                        if (addGreenMateViewModel.isModuleAdded()) {
                            findNavController().navigate(R.id.action_serialNumberFragment_to_findModuleFragment)
                        } else {
                            findNavController().navigate(R.id.action_serialNumberFragment2_to_findModuleFragment2)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addGreenMateViewModel.snackBarMessage.collectLatest {
                    progressDialog.dismiss()
                    if (it.isNotEmpty()) {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                            .show()
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