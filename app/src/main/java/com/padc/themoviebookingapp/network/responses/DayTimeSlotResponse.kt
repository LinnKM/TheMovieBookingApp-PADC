package com.padc.themoviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.data.vos.CinemaVO

data class DayTimeSlotResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<CinemaVO>?
)