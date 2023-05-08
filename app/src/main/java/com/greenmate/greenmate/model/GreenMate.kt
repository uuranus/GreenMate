package com.greenmate.greenmate.model

data class GreenMate(
    val name: String,
    val type:String,
    val light: String="",
    val humidity: String= "",
    val temperature: String ="",
    val image: Int
)