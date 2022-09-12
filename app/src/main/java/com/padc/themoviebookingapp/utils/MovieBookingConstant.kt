package com.padc.themoviebookingapp.utils

// BaseUrls
const val MOVIE_BOOKING_API_BASE_URL = "https://tmba.padc.com.mm"
const val MOVIE_API_BASE_URL = "https://api.themoviedb.org"
const val Movie_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w400/"

// EndPoints
const val API_REGISTER_USER = "/api/v1/register"
const val API_GET_CINEMA_DAY_TIMESLOTS = "/api/v1/cinema-day-timeslots"
const val API_LOGIN_USER = "/api/v1/email-login"
const val API_GET_UPCOMING_MOVIES = "/3/movie/upcoming"
const val API_GET_NOW_PLAYING_MOVIES = "/3/movie/now_playing"
const val API_GET_PROFILE = "/api/v1/profile"
const val API_LOGOUT_USER = "/api/v1/logout"
const val API_GET_MOVIES = "/api/v1/movies"
const val API_GET_CINEMA_SEATING_PLAN = "/api/v1/seat-plan"
const val API_GET_SNACK_LIST = "/api/v1/snacks"
const val API_GET_PAYMENT_METHOD_LIST = "/api/v1/payment-methods"
const val API_CREATE_CARD = "/api/v1/card"
const val API_CHECK_OUT = "/api/v1/checkout"

// Params
const val PARAM_NAME = "name"
const val PARAM_EMAIL = "email"
const val PARAM_PASSWORD = "password"
const val PARAM_PHONE = "phone"
const val PARAM_API_KEY = "api_key"
const val PARAM_AUTHORIZATION = "Authorization"
const val PARAM_STATUS = "status"
const val PARAM_MOVIE_ID = "movie_id"
const val PARAM_DATE = "date"
const val PARAM_CINEMA_TIMESLOT_ID = "cinema_day_timeslot_id"
const val PARAM_BOOKING_DATE = "booking_date"
const val PARAM_CARD_NUMBER = "card_number"
const val PARAM_CARD_HOLDER = "card_holder"
const val PARAM_EXPIRATION_DATE = "expiration_date"
const val PARAM_CVC = "cvc"

// Constant Variables
const val SUCCESS_LOGIN_CODE = 200
const val SUCCESS_REGISTER_CODE = 201
const val SUCCESSFUL_LOGOUT_CODE = 200
const val LOGIN_STATE = "LOGIN"
const val REGISTER_STATE = "REGISTER"
const val API_KEY = "46f8a8d6a7a95ae4c69d3078183fee84"
const val CURRENT_STATUS = "current"
const val COMINGSOON_STATUS = "comingsoon"