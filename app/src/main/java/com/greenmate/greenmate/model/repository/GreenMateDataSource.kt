package com.greenmate.greenmate.model.repository

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

    fun editGreenMate(greenMate: GreenMate):Boolean{
        return service.editGreenMateInfo(greenMate)
    }
}