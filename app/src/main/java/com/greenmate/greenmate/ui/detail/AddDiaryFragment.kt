package com.greenmate.greenmate.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.FragmentAddDiaryBinding

class AddDiaryFragment : Fragment() {
    private var _binding: FragmentAddDiaryBinding? = null
    private val binding: FragmentAddDiaryBinding get() = _binding!!

    private val calendarBottomSheetFragment = AddDiaryBottomSheetFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_diary, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dateTextView.setOnClickListener {
            calendarBottomSheetFragment.show(requireActivity().supportFragmentManager,
                "CHOOSE_DATE")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}