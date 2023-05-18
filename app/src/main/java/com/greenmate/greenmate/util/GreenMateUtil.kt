package com.greenmate.greenmate.util


fun makeDateString(year: Int, month: Int, date: Int): String {
    return String.format("%04d-%02d-%02d", year, month, date)
}