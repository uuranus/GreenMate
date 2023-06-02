package com.greenmate.greenmate.util

import com.greenmate.greenmate.R


fun decideLight(light: Int): String {
//    return when (light) {
//        in 2..1500 -> "좋음"
//        in 0..1 -> "낮음"
//        else -> "높음"
//    }
    return light.toString()
}

fun decideHumidity(humidity: Int): String {
//    return when (humidity) {
//        in 40..100 -> "좋음"
//        in 0..40 -> "낮음"
//        else -> "높음"
//    }
    return humidity.toString()
}

fun decideTemperature(temp: Int): String {
//    return when (temp) {
//        in 16..26 -> "좋음"
//        in -100..16 -> "낮음"
//        else -> "높음"
//    }
    return temp.toString()
}

fun decideSoilWater(soilWater: Int): String {
//    return when (soilWater) {
//        in 0..500 -> "좋음"
//        in -100..0 -> "낮음"
//        else -> "높음"
//    }

    return soilWater.toString()
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