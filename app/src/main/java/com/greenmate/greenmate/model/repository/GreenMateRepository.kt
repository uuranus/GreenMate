package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.GreenMateWithUser
import com.greenmate.greenmate.model.data.User
import com.greenmate.greenmate.model.data.toDiaryList
import java.io.File
import javax.inject.Inject

class GreenMateRepository @Inject constructor(
    private val dataSource: GreenMateDataSource,
) {

    suspend fun login(id: String, password: String): Result<User> {
        return dataSource.login(id, password)
    }

    suspend fun getAllGreenMates(): Result<GreenMateWithUser> {
        return dataSource.getAllGreenMates()
    }

    suspend fun findSerialNumber(moduleId: String): Result<Boolean> {
        return dataSource.findSerialNumber(moduleId)
    }

    suspend fun saveGreenMateImage(imageName: String, imageByte: ByteArray): Result<String> {
        return dataSource.saveImage(imageName, imageByte)
    }

    suspend fun addGreenMate(greenMate: GreenMate): Result<String> {
        return dataSource.addGreenMate(greenMate)
    }

    suspend fun editGreenMateName(moduleId: String, newName: String): Boolean {
        dataSource.editGreenMateName(moduleId, newName)
        return true
    }

    suspend fun editGreenMateImage(moduleId: String, newUrl: String): Boolean {
        dataSource.editGreenMateImage(moduleId, newUrl)
        return true
    }

    suspend fun deleteGreenMate(moduleId: String): Result<Boolean> {
        return dataSource.deleteGreenMate(moduleId)
    }

    suspend fun addDiary(id: String, diary: String): Result<Boolean> {
        return dataSource.addDiary(id, diary)
    }

    suspend fun getAllDiaries(moduleId: String): Result<List<Diary>> {
        val result = dataSource.getAllDiaries(moduleId)
        if (result.isFailure) return Result.failure(Exception())

        return result.getOrNull()?.let {
            Result.success(it.toDiaryList())
        } ?: Result.failure(Exception())
    }
}