package com.greenmate.greenmate.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.detail.GardeningActivityListAdapter
import com.greenmate.greenmate.databinding.FragmentAddDiaryBinding
import com.greenmate.greenmate.model.Todo
import com.greenmate.greenmate.util.makeDateString

class AddDiaryFragment : Fragment() {
    private var _binding: FragmentAddDiaryBinding? = null
    private val binding: FragmentAddDiaryBinding get() = _binding!!
    private val addDiaryViewModel: AddDiaryViewModel by viewModels()
    private val gardeningActivityAdapter = GardeningActivityListAdapter()

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
        binding.run {
            vm = addDiaryViewModel
            lifecycleOwner = this@AddDiaryFragment.viewLifecycleOwner

            gardeningActivityRecyclerView.adapter = gardeningActivityAdapter
            gardeningActivityRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3,)
            gardeningActivityAdapter.submitList(listOf(
                Todo("물주기", R.drawable.icon_water),
                Todo("환기하기", R.drawable.icon_wind),
                Todo("영양관리", R.drawable.icon_medical),
                Todo("물주기", R.drawable.icon_water)))

            dateTextView.setOnClickListener {
                calendarBottomSheetFragment.show(requireActivity().supportFragmentManager,
                    "CHOOSE_DATE")
                calendarBottomSheetFragment.setOnDateClickListener { year, month, date ->
                    dateTextView.text = makeDateString(year, month, date)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}