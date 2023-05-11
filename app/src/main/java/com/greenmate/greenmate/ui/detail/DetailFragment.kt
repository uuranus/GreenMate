package com.greenmate.greenmate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.DiaryListAdapter
import com.greenmate.greenmate.adapter.TodoListAdapter
import com.greenmate.greenmate.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val todoAdapter: TodoListAdapter = TodoListAdapter()
    private val diaryAdapter: DiaryListAdapter = DiaryListAdapter()
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.run {
            vm = detailViewModel
            lifecycleOwner = this@DetailFragment.viewLifecycleOwner

            detailViewModel.setCurrentInfo(args.selectedGreenMate)

            toolbar.setupWithNavController(findNavController(), appBarConfiguration)
            toolbar.setTitleTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.transparent
                )
            )
            toolbar.setNavigationIcon(R.drawable.icon_back_arrow)

            todoRecyclerView.adapter = todoAdapter
            diaryRecyclerView.adapter = diaryAdapter

            todoTextView.setOnClickListener {
                detailViewModel.setFocus(true)
            }

            diaryTextView.setOnClickListener {
                detailViewModel.setFocus(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}