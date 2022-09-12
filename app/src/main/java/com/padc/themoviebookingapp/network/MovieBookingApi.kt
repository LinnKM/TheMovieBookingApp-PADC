package com.padc.themoviebookingapp.network

import com.padc.themoviebookingapp.network.responses.*
import com.padc.themoviebookingapp.utils.*
import retrofit2.Call
import retrofit2.http.*

interface MovieBookingApi {

    // Login User
    @POST(API_LOGIN_USER)
    @FormUrlEncoded
    fun loginUser(
        @Field(PARAM_EMAIL) email: String,
        @Field(PARAM_PASSWORD) password: String
    ): Call<RegisterUserResponse>

    // Register User
    @POST(API_REGISTER_USER)
    @FormUrlEncoded
    fun registerUser(
        @Field(PARAM_NAME) name: String,
        @Field(PARAM_EMAIL) email: String,
        @Field(PARAM_PHONE) phone: String,
        @Field(PARAM_PASSWORD) password: String
    ): Call<RegisterUserResponse>

    @GET(API_GET_PROFILE)
    fun getUserProfile(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<RegisterUserResponse>

    @POST(API_LOGOUT_USER)
    fun logoutUser(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<RegisterUserResponse>

    @GET(API_GET_MOVIES)
    fun getMoviesByStatus(
        @Query(PARAM_STATUS) status: String
    ): Call<MovieListResponse>

    @GET("$API_GET_MOVIES/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String
    ): Call<MovieDetailResponse>

    @GET(API_GET_CINEMA_DAY_TIMESLOTS)
    fun getCinemaTimeSlots(
        @Query(PARAM_MOVIE_ID) movieId: String,
        @Query(PARAM_DATE) date: String,
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<DayTimeSlotResponse>

    @GET(API_GET_CINEMA_SEATING_PLAN)
    fun getCinemaSeatingPlan(
        @Query(PARAM_CINEMA_TIMESLOT_ID) cinemaTimeSlotId: String,
        @Query(PARAM_BOOKING_DATE) bookingDate: String,
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<SeatingListResponse>

    @GET(API_GET_SNACK_LIST)
    fun getSnackList(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<SnackListResponse>

    @GET(API_GET_PAYMENT_METHOD_LIST)
    fun getPaymentMethodList(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<PaymentMethodListResponse>

    @POST(API_CREATE_CARD)
    @FormUrlEncoded
    fun createPaymentCard(
        @Header(PARAM_AUTHORIZATION) authorization: String,
        @Field(PARAM_CARD_NUMBER) cardNumber: String,
        @Field(PARAM_CARD_HOLDER) cardHolder: String,
        @Field(PARAM_EXPIRATION_DATE) expirationDate: String,
        @Field(PARAM_CVC) cvc: String
    ): Call<CardListResponse>

    @POST(API_CHECK_OUT)
    fun checkOut(
        @Header(PARAM_AUTHORIZATION) authorization: String,
        @Body checkOutRequest: CheckOutRequest
    ): Call<CheckOutResponse>
}