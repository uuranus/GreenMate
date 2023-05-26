package com.greenmate.greenmate.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.DialogDeleteGreenMateBinding
import com.greenmate.greenmate.databinding.FragmentDetailEditBinding
import com.greenmate.greenmate.ui.camera.CameraActivity

class DetailEditFragment() : Fragment() {
    private var _binding: FragmentDetailEditBinding? = null
    private val binding: FragmentDetailEditBinding get() = _binding!!
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var dialogView: DialogDeleteGreenMateBinding
    private lateinit var deleteAlertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailEditBinding.inflate(inflater, container, false)
        dialogView = DialogDeleteGreenMateBinding.inflate(requireActivity().layoutInflater).apply {
            yesButton.setOnClickListener {
                detailViewModel.deleteGreenMate() //TODO viewModel 합치기
                deleteAlertDialog.dismiss()

            }
            noButton.setOnClickListener {
                deleteAlertDialog.dismiss()
            }
        }
        deleteAlertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView.root)
            .setCancelable(true)
            .create()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vm = detailViewModel
            lifecycleOwner = this@DetailEditFragment.viewLifecycleOwner

            saveButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.run {
            setupWithNavController(findNavController(), appBarConfiguration)
            setNavigationIcon(R.drawable.icon_back_arrow)
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            setTitleTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.transparent
                )
            )

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deleteMenu -> {
                        deleteAlertDialog.show()
                        return@setOnMenuItemClickListener true
                    }

                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }

        }

        binding.run {
            greenMateImageView.setOnClickListener {
                val intent = Intent(requireActivity(), CameraActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}