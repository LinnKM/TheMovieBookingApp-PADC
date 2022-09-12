package com.padc.themoviebookingapp.data.models

import com.padc.themoviebookingapp.data.vos.*
import com.padc.themoviebookingapp.network.CheckOutRequest

interface MovieBookingModel {
    fun loginUser(
        email: String,
        password: String,
        onSuccess: (Boolean) -> Unit,
    )

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Boolean) -> Unit
    )

    fun getUserProfile(
        status: String = "",
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun logoutUser(
        onSuccess: (Boolean) -> Unit
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
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinemaSeatingPlan(
        cinemaTimeSlotId: String,
        bookingDate: String,
        onSuccess: (List<List<MovieSeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackList(
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethodList(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun createPaymentCard(
        cardNumber: String,
        cardHolder: String,
        expirationDate: String,
        cvc: String,
        onSuccess: (Int) -> Unit,
        onFailure: (String) -> Unit
    )

    fun checkOut(
        checkOutRequest: CheckOutRequest,
        onSuccess: (CheckOutVO, Int, String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getUserToken(
        onSuccess: (String) -> Unit
    )

//    fun deleteCinemaTimeSlots()
}