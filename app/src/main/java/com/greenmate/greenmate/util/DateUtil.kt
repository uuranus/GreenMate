package com.greenmate.greenmate.util

import java.text.SimpleDateFormat
import java.util.Calendar


data class Date(
    val year: Int,
    val month: Int,
    val date: Int,
)

private val today = Calendar.getInstance()
private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

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

fun getTimeDistance(startDate: String?): String {
    startDate ?: return "키우기 시작한 지 1일"

    val sDate = serverDateFormat.parse(startDate)?.time ?: System.currentTimeMillis()
    val tDate = today.timeInMillis

    val diff = (tDate - sDate) / (24 * 60 * 60 * 1000)
    return "키우기 시작한 지 ${diff}일"
}