package com.greenmate.greenmate.adapter.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.greenmate.greenmate.R
import com.greenmate.greenmate.databinding.ItemChatRoomBinding
import com.greenmate.greenmate.model.ChatRoom

class ChatRoomListAdapter(private val onClickListener: (ChatRoom) -> Unit) :
    ListAdapter<ChatRoom, ChatRoomListAdapter.ChatRoomViewHolder>(diffUtil) {

    inner class ChatRoomViewHolder(private val binding: ItemChatRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickListener(currentList[adapterPosition])
            }
        }

        fun bind(data: ChatRoom) {
            binding.data = data
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        return ChatRoomViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_chat_room,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatRoom>() {
            override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
                return oldItem == newItem
            }

        }
    }

}