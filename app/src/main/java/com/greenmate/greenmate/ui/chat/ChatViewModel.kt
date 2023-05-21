package com.greenmate.greenmate.ui.chat

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.model.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatViewModel : ViewModel() {

    private val _chatName = MutableStateFlow("")
    val chatName: StateFlow<String> get() = _chatName

    private val _chats = MutableStateFlow(mutableListOf(
        Chat(0, "안녕!", "11:20"),
        Chat(0, "반가워요", "11:21"),
        Chat(0, "오늘 하루 어땠어요?", "11:21"),
    ))

    val chats: StateFlow<List<Chat>> get() = _chats

    fun setChatName(name: String) {
        _chatName.value = name
    }

    fun addNewChat(message: String) {
        _chats.value.add(Chat(1, message, "Now"))
    }
}