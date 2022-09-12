package com.padc.themoviebookingapp.network.dataagents

import com.padc.themoviebookingapp.data.vos.*
import com.padc.themoviebookingapp.network.CheckOutRequest
import com.padc.themoviebookingapp.network.responses.RegisterUserResponse

interface MovieBookingDataAgent {

    fun loginUser(
        email: String,
        password: String,
        onSuccess: (Int, String, UserVO) -> Unit
    )

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Int, String, UserVO) -> Unit
    )

    fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit
    )

    fun getUpComingMovies(
        onSuccess: (List<MovieVO>) -> Unit
    )

    fun getUserProfile(
        userToken: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun logoutUser(
        userToken: String,
        onSuccess: (Int) -> Unit
    )

    fun getMoviesByStatus(
        status: String,
        onSuccess: (List<MovieVO>) -> Unit
    )

    fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit
    )

    fun getCinemaTimeSlots(
        movieId: String,
        date: String,
        userToken: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinemaSeatingPlan(
        cinemaTimeSlotId: String,
        bookingDate: String,
        userToken: String,
        onSuccess: (List<List<MovieSeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackList(
        userToken: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethodList(
        userToken: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun createPaymentCard(
        userToken: String,
        cardNumber: String,
        cardHolder: String,
        expirationDate: String,
        cvc: String,
        onSuccess: (Int) -> Unit,
        onFailure: (String) -> Unit
    )

    fun checkOut(
        userToken: String,
        checkOutRequest: CheckOutRequest,
        onSuccess: (CheckOutVO, Int, String) -> Unit,
        onFailure: (String) -> Unit
    )
}