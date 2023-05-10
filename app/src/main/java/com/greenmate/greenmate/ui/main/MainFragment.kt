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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.GreenMateListAdapter
import com.greenmate.greenmate.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

            mainGreenMateCardView.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(mainViewModel.getSelectedGreenMate())
                findNavController().navigate(action)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.greenMates.collectLatest {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}