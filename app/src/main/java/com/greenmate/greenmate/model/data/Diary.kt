package com.greenmate.greenmate.model.data

data class Diary(
    val dateMonth: String,
    val dateDate: String,
    val list: MutableList<Todo>
)
