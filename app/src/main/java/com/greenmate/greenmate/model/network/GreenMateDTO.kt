package com.greenmate.greenmate.model.network

import com.google.gson.annotations.SerializedName
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.User

data class UserDTO(
    @SerializedName("name") val userName: String,
    @SerializedName("id") val userId: String,
    @SerializedName("password") val password: String,
    @SerializedName("birth") val userBirth: String,
)

data class LoginDTO(
    @SerializedName("id") val id:String,
    @SerializedName("password") val password: String
)

data class GreenMateDTO(
    @SerializedName("moduleId") val moduleId: String = "",
    @SerializedName("userId") val userId: String,
    @SerializedName("plantName") val plantName: String,
    @SerializedName("nickname") val nickname: String,
)

data class ModuleIdStringDTO(
    @SerializedName("moduleId") val moduleId: String,
)

data class UserIdStringDTO(
    @SerializedName("userId") val userId: String,
)

fun UserDTO.toUser(): User {
    return User(
        name = userName,
        id = userId,
        password = password,
        birth = userBirth
    )
}

fun ModuleIdStringDTO.toModuleString(): String {
    return moduleId
}

fun UserIdStringDTO.toUserIdString(): String {
    return userId
}