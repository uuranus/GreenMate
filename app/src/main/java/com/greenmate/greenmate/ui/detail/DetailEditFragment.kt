package com.greenmate.greenmate.ui.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.DialogLoadingBinding
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
    private val progressDialog: android.app.AlertDialog by lazy {
        val dialogView = DialogLoadingBinding.inflate(requireActivity().layoutInflater)
        android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

    private var cameraActivityLauncher: ActivityResultLauncher<Intent>? = null
    private var file: File? = null

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
                progressDialog.show()
                val awsCredentials: AWSCredentials =
                    BasicAWSCredentials(
                        "AKIA5RPIQAMQGHQZWFIB",
                        "xw3IkR9m59wYWZ/Wv4cLGcrLy/dDfUqd4I8JgSaq"
                    )

                val s3Client =
                    AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2))

                val transferUtility = TransferUtility.builder().s3Client(s3Client).context(
                    requireActivity().applicationContext
                ).build()
                TransferNetworkLossHandler.getInstance(requireActivity().applicationContext)

                val fileName = "${System.currentTimeMillis()}.jpeg"
                val uploadObserver = transferUtility.upload(
                    "greenmate-test",
                    fileName,
                    file
                ) // (bucket api, file이름, file객체)


                uploadObserver.setTransferListener(object : TransferListener {
                    override fun onStateChanged(id: Int, state: TransferState) {
                        progressDialog.dismiss()
                        if (state === TransferState.COMPLETED) {
                            detailViewModel.saveImageUrl("https://greenmate-test.s3-ap-southeast-2.amazonaws.com/${fileName}")

                            detailViewModel.changeGreenMateInfo()
                        }
                    }

                    override fun onProgressChanged(id: Int, current: Long, total: Long) {
                        val done = (current.toDouble() / total * 100.0).toInt()
                        Log.d("MYTAG", "UPLOAD - - ID: $id, percent done = $done")
                    }

                    override fun onError(id: Int, ex: Exception) {
                        Log.d("MYTAG", "UPLOAD ERROR - - ID: $id - - EX:$ex")
                    }
                })

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
                    file = File(URI(imageFilePath))
                    Glide.with(requireContext())
                        .load(file)
                        .into(binding.greenMateImageView)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}