package com.greenmate.greenmate.ui.camera

import android.app.Activity
import android.app.AlertDialog
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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.DialogLoadingBinding
import com.greenmate.greenmate.databinding.FragmentCameraCheckBinding
import com.greenmate.greenmate.ui.addGreenMate.AddGreenMateViewModel
import com.greenmate.greenmate.util.IMAGE_BASE_URL
import java.io.File
import java.net.URI


class CameraCheckFragment : Fragment() {
    private var _binding: FragmentCameraCheckBinding? = null
    private val binding: FragmentCameraCheckBinding get() = _binding!!
    private var cameraActivityLauncher: ActivityResultLauncher<Intent>? = null
    private val addGreenMateViewModel: AddGreenMateViewModel by activityViewModels()
    private var file: File? = null
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_camera_check, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                println("OK!!! $result")
                if (result.resultCode == Activity.RESULT_OK) {

                    val imageFilePath =
                        result.data?.getStringExtra(IMAGE_NAME_KEY)
                            ?: return@registerForActivityResult
                    file = File(URI(imageFilePath))
                    Glide.with(requireContext())
                        .load(file)
                        .into(binding.photoImageView)
                }
            }

        binding.run {
            photoImageView.setOnClickListener {
                cameraActivityLauncher?.launch(
                    Intent(
                        requireContext(),
                        CameraActivity::class.java
                    )
                )
            }
            retakeButton.setOnClickListener {
                cameraActivityLauncher?.launch(
                    Intent(
                        requireContext(),
                        CameraActivity::class.java
                    )
                )
            }

            continueButton.setOnClickListener {
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

                val moduleId = addGreenMateViewModel.getModuleId()
                val fileName = "${moduleId}.jpeg"
                val uploadObserver = transferUtility.upload(
                    "greenmate-test",
                    fileName,
                    file
                ) // (bucket api, file이름, file객체)
                println("fileName ${fileName}")


                uploadObserver.setTransferListener(object : TransferListener {
                    override fun onStateChanged(id: Int, state: TransferState) {
                        if (state === TransferState.COMPLETED) {
                            addGreenMateViewModel.saveImage("${IMAGE_BASE_URL}${fileName}")

                            if (addGreenMateViewModel.isModuleAdded()) {
                                findNavController().navigate(R.id.action_cameraCheckFragment_to_makeNameFragment)
                            } else {
                                findNavController().navigate(R.id.action_cameraCheckFragment2_to_makeNameFragment2)
                            }
                        }
                    }

                    override fun onProgressChanged(id: Int, current: Long, total: Long) {
                        val done = (current.toDouble() / total * 100.0).toInt()
                        Log.d("MYTAG", "UPLOAD - - ID: $id, percent done = $done")
                        if (done == 100) {
                            progressDialog.dismiss()
                        }
                    }

                    override fun onError(id: Int, ex: Exception) {
                        Log.d("MYTAG", "UPLOAD ERROR - - ID: $id - - EX:$ex")
                    }
                })

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val IMAGE_NAME_KEY = "IMAGE_NAME_KEY"
    }
}