package com.padc.themoviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.data.vos.DateVO
import com.padc.themoviebookingapp.data.vos.MovieVO

data class MovieListResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieVO>?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val movieList: List<MovieVO>?,
)