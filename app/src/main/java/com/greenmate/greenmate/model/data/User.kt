package com.greenmate.greenmate.model.data

import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.network.UserDTO

data class User(
    val name: String,
    val id: String,
    val password: String,
    val birth: String,
    val profileImg: String = "",
)

fun User.toDTO(): UserDTO {
    return UserDTO(
        userName = name,
        userId = id,
        password = password,
        userBirth = birth,
        userProfileImg = profileImg
    )
}