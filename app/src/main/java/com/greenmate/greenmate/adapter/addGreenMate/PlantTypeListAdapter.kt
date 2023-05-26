package com.greenmate.greenmate.adapter.addGreenMate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.databinding.ItemSelectTypeBinding
import com.greenmate.greenmate.ui.addGreenMate.AddGreenMateViewModel

class PlantTypeListAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<String, PlantTypeListAdapter.PlantTypeViewHolder>(diffUtil) {

    inner class PlantTypeViewHolder(private val binding: ItemSelectTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.run {
                root.setOnFocusChangeListener { _, _ ->
                    clickListener(adapterPosition)
                }
                root.setOnClickListener {

                }
            }
        }

        fun bind(data: String) {
            binding.plantNameTextView.text = data
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PlantTypeListAdapter.PlantTypeViewHolder {
        return PlantTypeViewHolder(
            ItemSelectTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: PlantTypeListAdapter.PlantTypeViewHolder, position: Int) {
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