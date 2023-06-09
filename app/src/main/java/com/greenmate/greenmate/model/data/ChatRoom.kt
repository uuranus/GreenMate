package com.greenmate.greenmate.model.data

data class ChatRoom(
    val roomId: Int,
    val imageUrl: String = "",
    val name: String,
    val lastMsg: String,
    val lastMsgTime: String,
) : java.io.Serializable