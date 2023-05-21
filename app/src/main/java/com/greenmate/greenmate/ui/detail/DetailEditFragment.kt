package com.greenmate.greenmate.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
    private val detailEditViewModel: DetailEditViewModel by viewModels()
    private val detailDetailArgs: DetailEditFragmentArgs by navArgs()
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
                detailEditViewModel.deleteGreenMate() //TODO viewModel 합치기
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
            vm = detailEditViewModel
            lifecycleOwner = this@DetailEditFragment.viewLifecycleOwner

            saveButton.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        detailEditViewModel.setSelectedGreenMateInfo(detailDetailArgs.greenMateImageUrl)

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