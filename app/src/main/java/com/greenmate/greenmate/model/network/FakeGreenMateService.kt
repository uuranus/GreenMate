package com.greenmate.greenmate.model.network

import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.GreenMate

object FakeGreenMateService {

    //    private val greenMates = mutableListOf(
//        GreenMate("1", "그리니", "식물1", "키우기 시작한지 1일째", "좋음", "좋음", "좋음", R.drawable.plant1),
//        GreenMate("2", "그리니2", "식물2", "키우기 시작한지 15일째", "좋음", "좋음", "나쁨", R.drawable.plant1),
//        GreenMate("3", "그린조아", "식물3", "키우기 시작한지 52일째", "좋음", "나쁨", "좋음", R.drawable.plant2),
//        GreenMate("4", "그리니4", "식물4", "키우기 시작한지 24일째", "좋음", "좋음", "좋음", R.drawable.plant2),
//        GreenMate("5", "그린조아2", "식물5", "키우기 시작한지 10일째", "좋음", "보통", "보통", R.drawable.plant1),
//        GreenMate("6", "그린조아5", "식물6", "키우기 시작한지 30일째", "나쁨", "좋음", "보통", R.drawable.plant2)
//    )
    private val greenMates = mutableListOf<GreenMate>()

    fun getGreenMates(): List<GreenMate> {
        return greenMates
    }

    fun addGreenMate(greenMate: GreenMate): Boolean {
        greenMates.add(greenMate)
        return true
    }

    fun editGreenMateInfo(greenMate: GreenMate): Boolean {
        greenMates.find { it.name == greenMate.id }?.let {
            it.copy(name = greenMate.name, image = greenMate.image)
        }
        return true
    }

    fun deleteGreenMate(greenMate: GreenMate): Boolean {
        val target = greenMates.find { it.id == greenMate.id }
        greenMates.remove(target)
        return true
    }
}