package com.greenmate.greenmate.ui.detail

import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.detail.GardeningActivityListAdapter
import com.greenmate.greenmate.databinding.FragmentAddDiaryBinding
import com.greenmate.greenmate.model.data.Todo
import com.greenmate.greenmate.util.makeFullDateString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddDiaryFragment : Fragment() {
    private var _binding: FragmentAddDiaryBinding? = null
    private val binding: FragmentAddDiaryBinding get() = _binding!!
    private val addDiaryViewModel: AddDiaryViewModel by viewModels()
    private val navArgs: AddDiaryFragmentArgs by navArgs()
    private val gardeningActivityAdapter = GardeningActivityListAdapter {
        addDiaryViewModel.setGardeningActivity(it)
    }

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

        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.run {
            setupWithNavController(findNavController(), appBarConfiguration)
            setNavigationIcon(R.drawable.icon_back_arrow)
            setTitleTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.transparent
                )
            )
        }

        binding.run {
            vm = addDiaryViewModel
            lifecycleOwner = this@AddDiaryFragment.viewLifecycleOwner

            gardeningActivityRecyclerView.adapter = gardeningActivityAdapter
            gardeningActivityRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            gardeningActivityAdapter.submitList(
                listOf(
                    Todo("물주기", R.drawable.icon_water),
                    Todo("환기하기", R.drawable.icon_wind),
                    Todo("영양관리", R.drawable.icon_medical),
                    Todo("물주기", R.drawable.icon_water)
                )
            )

            dateTextView.setOnClickListener {
                calendarBottomSheetFragment.show(
                    requireActivity().supportFragmentManager,
                    "CHOOSE_DATE"
                )
                calendarBottomSheetFragment.setOnDateClickListener { year, month, date ->
                    dateTextView.text = makeFullDateString(year, month, date)
                }
            }

            saveButton.setOnClickListener {
                addDiaryViewModel.saveNewGardening()
            }


            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    addDiaryViewModel.isSaveSuccess.collectLatest {
                        if (it) {
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }

        addDiaryViewModel.setCurrentId(navArgs.currentId)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}