package com.greenmate.greenmate.util

val BASE_URL = "http://3.39.202.153:8080"
val IMAGE_BASE_URL = "arn:aws:s3:::greenmate-test"

private var _userId = "masterUser"
private var _userPassword = "masterUser"
val USER_ID: String get() = _userId
val USER_PASSWORD: String get() = _userPassword

fun setUerId(id: String) {
    _userId = id
}

fun setUserPassword(password: String) {
    _userPassword = password
}