package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.User
import com.greenmate.greenmate.model.data.toDTO
import com.greenmate.greenmate.model.network.FakeGreenMateService
import com.greenmate.greenmate.model.network.GreenMateService
import com.greenmate.greenmate.model.network.LoginDTO
import com.greenmate.greenmate.model.network.ModuleIdStringDTO
import com.greenmate.greenmate.model.network.toModuleString
import com.greenmate.greenmate.model.network.toUser
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.log

class GreenMateDataSource @Inject constructor(
    private val fakeService: FakeGreenMateService,
    private val service: GreenMateService,
) {

    suspend fun login(id: String, password: String): Result<User> {
        val loginDTO = LoginDTO(id, password)
        val response = service.login(loginDTO)
        return if (response.isSuccessful) {
            response.body()?.get("user")?.let {
                Result.success(it.toUser())
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    fun getAllGreenMates(): List<GreenMate> {
        return fakeService.getGreenMates()
    }

    suspend fun findSerialNumber(moduleId: String): Result<Boolean> {
        val response = service.checkModule(ModuleIdStringDTO(moduleId))
        return if (response.isSuccessful) {
            response.body()?.get("result")?.let {
                Result.success(it == 1)
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun addGreenMate(greenMate: GreenMate): Result<String> {
        val response = service.addGreenMate(greenMate.toDTO())
        println("Response $response")
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it.toModuleString())
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    fun editGreenMate(greenMate: GreenMate): GreenMate {
        return fakeService.editGreenMateInfo(greenMate)
    }

    fun deleteGreenMate(id: String): Boolean {
        return fakeService.deleteGreenMate(id)
    }

    fun addDiary(id: String, diary: String): String {
        return fakeService.addDiary(id, diary)
    }

    fun getAllDiaries(id: String): List<Diary> {
        return fakeService.getAllDiaries(id)
    }
}