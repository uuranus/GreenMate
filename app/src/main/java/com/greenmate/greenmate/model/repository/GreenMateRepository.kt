package com.greenmate.greenmate.model.repository

import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.User
import com.greenmate.greenmate.model.data.toDiaryList
import javax.inject.Inject

class GreenMateRepository @Inject constructor(
    private val dataSource: GreenMateDataSource,
) {

    suspend fun login(id: String, password: String): Result<User> {
        return dataSource.login(id, password)
    }

    fun getAllGreenMates(): List<GreenMate> {
        return dataSource.getAllGreenMates()
    }

    suspend fun findSerialNumber(moduleId: String): Result<Boolean> {
        return dataSource.findSerialNumber(moduleId)
    }

    suspend fun addGreenMate(greenMate: GreenMate): Result<String> {
        return dataSource.addGreenMate(greenMate)
    }

    suspend fun editGreenMate(imageName: String, newUrl: ByteArray): Boolean {
        dataSource.editGreenMate(imageName, newUrl)
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