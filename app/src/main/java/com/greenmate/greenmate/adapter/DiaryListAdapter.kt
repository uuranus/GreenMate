package com.greenmate.greenmate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.databinding.ItemDiaryBinding
import com.greenmate.greenmate.model.Diary

class DiaryListAdapter :
    ListAdapter<Diary, DiaryListAdapter.DiaryViewHolder>(diffUtil) {

    inner class DiaryViewHolder(private val binding: ItemDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val listAdapter = DiaryListListAdapter()

        init {
            binding.diaryListRecyclerView.adapter = listAdapter
        }

        fun bind(data: Diary) {
            binding.data = data
            listAdapter.submitList(data.list)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return DiaryViewHolder(
            ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Diary>() {
            override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem.dateDate == newItem.dateDate
            }

            override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem == newItem
            }

        }
    }

}