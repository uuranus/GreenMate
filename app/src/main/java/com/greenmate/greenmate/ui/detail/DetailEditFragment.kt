package com.greenmate.greenmate.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.DialogYesOrNoBinding
import com.greenmate.greenmate.databinding.FragmentDetailEditBinding
import com.greenmate.greenmate.ui.camera.CameraActivity
import com.greenmate.greenmate.ui.camera.CameraCheckFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI

class DetailEditFragment() : Fragment() {
    private var _binding: FragmentDetailEditBinding? = null
    private val binding: FragmentDetailEditBinding get() = _binding!!
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var dialogView: DialogYesOrNoBinding
    private lateinit var deleteAlertDialog: AlertDialog

    private var cameraActivityLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_edit, container, false)
        dialogView = DialogYesOrNoBinding.inflate(requireActivity().layoutInflater).apply {
            yesButton.setOnClickListener {
                detailViewModel.deleteGreenMate()
                deleteAlertDialog.dismiss()
                findNavController().navigateUp()
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
                detailViewModel.changeGreenMateInfo()
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
                cameraActivityLauncher?.launch(Intent(requireContext(), CameraActivity::class.java))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.snackBarMessage.collectLatest {
                    if (it.isNotEmpty()) {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.isEditSuccess.collectLatest {
                    if (it) {
                        findNavController().navigateUp()
                    }
                }
            }
        }

        cameraActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageFilePath =
                        result.data?.getStringExtra(CameraCheckFragment.IMAGE_NAME_KEY)
                            ?: return@registerForActivityResult
                    val uri = File(URI(imageFilePath))
                    detailViewModel.setImageUrl(uri.readBytes())
                    Glide.with(requireContext())
                        .load(uri)
                        .into(binding.greenMateImageView)

                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}