package com.greenmate.greenmate.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.FragmentLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding: FragmentLoadingBinding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            for (i in 0 until 3) {
                binding.loadingImageView.setImageResource(if (i % 2 == 0) R.drawable.loading1 else R.drawable.loading2)
                binding.loadingTextView.text =
                    if (i == 1) "거의 다 끝났어요! 조금만 기다려주세요" else if (i == 0) "요정이 메이트를 측정하고 있어요" else "같이 성장해요"
                for (j in 0 until 10) {
                    binding.progressBar.progress =
                        (binding.progressBar.progress + 10 / 3).coerceAtMost(100)
                    delay(80)
                }
            }
            mainViewModel.setIsDataLoaded(true)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}