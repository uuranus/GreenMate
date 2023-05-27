package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.Todo
import javax.inject.Inject

class GreenMateRepository @Inject constructor(
    private val dataSource: GreenMateDataSource,
) {
    fun getAllGreenMates(): List<GreenMate> {
        return dataSource.getAllGreenMates()
    }

    fun addGreenMate(greenMate: GreenMate): Boolean {
        return dataSource.addGreenMate(greenMate)
    }

    fun editGreenMate(greenMate: GreenMate): GreenMate {
        return dataSource.editGreenMate(greenMate)
    }

    fun deleteGreenMate(id: String): Boolean {
        return dataSource.deleteGreenMate(id)
    }

    fun findSerialNumber(number: String): Boolean {
        return dataSource.findSerialNumber(number)
    }

    fun addDiary(id: String, diary: String): String {
        return dataSource.addDiary(id,diary)
    }

    fun getAllDiaries(id:String):List<Diary>{
        return dataSource.getAllDiaries(id)
    }
}