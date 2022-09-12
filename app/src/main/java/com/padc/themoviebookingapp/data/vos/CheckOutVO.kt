package com.padc.themoviebookingapp.data.vos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CheckOutVO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("booking_no")
    val bookingNo: String?,
    @SerializedName("booking_date")
    val bookingDate: String?,
    @SerializedName("row")
    val row: String?,
    @SerializedName("seat")
    val seat: String?,
    @SerializedName("total_seat")
    val totalSeat: Int?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("movie_id")
    val movieId: Int?,
    @SerializedName("cinema_id")
    val cinemaId: Int?,
    @SerializedName("username")
    val userName: String?,
    @SerializedName("timeslot")
    val timeSlot: TimeSlotVO?,
    @SerializedName("snacks")
    val snacks: List<SnackVO>?
): Serializable