package com.padc.themoviebookingapp.data.vos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimeSlotVO(
    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeSlot: Int?,
    @SerializedName("start_time")
    val startTime: String?,
    var isSelected: Boolean = false
): Serializable