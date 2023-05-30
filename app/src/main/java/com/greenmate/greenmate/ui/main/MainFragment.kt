package com.greenmate.greenmate.ui.main

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.main.GreenMateListAdapter
import com.greenmate.greenmate.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GreenMateListAdapter(onClickListener = {
            mainViewModel.setMainGreenMate(it)
        })

        binding.run {

            vm = mainViewModel
            lifecycleOwner = this@MainFragment.viewLifecycleOwner

            greenMateRecyclerView.adapter = adapter

            val string = SpannableString(myGreenMateTextView.text)
            string.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.accent_text_color
                    )
                ), 2, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            myGreenMateTextView.text = string

            addImageButton.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToAddGreenMateActivity(1)
                findNavController().navigate(action)
            }

            mainGreenMateCardView.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(mainViewModel.getSelectedGreenMate())
                val extras =
                    FragmentNavigatorExtras(binding.greenMateImageView to "detailGreenMateImage")
                findNavController().navigate(action, extras)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (mainViewModel.isDataLoaded()) {
            if (mainViewModel.isGreenMateEmpty()) {
                mainViewModel.setIsDataLoaded(false)
                findNavController().navigate(R.id.action_mainFragment_to_notFoundFragment)
            }

            if (mainViewModel.isMainGreenMateEmpty()) {
                mainViewModel.setMainGreenMateByFirst()
            } else {
                mainViewModel.setMainGreenMateCurrent()
            }

            mainViewModel.setIsDataLoaded(false)
        } else {
            findNavController().navigate(R.id.action_mainFragment_to_loadingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}