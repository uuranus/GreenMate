package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.GreenMate
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

    fun editGreenMate(greenMate: GreenMate): Boolean {
        return dataSource.editGreenMate(greenMate)
    }

    fun deleteGreenMate(id:String):Boolean{
        return dataSource.deleteGreenMate(id)
    }
}