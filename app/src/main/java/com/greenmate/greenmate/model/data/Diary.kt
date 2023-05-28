package com.greenmate.greenmate.model.data

import com.greenmate.greenmate.util.getDate
import com.greenmate.greenmate.util.getMonth
import com.greenmate.greenmate.util.getTodoIcon

data class Diary(
    val date: String,
    val list: MutableList<Todo>,
)

data class UIDiary(
    val month: Int,
    val date: Int,
    val list: MutableList<Todo>,
)

data class DailyDiary(
    val date: String,
    val diaryName: String,
)

fun Diary.toUIDiary(): UIDiary {
    return UIDiary(
        getMonth(this.date),
        getDate(this.date),
        this.list
    )
}

fun List<DailyDiary>.toDiaryList(): List<Diary> {
    val result = HashMap<String, Diary>()

    forEach {
        if (result.containsKey(it.date)) {
            result[it.date]?.list?.add(Todo(it.diaryName, getTodoIcon(it.diaryName)))
        } else {
            result[it.date] =
                Diary(it.date, mutableListOf(Todo(it.diaryName, getTodoIcon(it.diaryName))))
        }
    }
    return result.values.toList()
}