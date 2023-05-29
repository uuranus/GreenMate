package com.greenmate.greenmate.ui.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.greenmate.greenmate.databinding.FragmentCameraCheckBinding
import java.io.File
import java.net.URI

class CameraCheckFragment : Fragment() {
    private var _binding: FragmentCameraCheckBinding? = null
    private val binding: FragmentCameraCheckBinding get() = _binding!!
    private var cameraActivityLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCameraCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageFilePath =
                        result.data?.getStringExtra(CameraCheckFragment.IMAGE_NAME_KEY)
                            ?: return@registerForActivityResult
                    Glide.with(requireContext())
                        .load(File(URI(imageFilePath)))
                        .into(binding.photoImageView)

                }
            }

        cameraActivityLauncher?.launch(Intent(requireContext(), CameraActivity::class.java))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val IMAGE_NAME_KEY = "IMAGE_NAME_KEY"
    }
}