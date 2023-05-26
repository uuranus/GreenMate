package com.greenmate.greenmate.adapter.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ItemGreenMateBinding
import com.greenmate.greenmate.model.data.GreenMate

class GreenMateListAdapter(private val onClickListener: (GreenMate) -> Unit) :
    ListAdapter<GreenMate, GreenMateListAdapter.GreenMaterViewHolder>(diffUtil) {

    inner class GreenMaterViewHolder(private val binding: ItemGreenMateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickListener(currentList[adapterPosition])
            }
        }

        fun bind(data: GreenMate) {
            binding.data = data
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreenMaterViewHolder {
        return GreenMaterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_green_mate,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GreenMaterViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GreenMate>() {
            override fun areItemsTheSame(oldItem: GreenMate, newItem: GreenMate): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: GreenMate, newItem: GreenMate): Boolean {
                return oldItem == newItem
            }

        }
    }

}