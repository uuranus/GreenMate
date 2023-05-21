package com.greenmate.greenmate.model

data class ChatRoom(
    val roomId: Int,
    val imageUrl: Int,
    val name: String,
    val lastMsg: String,
    val lastMsgTime: String,
) : java.io.Serializable