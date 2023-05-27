package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.network.FakeGreenMateService
import javax.inject.Inject

class GreenMateDataSource @Inject constructor(
    private val service: FakeGreenMateService,
) {

    fun getAllGreenMates(): List<GreenMate> {
        return service.getGreenMates()
    }

    fun addGreenMate(greenMate: GreenMate): Boolean {
        return service.addGreenMate(greenMate)
    }

    fun editGreenMate(greenMate: GreenMate): GreenMate {
        return service.editGreenMateInfo(greenMate)
    }

    fun deleteGreenMate(id: String): Boolean {
        return service.deleteGreenMate(id)
    }

    fun findSerialNumber(number: String): Boolean {
        return service.isSerialNumberExist(number)
    }

    fun addDiary(id: String, diary: String): String {
        return service.addDiary(id,diary)
    }

    fun getAllDiaries(id:String):List<Diary>{
        return service.getAllDiaries(id)
    }
}