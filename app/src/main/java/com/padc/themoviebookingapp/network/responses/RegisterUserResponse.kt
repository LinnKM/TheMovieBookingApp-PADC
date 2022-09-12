package com.padc.themoviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.data.vos.UserVO

data class RegisterUserResponse(
    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: UserVO?,

    @SerializedName("token")
    val token: String?
)