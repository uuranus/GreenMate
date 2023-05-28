package com.greenmate.greenmate.util

val BASE_URL = "http://3.39.202.153:8080"

private var _userId = "masterUser"
val USER_ID: String get() = _userId

fun setUerId(id: String) {
    _userId = id
}