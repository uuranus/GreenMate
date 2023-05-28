package com.greenmate.greenmate.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.databinding.ItemDiaryBinding
import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.toUIDiary

class DiaryListAdapter :
    ListAdapter<Diary, DiaryListAdapter.DiaryViewHolder>(diffUtil) {

    inner class DiaryViewHolder(private val binding: ItemDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val listAdapter = DiaryListListAdapter()

        init {
            binding.diaryListRecyclerView.adapter = listAdapter
        }

        fun bind(data: Diary) {
            binding.data = data.toUIDiary()
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
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem == newItem
            }

        }
    }

}