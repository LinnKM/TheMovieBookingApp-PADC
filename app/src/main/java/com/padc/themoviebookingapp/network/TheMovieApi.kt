package com.padc.themoviebookingapp.network

import com.padc.themoviebookingapp.network.responses.MovieListResponse
import com.padc.themoviebookingapp.utils.API_GET_NOW_PLAYING_MOVIES
import com.padc.themoviebookingapp.utils.API_GET_UPCOMING_MOVIES
import com.padc.themoviebookingapp.utils.API_KEY
import com.padc.themoviebookingapp.utils.PARAM_API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieApi {

    // Call Now Playing Movies
    @GET(API_GET_NOW_PLAYING_MOVIES)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY
    ): Call<MovieListResponse>

    // Call UpComing Movies
    @GET(API_GET_UPCOMING_MOVIES)
    fun getUpComingMovies(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY
    ): Call<MovieListResponse>
}