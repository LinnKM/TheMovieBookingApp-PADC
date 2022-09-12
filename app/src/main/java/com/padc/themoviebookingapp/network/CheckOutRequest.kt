package com.padc.themoviebookingapp.network

import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.data.vos.SnackVO

data class CheckOutRequest(
    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeSlotId: Int,
    @SerializedName("row")
    val row: String,
    @SerializedName("seat_number")
    val seatNumber: String,
    @SerializedName("booking_date")
    val bookingDate: String,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("movie_id")
    val movieId: Int,
    @SerializedName("card_id")
    val cardId: Int,
    @SerializedName("cinema_id")
    val cinemaId: Int,
    @SerializedName("snacks")
    val snacks: List<SnackVO>
)