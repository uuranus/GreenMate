package com.greenmate.greenmate.model.data

import com.greenmate.greenmate.model.network.GreenMateDTO
import com.greenmate.greenmate.util.USER_ID

data class GreenMate(
    val id: String = "",
    val name: String,
    val type: String,
    val startDate: String = "키우기 시작한 지 1일",
    val light: String = "",
    val humidity: String = "",
    val temperature: String = "",
    val image: Int,
) : java.io.Serializable


fun GreenMate.toDTO(): GreenMateDTO {
    return GreenMateDTO(
        id,
        USER_ID,
        type,
        name
    )
}