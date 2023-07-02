package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.DailyDiary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.GreenMateWithUser
import com.greenmate.greenmate.model.data.User
import com.greenmate.greenmate.model.data.toDTO
import com.greenmate.greenmate.model.network.AddDiaryDTO
import com.greenmate.greenmate.model.network.FakeGreenMateService
import com.greenmate.greenmate.model.network.GreenMateImageService
import com.greenmate.greenmate.model.network.GreenMateService
import com.greenmate.greenmate.model.network.LoginDTO
import com.greenmate.greenmate.model.network.ModuleIdStringDTO
import com.greenmate.greenmate.model.network.UpdateImageDTO
import com.greenmate.greenmate.model.network.UpdateNameDTO
import com.greenmate.greenmate.model.network.toDailyDiary
import com.greenmate.greenmate.model.network.toGreenMateWithUser
import com.greenmate.greenmate.model.network.toModuleString
import com.greenmate.greenmate.model.network.toUser
import com.greenmate.greenmate.util.USER_ID
import com.greenmate.greenmate.util.USER_PASSWORD
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class GreenMateDataSource @Inject constructor(
    private val fakeService: FakeGreenMateService,
    private val service: GreenMateService,
    private val imageService: GreenMateImageService,
) {

    suspend fun login(id: String, password: String): Result<User> {
        val loginDTO = LoginDTO(id, password)
        val networkResponse = service.login(loginDTO)
        return if (networkResponse.isSuccessful) {
            networkResponse.body()?.get("user")?.let {
                Result.success(it.toUser())
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun getAllGreenMates(): Result<GreenMateWithUser> {
        val networkResponse = service.getAllGreenMates(LoginDTO(USER_ID, USER_PASSWORD))
        return if (networkResponse.isSuccessful) {
            networkResponse.body()?.let {
                Result.success(it.toGreenMateWithUser())
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun isSerialNumberExist(moduleId: String): Result<Boolean> {
        val networkResponse = service.checkModule(ModuleIdStringDTO(moduleId))
        return if (networkResponse.isSuccessful) {
            networkResponse.body()?.get("result")?.let {
                Result.success(it == IS_SUCCESS)
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

//    suspend fun saveImage(imageName: String, imageByte: ByteArray): Result<String> {
//        val content = RequestBody.create(
//            MediaType.parse("image/*"),
//            imageByte
//        )
//        val photo = MultipartBody.Part.createFormData(
//            "photo",
//            imageName, content
//        )
//
//        val response = imageService.saveImage(
//            imageName, photo, content
//        )
//        return Result.success(imageName)
//    }

    suspend fun addGreenMate(greenMate: GreenMate): Result<String> {
        val networkResponse = service.addGreenMate(greenMate.toDTO())
        return if (networkResponse.isSuccessful) {
            networkResponse.body()?.let {
                Result.success(it.toModuleString())
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun editGreenMateName(moduleId: String, newName: String): Boolean {
        val networkResponse = service.updateGreenmateNickname(UpdateNameDTO(moduleId, newName))
        return true
    }

    suspend fun editGreenMateImage(moduleId: String, newUrl: String): Boolean {
        val networkResponse = service.updateGreenmatePhoto(UpdateImageDTO(moduleId, newUrl))
        return true
    }

    suspend fun deleteGreenMate(moduleId: String): Result<Boolean> {
        val networkResponse = service.deleteGreenMate(ModuleIdStringDTO(moduleId))
        return if (networkResponse.isSuccessful) {
            networkResponse.body()?.let {
                Result.success(true)
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun addDiary(moduleId: String, diary: String): Result<Boolean> {
        val networkResponse = service.addDailyRecord(AddDiaryDTO(moduleId, diary))
        return if (networkResponse.isSuccessful) {
            networkResponse.body()?.let {
                Result.success(true)
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    suspend fun getAllDiaries(moduleId: String): Result<List<DailyDiary>> {
        val networkResponse = service.getAllDiaries(ModuleIdStringDTO(moduleId))
        return if (networkResponse.isSuccessful) {
            networkResponse.body()?.get("dailyRecordList")?.let {
                Result.success(it.map { it2 -> it2.toDailyDiary() })
            } ?: Result.failure(Exception())
        } else {
            Result.failure(Exception())
        }
    }

    companion object {
        const val IS_SUCCESS = 1
        const val IS_FAIL = 0
    }
}