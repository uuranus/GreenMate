package com.greenmate.greenmate.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ItemGardeningActivityBinding
import com.greenmate.greenmate.model.data.Todo

class GardeningActivityListAdapter(private val selectedActivityListener: (Todo) -> Unit) :
    ListAdapter<Todo, GardeningActivityListAdapter.GardeningActivityViewHolder>(diffUtil) {

    inner class GardeningActivityViewHolder(private val binding: ItemGardeningActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                root.setOnClickListener {
                    root.requestFocus()
                }

                root.setOnFocusChangeListener { _, b ->
                    if (b) {
                        imageView.setColorFilter(ContextCompat.getColor(root.context,
                            R.color.white))
                        selectedActivityListener(currentList[adapterPosition])
                    } else {
                        imageView.setColorFilter(ContextCompat.getColor(root.context,
                            R.color.primary_color))
                    }
                }
            }
        }

        fun bind(data: Todo) {
            binding.data = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardeningActivityViewHolder {
        return GardeningActivityViewHolder(
            ItemGardeningActivityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GardeningActivityViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

        }
    }
}