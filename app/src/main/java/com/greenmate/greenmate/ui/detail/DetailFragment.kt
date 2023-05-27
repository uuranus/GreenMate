package com.greenmate.greenmate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.detail.DiaryListAdapter
import com.greenmate.greenmate.adapter.detail.TodoListAdapter
import com.greenmate.greenmate.databinding.DialogDeleteGreenMateBinding
import com.greenmate.greenmate.databinding.FragmentDetailBinding
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.ui.main.MainFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val todoAdapter: TodoListAdapter = TodoListAdapter()
    private val diaryAdapter: DiaryListAdapter = DiaryListAdapter()
    private val detailViewModel: DetailViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var dialogView: DialogDeleteGreenMateBinding
    private lateinit var deleteAlertDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        dialogView = DialogDeleteGreenMateBinding.inflate(requireActivity().layoutInflater).apply {
            yesButton.setOnClickListener {
                detailViewModel.deleteGreenMate()
                deleteAlertDialog.dismiss()

            }
            noButton.setOnClickListener {
                deleteAlertDialog.dismiss()
            }
        }
        deleteAlertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView.root)
            .setCancelable(true)
            .create()

        println("oncreate")
        detailViewModel.setCurrentInfo(args.selectedGreenMate)
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
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editMenu -> {
                        findNavController().navigate(R.id.action_detailFragment_to_detailEditFragment)
                        return@setOnMenuItemClickListener true
                    }

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
            vm = detailViewModel
            lifecycleOwner = this@DetailFragment.viewLifecycleOwner

            todoRecyclerView.adapter = todoAdapter
            diaryRecyclerView.adapter = diaryAdapter

            todoTextView.setOnClickListener {
                detailViewModel.setFocus(true)
            }

            diaryTextView.setOnClickListener {
                detailViewModel.setFocus(false)
            }

            addDiaryImageButton.setOnClickListener {
                val action =
                    DetailFragmentDirections.actionDetailFragmentToAddDiaryFragment(detailViewModel.getCurrentId())
                findNavController().navigate(action)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.isDeleted.collectLatest {
                    if (it) {
                        detailViewModel.setIsDeleted(false)
                        findNavController().navigateUp()
                    }
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        detailViewModel.setCurrentInfoAgain()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}