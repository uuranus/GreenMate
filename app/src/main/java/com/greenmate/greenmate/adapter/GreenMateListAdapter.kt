package com.greenmate.greenmate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.databinding.ItemGreenMateBinding

class GreenMateListAdapter() :
    ListAdapter<String, GreenMateListAdapter.GreenMaterViewHolder>(diffUtil) {

    inner class GreenMaterViewHolder(private val binding: ItemGreenMateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreenMaterViewHolder {
        return GreenMaterViewHolder(
            ItemGreenMateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GreenMaterViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

}