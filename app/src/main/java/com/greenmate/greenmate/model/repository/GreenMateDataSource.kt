package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.DailyDiary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.GreenMateWithUser
import com.greenmate.greenmate.model.data.User
import com.greenmate.greenmate.model.data.toDTO
import com.greenmate.greenmate.model.network.AddDiaryDTO
import com.greenmate.greenmate.model.network.FakeGreenMateService
import com.greenmate.greenmate.model.network.GreenMateService
import com.greenmate.greenmate.model.network.LoginDTO
import com.greenmate.greenmate.model.network.ModuleIdStringDTO
import com.greenmate.greenmate.model.network.toDailyDiary
import com.greenmate.greenmate.model.network.toGreenMateWithUser
import com.greenmate.greenmate.model.network.toModuleString
import com.greenmate.greenmate.model.network.toUser
import com.greenmate.greenmate.util.USER_ID
import com.greenmate.greenmate.util.USER_PASSWORD
import javax.inject.Inject

class GreenMateDataSource @Inject constructor(
    private val fakeService: FakeGreenMateService,
    private val service: GreenMateService,
//    private val imageService: GreenMateImageService,
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

    suspend fun getAllGreenMates(): Result<GreenMateWithUser> {
        val response = service.getAllGreenMates(LoginDTO(USER_ID, USER_PASSWORD))
        println("getAllGreens ${response.body()}")
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it.toGreenMateWithUser())
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
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
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it.toModuleString())
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun editGreenMate(imageName: String, newUrl: ByteArray): Boolean {
//        val response = imageService.changePlantImage(
//            imageName, RequestBody.create(
//                MediaType.parse("image/jpeg"),
//                newUrl
//            )
//        )
//        println("response ${response.body()}")
        return true
    }

    suspend fun deleteGreenMate(moduleId: String): Result<Boolean> {
        val response = service.deleteGreenMate(ModuleIdStringDTO(moduleId))
        println("delete $response")
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(true)
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun addDiary(moduleId: String, diary: String): Result<Boolean> {
        val response = service.addDailyRecord(AddDiaryDTO(moduleId, diary))
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(true)
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun getAllDiaries(moduleId: String): Result<List<DailyDiary>> {
        val response = service.getAllDiaries(ModuleIdStringDTO(moduleId))
        return if (response.isSuccessful) {
            response.body()?.get("dailyRecordList")?.let {
                Result.success(it.map { it2 -> it2.toDailyDiary() })
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }
}