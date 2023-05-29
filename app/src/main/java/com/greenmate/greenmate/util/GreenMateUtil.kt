package com.greenmate.greenmate.util

import com.greenmate.greenmate.R


fun decideLight(light: Int): String {
    return when (light) {
        in 200..1500 -> "좋음"
        in 0..200 -> "낮음"
        else -> "높음"
    }
}

fun decideHumidity(humidity: Int): String {
    return when (humidity) {
        in 40..70 -> "좋음"
        in 0..40 -> "낮음"
        else -> "높음"
    }
}

fun decideTemperature(temp: Int): String {
    return when (temp) {
        in 16..26 -> "좋음"
        in -100..16 -> "낮음"
        else -> "높음"
    }
}

fun decideSoilWater(soilWater: Int): String {
    return when (soilWater) {
        in 400..500 -> "좋음"
        in 0..400 -> "낮음"
        else -> "높음"
    }
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