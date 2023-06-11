package com.greenmate.greenmate.model.network

import com.google.gson.annotations.SerializedName
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.DailyDiary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.GreenMateWithUser
import com.greenmate.greenmate.model.data.User
import com.greenmate.greenmate.util.decideHumidity
import com.greenmate.greenmate.util.decideLight
import com.greenmate.greenmate.util.decideSoilWater
import com.greenmate.greenmate.util.decideTemperature
import com.greenmate.greenmate.util.getTimeDistance
import com.greenmate.greenmate.util.toDateString

data class UserDTO(
    @SerializedName("name") val userName: String,
    @SerializedName("id") val userId: String,
    @SerializedName("password") val password: String,
    @SerializedName("birth") val userBirth: String,
    @SerializedName("photo") val userProfileImg: String?,
)

data class LoginDTO(
    @SerializedName("id") val id: String,
    @SerializedName("password") val password: String,
)

data class AddGreenMateDTO(
    @SerializedName("moduleId") val moduleId: String = "",
    @SerializedName("userId") val userId: String,
    @SerializedName("plantName") val plantName: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("photo") val plantPhoto: String,
)

data class AllGreenMatesDTO(
    @SerializedName("user") val userData: UserDTO?,
    @SerializedName("greenmateList") val greenmates: List<GreenMateDTO>,
)

data class GreenMateDTO(
    @SerializedName("moduleId") val moduleId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("plantName") val plantName: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("time") val startDate: String,
    @SerializedName("soilWater") val soilWater: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temperature") val temperature: Int,
    @SerializedName("illuminance") val illuminance: Int,
    @SerializedName("photo") val photo: String?,
)

data class ModuleIdStringDTO(
    @SerializedName("moduleId") val moduleId: String,
)

data class UserIdStringDTO(
    @SerializedName("userId") val userId: String,
)

data class DailyDiaryDTO(
    @SerializedName("time") val time: String,
    @SerializedName("moduleId") val moduleId: String,
    @SerializedName("dailyRecord") val diaryName: String,
)

data class AddDiaryDTO(
    @SerializedName("moduleId") val moduleId: String,
    @SerializedName("dailyRecord") val diaryName: String,
)

data class UpdateNameDTO(
    @SerializedName("moduleId") val moduleId: String,
    @SerializedName("nickname") val plantName: String,
)

data class UpdateImageDTO(
    @SerializedName("moduleId") val moduleId: String,
    @SerializedName("photo") val plantImgUrl: String,
)

/* Extensions */
fun UserDTO.toUser(): User {
    return User(
        name = userName,
        id = userId,
        password = password,
        birth = userBirth,
        profileImg = userProfileImg ?: ""
    )
}

fun ModuleIdStringDTO.toModuleString(): String {
    return moduleId
}

fun UserIdStringDTO.toUserIdString(): String {
    return userId
}

fun DailyDiaryDTO.toDailyDiary(): DailyDiary {
    return DailyDiary(
        this.time.toDateString(),
        diaryName
    )
}

val images = arrayOf(R.drawable.plant1, R.drawable.plant2)
fun GreenMateDTO.toGreenMate(): GreenMate {
    return GreenMate(
        id = moduleId,
        name = nickname,
        type = plantName,
        startDate = getTimeDistance(startDate),
        light = decideLight(illuminance),
        humidity = decideHumidity(soilWater),
        temperature = decideTemperature(temperature),
        soilWater = decideSoilWater(soilWater),
        image = moduleId ?: ""
    )
}

fun AllGreenMatesDTO.toGreenMateWithUser(): GreenMateWithUser {
    return GreenMateWithUser(
        userData = userData?.toUser() ?: User("PEPL", "masterUser", "greenmate1234", "20200103"),
        greenMates = greenmates.map { it.toGreenMate() }
    )
}