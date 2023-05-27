package com.greenmate.greenmate.util

import java.util.Calendar


data class Date(
    val year: Int,
    val month: Int,
    val date: Int,
)

private val today = Calendar.getInstance()

fun makeFullDateString(year: Int, month: Int, date: Int): String {
    return String.format("%04d-%02d-%02d", year, month, date)
}

fun makeMonthString(month: Int): String {
    return String.format("%02d", month)
}


fun makeDateString(date: Int): String {
    return String.format("%02d", date)
}

fun getToday(): Date {
    return Date(
        today.get(Calendar.YEAR),
        today.get(Calendar.MONTH) + 1,
        today.get(Calendar.DATE)
    )
}