package com.padc.themoviebookingapp.data.models

import android.content.Context
import com.padc.themoviebookingapp.data.vos.*
import com.padc.themoviebookingapp.network.CheckOutRequest
import com.padc.themoviebookingapp.network.dataagents.MovieBookingDataAgent
import com.padc.themoviebookingapp.network.dataagents.RetrofitDataAgentImpl
import com.padc.themoviebookingapp.persistence.MovieBookingDatabase
import com.padc.themoviebookingapp.persistence.entities.DateAndCinemaTimeSlotVO
import com.padc.themoviebookingapp.utils.SUCCESSFUL_LOGOUT_CODE
import com.padc.themoviebookingapp.utils.SUCCESS_LOGIN_CODE
import com.padc.themoviebookingapp.utils.SUCCESS_REGISTER_CODE

object MovieBookingImpl : MovieBookingModel {
    // Model
    private val mMovieBookingDataAgent: MovieBookingDataAgent = RetrofitDataAgentImpl

    // Database
    private var mMovieBookingDatabase: MovieBookingDatabase? = null

    // Initialization of database
    fun initDatabase(context: Context) {
        mMovieBookingDatabase = MovieBookingDatabase.getDBInstance(context)
    }

    override fun loginUser(email: String, password: String, onSuccess: (Boolean) -> Unit) {
        mMovieBookingDataAgent.loginUser(email, password, onSuccess = { code, token, userProfile ->

            // Insert Data To Database
            userProfile.userToken = token
            mMovieBookingDatabase?.userDataDao()?.insertUserProfile(userProfile)

            if (code == SUCCESS_LOGIN_CODE) onSuccess(true)
        })
    }

    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Boolean) -> Unit
    ) {
        mMovieBookingDataAgent.registerUser(
            name = name,
            email = email,
            phone = phone,
            password = password,
            onSuccess = { code, token, userProfile ->

                // Insert Data To Database
                userProfile.userToken = token
                mMovieBookingDatabase?.userDataDao()?.insertUserProfile(userProfile)

                if (code == SUCCESS_REGISTER_CODE) onSuccess(true)
            }
        )
    }

    override fun getUserProfile(
        status: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

        when (status) {
            UPDATED -> {
                // Network
                mMovieBookingDataAgent.getUserProfile(
                    userToken = "Bearer ${
                        mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
                    }",
                    onSuccess = { userProfile ->
                        // Insert Data To Database
                        userProfile.userToken =
                            mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
                        mMovieBookingDatabase?.userDataDao()?.insertUserProfile(userProfile)

                        onSuccess(userProfile)
                    },
                    onFailure = onFailure
                )
            }

            else -> {
                // Database
                val userProfile = mMovieBookingDatabase?.userDataDao()?.getUserProfile()
                userProfile?.let {
                    onSuccess(it)
                }
            }
        }
    }

    override fun logoutUser(onSuccess: (Boolean) -> Unit) {
        mMovieBookingDataAgent.logoutUser(
            userToken = "Bearer ${
                mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
            }", onSuccess = {
                if (it == SUCCESSFUL_LOGOUT_CODE) {
                    mMovieBookingDatabase?.userDataDao()?.deleteUserProfile()
                    onSuccess(true)
                }
            })
    }

    override fun getMoviesByStatus(status: String, onSuccess: (List<MovieVO>) -> Unit) {
        // Database
        onSuccess(mMovieBookingDatabase?.movieDao()?.getMoviesByType(type = status) ?: listOf())

        // Network
        mMovieBookingDataAgent.getMoviesByStatus(status = status, onSuccess = { movieList ->
            movieList.forEach { movie -> movie.type = status }

            mMovieBookingDatabase?.movieDao()?.insertMovie(movieList)
            onSuccess(movieList)
        })
    }

    override fun getMovieDetails(movieId: String, onSuccess: (MovieVO) -> Unit) {
        // Database
        val movieFromDatabase =
            mMovieBookingDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
        movieFromDatabase?.let(onSuccess)

        // Network
        mMovieBookingDataAgent.getMovieDetails(movieId = movieId, onSuccess = { movie ->
            val movieFromDatabase =
                mMovieBookingDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())

            movie.type = movieFromDatabase?.type
            mMovieBookingDatabase?.movieDao()?.insertSingleMovie(movie)

            onSuccess(movie)
        })
    }

    override fun getCinemaTimeSlots(
        movieId: String,
        date: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        // Database
        val cinemasByDate = mMovieBookingDatabase?.cinemaDao()?.getCinemasByDate(date = date)
        if(cinemasByDate == null){
            onSuccess(listOf())
        } else onSuccess(cinemasByDate.cinemas?: listOf())

        // Network
        mMovieBookingDataAgent.getCinemaTimeSlots(
            movieId = movieId,
            date = date,
            userToken = "Bearer ${
                mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
            }",
            onSuccess = { cinemaList ->
                val cinemasToSave = DateAndCinemaTimeSlotVO(date = date, cinemas = cinemaList)
                mMovieBookingDatabase?.cinemaDao()?.insertCinemaListByDate(cinemasToSave)

                onSuccess(cinemaList)
            },
            onFailure = onFailure
        )
    }

    override fun getCinemaSeatingPlan(
        cinemaTimeSlotId: String,
        bookingDate: String,
        onSuccess: (List<List<MovieSeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getCinemaSeatingPlan(
            cinemaTimeSlotId = cinemaTimeSlotId,
            bookingDate = bookingDate,
            userToken = "Bearer ${
                mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
            }",
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getSnackList(onSuccess: (List<SnackVO>) -> Unit, onFailure: (String) -> Unit) {
        // Database
        onSuccess(mMovieBookingDatabase?.snackDao()?.getSnacks() ?: listOf())

        // Network
        mMovieBookingDataAgent.getSnackList(
            userToken = "Bearer ${
                mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
            }",
            onSuccess = { snacks ->
                mMovieBookingDatabase?.snackDao()?.insertSnacks(snacks)
                onSuccess(snacks)
            },
            onFailure = onFailure
        )
    }

    override fun getPaymentMethodList(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        // Database
        onSuccess(mMovieBookingDatabase?.paymentDao()?.getPaymentMethods() ?: listOf())

        // Network
        mMovieBookingDataAgent.getPaymentMethodList(
            userToken = "Bearer ${
                mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
            }",
            onSuccess = { paymentMethods ->
                mMovieBookingDatabase?.paymentDao()?.insertPaymentMethods(paymentMethods)
                onSuccess(paymentMethods)
            },
            onFailure = onFailure
        )
    }

    override fun createPaymentCard(
        cardNumber: String,
        cardHolder: String,
        expirationDate: String,
        cvc: String,
        onSuccess: (Int) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.createPaymentCard(
            userToken = "Bearer ${
                mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
            }",
            cardNumber = cardNumber,
            cardHolder = cardHolder,
            expirationDate = expirationDate,
            cvc = cvc,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun checkOut(
        checkOutRequest: CheckOutRequest,
        onSuccess: (CheckOutVO, Int, String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.checkOut(
            userToken = "Bearer ${
                mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken
            }",
            checkOutRequest = checkOutRequest,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getUserToken(onSuccess: (String) -> Unit) {
        onSuccess(mMovieBookingDatabase?.userDataDao()?.getUserProfile()?.userToken ?: "")
    }

//    override fun deleteCinemaTimeSlots() {
//        mMovieBookingDatabase?.cinemaDao()?.deleteCinemas()
//    }
}