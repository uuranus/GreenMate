package com.greenmate.greenmate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.TodoListAdapter
import com.greenmate.greenmate.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var todoAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoListAdapter()

        binding.todoRecyclerView.adapter = todoAdapter
        todoAdapter.submitList(listOf("물주기", "환기하기", "영양관리"))

    }

}