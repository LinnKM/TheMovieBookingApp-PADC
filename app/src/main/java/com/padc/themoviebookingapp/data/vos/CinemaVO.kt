package com.padc.themoviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class CinemaVO(
    @SerializedName("cinema_id")
    val cinema_id: Int?,
    @SerializedName("cinema")
    val cinema: String?,
    @SerializedName("timeslots")
    val timeSlots: List<TimeSlotVO>?
)