package com.greenmate.greenmate.model

data class Diary(
    val dateMonth: String,
    val dateDate: String,
    val list: List<Todo>
)
