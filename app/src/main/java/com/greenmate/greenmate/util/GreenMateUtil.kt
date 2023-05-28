package com.greenmate.greenmate.util

import com.greenmate.greenmate.R
import java.text.SimpleDateFormat
import java.util.Calendar


data class Date(
    val year: Int,
    val month: Int,
    val date: Int,
)

private val today = Calendar.getInstance()
private val serverDateFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss")

fun makeFullDateString(year: Int, month: Int, date: Int): String {
    return String.format("%04d-%02d-%02d", year, month, date)
}

fun getMonth(date: String): Int {
    return date.substring(5, 7).toInt()
}

fun getDate(date: String): Int {
    return date.substring(8, 10).toInt()
}

fun getToday(): Date {
    return Date(
        today.get(Calendar.YEAR),
        today.get(Calendar.MONTH) + 1,
        today.get(Calendar.DATE)
    )
}

fun String.toDateString(): String {
    val date = serverDateFormat.parse(this)
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    return makeFullDateString(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DATE)
    )
}

fun getTodoIcon(diaryName: String): Int {
    return when (diaryName) {
        "물주기" -> {
            R.drawable.icon_water
        }

        "환기하기" -> {
            R.drawable.icon_wind
        }

        "영양관리" -> {
            R.drawable.icon_medical
        }

        else -> {
            R.drawable.icon_water
        }
    }
}