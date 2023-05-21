package com.greenmate.greenmate.adapter.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ItemChatFromGreenBinding
import com.greenmate.greenmate.databinding.ItemChatFromMeBinding
import com.greenmate.greenmate.model.Chat

class ChatListAdapter :
    ListAdapter<Chat, RecyclerView.ViewHolder>(diffUtil) {

    inner class ChatFromMeViewHolder(private val binding: ItemChatFromMeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Chat) {
            binding.data = data
        }

    }

    inner class ChatFromGreenViewHolder(private val binding: ItemChatFromGreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Chat) {
            binding.data = data
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            ChatFromGreenViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_chat_from_green,
                    parent,
                    false
                )
            )
        } else {
            ChatFromMeViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_chat_from_me,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChatFromMeViewHolder) {
            holder.bind(currentList[position])
        } else if (holder is ChatFromGreenViewHolder) {
            holder.bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].senderId
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.sendTime == newItem.sendTime
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }

        }
    }

}