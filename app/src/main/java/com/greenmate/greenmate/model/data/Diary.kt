package com.greenmate.greenmate.model.data

data class Diary(
    val dateMonth: Int,
    val dateDate: Int,
    val list: MutableList<Todo>,
)
