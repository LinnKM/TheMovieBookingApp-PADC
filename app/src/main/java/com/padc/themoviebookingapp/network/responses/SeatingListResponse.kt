package com.padc.themoviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.data.vos.MovieSeatVO

data class SeatingListResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<List<MovieSeatVO>>?
)