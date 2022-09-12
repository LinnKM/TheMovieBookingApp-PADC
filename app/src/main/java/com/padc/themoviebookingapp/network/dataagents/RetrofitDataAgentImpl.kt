package com.padc.themoviebookingapp.network.dataagents

import com.padc.themoviebookingapp.data.vos.*
import com.padc.themoviebookingapp.network.CheckOutRequest
import com.padc.themoviebookingapp.network.MovieBookingApi
import com.padc.themoviebookingapp.network.TheMovieApi
import com.padc.themoviebookingapp.network.responses.*
import com.padc.themoviebookingapp.utils.MOVIE_API_BASE_URL
import com.padc.themoviebookingapp.utils.MOVIE_BOOKING_API_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitDataAgentImpl : MovieBookingDataAgent {
    private var mMovieBookingApi: MovieBookingApi? = null
    private var mMovieApi: TheMovieApi? = null

    init {
        val mClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(MOVIE_BOOKING_API_BASE_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val movieApiRetrofit = Retrofit.Builder()
            .baseUrl(MOVIE_API_BASE_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mMovieBookingApi = retrofit.create(MovieBookingApi::class.java)
        mMovieApi = movieApiRetrofit.create(TheMovieApi::class.java)
    }

    // Login User
    override fun loginUser(
        email: String,
        password: String,
        onSuccess: (Int, String, UserVO) -> Unit
    ) {
        mMovieBookingApi?.loginUser(email, password)
            ?.enqueue(object : Callback<RegisterUserResponse> {
                override fun onResponse(
                    call: Call<RegisterUserResponse>,
                    response: Response<RegisterUserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseCode = response.body()?.code ?: 0
                        val token = response.body()?.token ?: ""
                        response.body()?.data?.let { userProfile ->
                            onSuccess(responseCode, token, userProfile)
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                }
            })
    }

    // Register User
    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Int, String, UserVO) -> Unit
    ) {
        mMovieBookingApi?.registerUser(
            name = name,
            email = email,
            phone = phone,
            password = password
        )?.enqueue(object : Callback<RegisterUserResponse> {
            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseCode = response.body()?.code ?: 0
                    val token = response.body()?.token ?: ""
                    response.body()?.data?.let { userProfile ->
                        onSuccess(responseCode, token, userProfile)
                    }
                }
            }

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
            }
        })
    }

    // Get Now Playing Movies
    override fun getNowPlayingMovies(onSuccess: (List<MovieVO>) -> Unit) {
        mMovieApi?.getNowPlayingMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
            }

        })
    }

    // Get UpComing Movies
    override fun getUpComingMovies(onSuccess: (List<MovieVO>) -> Unit) {
        mMovieApi?.getUpComingMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
            }

        })
    }

    // Get UserProfile
    override fun getUserProfile(
        userToken: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getUserProfile(authorization = userToken)
            ?.enqueue(object : Callback<RegisterUserResponse> {
                override fun onResponse(
                    call: Call<RegisterUserResponse>,
                    response: Response<RegisterUserResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.let {
                            onSuccess(it)
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    onFailure(t.message?: "")
                }

            })
    }

    // LogOut User
    override fun logoutUser(userToken: String, onSuccess: (Int) -> Unit) {
        mMovieBookingApi?.logoutUser(authorization = userToken)
            ?.enqueue(object : Callback<RegisterUserResponse> {
                override fun onResponse(
                    call: Call<RegisterUserResponse>,
                    response: Response<RegisterUserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseCode = response.body()?.code ?: 0
                        onSuccess(responseCode)
                    }
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                }

            })
    }

    // Get Movies By Status(current or comingsoon)
    override fun getMoviesByStatus(status: String, onSuccess: (List<MovieVO>) -> Unit) {
        mMovieBookingApi?.getMoviesByStatus(status = status)
            ?.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful) {
                        val movieList = response.body()?.movieList ?: listOf()
                        onSuccess(movieList)
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {

                }

            })
    }

    // Get Movie Details
    override fun getMovieDetails(movieId: String, onSuccess: (MovieVO) -> Unit) {
        mMovieBookingApi?.getMovieDetails(movieId = movieId)
            ?.enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.movie?.let {
                            onSuccess(it)
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                }


            })
    }

    // Get CinemaTimeSlots
    override fun getCinemaTimeSlots(
        movieId: String,
        date: String,
        userToken: String,
        onSuccess: (List<CinemaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getCinemaTimeSlots(
            movieId = movieId,
            date = date,
            authorization = userToken
        )?.enqueue(object : Callback<DayTimeSlotResponse> {
            override fun onResponse(
                call: Call<DayTimeSlotResponse>,
                response: Response<DayTimeSlotResponse>
            ) {
                if (response.isSuccessful) {
                    val cinemaList = response.body()?.data ?: listOf()
                    onSuccess(cinemaList)
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<DayTimeSlotResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    // Get SeatingList
    override fun getCinemaSeatingPlan(
        cinemaTimeSlotId: String,
        bookingDate: String,
        userToken: String,
        onSuccess: (List<List<MovieSeatVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getCinemaSeatingPlan(
            cinemaTimeSlotId = cinemaTimeSlotId,
            bookingDate = bookingDate,
            authorization = userToken
        )?.enqueue(object : Callback<SeatingListResponse> {
            override fun onResponse(
                call: Call<SeatingListResponse>,
                response: Response<SeatingListResponse>
            ) {
                if (response.isSuccessful) {
                    val seatList = response.body()?.data ?: listOf()
                    onSuccess(seatList)
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<SeatingListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    // Get SnackList
    override fun getSnackList(
        userToken: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getSnackList(authorization = userToken)
            ?.enqueue(object : Callback<SnackListResponse> {
                override fun onResponse(
                    call: Call<SnackListResponse>,
                    response: Response<SnackListResponse>
                ) {
                    if (response.isSuccessful) {
                        val snackList = response.body()?.data ?: listOf()
                        onSuccess(snackList)
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<SnackListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    // Get PaymentMethodList
    override fun getPaymentMethodList(
        userToken: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getPaymentMethodList(authorization = userToken)
            ?.enqueue(object : Callback<PaymentMethodListResponse> {
                override fun onResponse(
                    call: Call<PaymentMethodListResponse>,
                    response: Response<PaymentMethodListResponse>
                ) {
                    if (response.isSuccessful) {
                        val paymentMethodList = response.body()?.data ?: listOf()
                        onSuccess(paymentMethodList)
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<PaymentMethodListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }

    // Create PaymentCard
    override fun createPaymentCard(
        userToken: String,
        cardNumber: String,
        cardHolder: String,
        expirationDate: String,
        cvc: String,
        onSuccess: (Int) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.createPaymentCard(
            authorization = userToken,
            cardNumber = cardNumber,
            cardHolder = cardHolder,
            expirationDate = expirationDate,
            cvc = cvc
        )?.enqueue(object : Callback<CardListResponse> {
            override fun onResponse(
                call: Call<CardListResponse>,
                response: Response<CardListResponse>
            ) {
                if (response.isSuccessful) {
                    val responseCode = response.body()?.code ?: 0
                    onSuccess(responseCode)
                } else (response.message())
            }

            override fun onFailure(call: Call<CardListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }
        })
    }

    // CheckOut
    override fun checkOut(
        userToken: String,
        checkOutRequest: CheckOutRequest,
        onSuccess: (CheckOutVO, Int, String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.checkOut(authorization = userToken, checkOutRequest = checkOutRequest)
            ?.enqueue(object : Callback<CheckOutResponse> {
                override fun onResponse(
                    call: Call<CheckOutResponse>,
                    response: Response<CheckOutResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.let{
                            val checkOut = it
                            val responseCode = response.body()?.code?: 0
                            val responseMessage = response.body()?.message?: ""

                            onSuccess(checkOut, responseCode, responseMessage)
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<CheckOutResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }
}