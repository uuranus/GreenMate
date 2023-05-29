package com.greenmate.greenmate.model.data

import com.greenmate.greenmate.model.network.AddGreenMateDTO
import com.greenmate.greenmate.util.USER_ID

data class GreenMateWithUser(
    val userData: User,
    val greenMates: List<GreenMate>,
)

data class GreenMate(
    val id: String = "",
    val name: String,
    val type: String,
    val startDate: String = "키우기 시작한 지 1일",
    val light: String = "",
    val humidity: String = "",
    val temperature: String = "",
    val soilWater: String = "",
    val image: Int,
) : java.io.Serializable

fun GreenMate.toDTO(): AddGreenMateDTO {
    return AddGreenMateDTO(
        id,
        USER_ID,
        type,
        name
    )
}