package com.padc.themoviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.data.vos.MovieVO

data class MovieDetailResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val movie: MovieVO?
)