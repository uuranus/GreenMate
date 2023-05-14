package com.greenmate.greenmate.model

data class GreenMate(
    val name: String,
    val type: String,
    val startDate:String="키우기 시작한 지 1일",
    val light: String = "",
    val humidity: String = "",
    val temperature: String = "",
    val image: Int
) : java.io.Serializable