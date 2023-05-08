package com.greenmate.greenmate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.DiaryListAdapter
import com.greenmate.greenmate.adapter.TodoListAdapter
import com.greenmate.greenmate.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private lateinit var todoAdapter: TodoListAdapter
    private lateinit var diaryAdapter: DiaryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoAdapter = TodoListAdapter()
        diaryAdapter = DiaryListAdapter()

        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.run {
            toolbar.setupWithNavController(findNavController(), appBarConfiguration)
            toolbar.title = ""
            toolbar.setNavigationIcon(R.drawable.icon_back_arrow)

            todoRecyclerView.adapter = todoAdapter
            diaryRecyclerView.adapter = diaryAdapter

            todoRecyclerView.setOnClickListener {

            }

            diaryRecyclerView.setOnClickListener {
            }

        }

        todoAdapter.submitList(listOf("물주기", "환기하기", "영양관리"))
        diaryAdapter.submitList(listOf("물주기", "환기하기", "영양관리"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}