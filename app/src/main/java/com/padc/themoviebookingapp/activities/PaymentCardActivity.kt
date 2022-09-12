package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alirezat775.lib.carouselview.Carousel
import alirezat775.lib.carouselview.CarouselView
import alirezat775.lib.carouselview.CarouselLazyLoadListener
import alirezat775.lib.carouselview.CarouselListener
import android.widget.Toast
import com.google.gson.Gson
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.adapters.PaymentCardListAdapter
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.data.vos.CardVO
import com.padc.themoviebookingapp.data.vos.CheckOutVO
import com.padc.themoviebookingapp.data.vos.SnackVO
import com.padc.themoviebookingapp.data.vos.UPDATED
import com.padc.themoviebookingapp.network.CheckOutRequest
import com.padc.themoviebookingapp.network.responses.CheckOutResponse
import com.padc.themoviebookingapp.utils.SUCCESS_LOGIN_CODE
import kotlinx.android.synthetic.main.activity_payment_card.*
import java.io.Serializable
import java.io.StringReader

class PaymentCardActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"
        private const val SELECTED_SNACK_LIST = "SELECTED_SNACK_LIST"
        private const val EXTRA_CINEMA_TIMESLOT_ID = "EXTRA_CINEMA_TIMESLOT_ID"
        private const val EXTRA_SEAT_ROW_LIST = "EXTRA_SEAT_ROW_LIST"
        private const val EXTRA_SEAT_NAME_LIST = "EXTRA_SEAT_NAME_LIST"
        private const val EXTRA_BOOKING_DATE = "EXTRA_BOOKING_DATE"
        private const val EXTRA_CINEMA_ID = "EXTRA_CINEMA_ID"
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        private const val EXTRA_CINEMA_NAME = "EXTRA_CINEMA_NAME"

        fun newIntent(
            context: Context,
            totalPrice: Int,
            selectedSnackList: List<SnackVO>,
            cinemaTimeSlotId: Int,
            seatRowList: MutableSet<String>,
            seatNameList: MutableList<String>,
            bookingDate: String,
            cinemaId: Int,
            movieId: Int,
            cinemaName: String
        ): Intent {
            val intent = Intent(context, PaymentCardActivity::class.java)
                .putExtra(EXTRA_TOTAL_PRICE, totalPrice)
                .putExtra(SELECTED_SNACK_LIST, selectedSnackList as Serializable)
                .putExtra(EXTRA_CINEMA_TIMESLOT_ID, cinemaTimeSlotId)
                .putExtra(EXTRA_SEAT_ROW_LIST, seatRowList as Serializable)
                .putExtra(EXTRA_SEAT_NAME_LIST, seatNameList as Serializable)
                .putExtra(EXTRA_BOOKING_DATE, bookingDate)
                .putExtra(EXTRA_CINEMA_ID, cinemaId)
                .putExtra(EXTRA_MOVIE_ID, movieId)
                .putExtra(EXTRA_CINEMA_NAME, cinemaName)
            return intent
        }
    }

    private lateinit var cardListAdapter: PaymentCardListAdapter
    private var mTotalPrice: Int? = null

    // Model
    private var mMovieBookingModel: MovieBookingModel = MovieBookingImpl
    private var mCardList: List<CardVO> = listOf()
    private var mCardId: Int? = null
    private lateinit var carousel: Carousel
    private var mSelectedSnackList: List<SnackVO> = listOf()
    private var mCinemaTimeSlotId: Int? = null
    private var mSeatRowList: MutableSet<String> = mutableSetOf()
    private var mSeatNameList: MutableList<String> = mutableListOf()
    private var mBookingDate: String? = null
    private var mCinemaId: Int? = null
    private var mMovieId: Int? = null
    private lateinit var mSeatRow: String
    private lateinit var mSeatNumber: String
    private var mResponseCode: Int? = null
    private lateinit var mSuccessfulCheckOut: CheckOutVO
    private lateinit var mResponseMessage: String
    private var mCinemaName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_card)

        setUpToolBar()
        setUpCarousel()
        // Get Data From Intent
        getDataFromIntent()
        setUpListeners()
        getUserProfileByStatus(UPDATED)
    }

    private fun setUpListeners() {

        // Back Button Listener
        toolBarPaymentCardScreen.setNavigationOnClickListener {
            super.onBackPressed()
        }

        // AddNewCard Listener
        btnAddCard.setOnClickListener {
            startActivityForResult(NewPaymentCardActivity.newIntent(this), 2)
        }

        // Confirm button Listener
        btnConfirmCard.setOnClickListener {
            val checkOut = CheckOutRequest(
                cinemaDayTimeSlotId = mCinemaTimeSlotId ?: 0,
                row = mSeatRow,
                seatNumber = mSeatNumber,
                bookingDate = mBookingDate ?: "",
                totalPrice = mTotalPrice ?: 0,
                movieId = mMovieId ?: 0,
                cardId = mCardId ?: 0,
                cinemaId = mCinemaId ?: 0,
                snacks = mSelectedSnackList
            )

            mMovieBookingModel.checkOut(
                checkOutRequest = checkOut,
                onSuccess = { successfulCheckOut, responseCode, responseMessage ->
                    mSuccessfulCheckOut = successfulCheckOut
                    mResponseCode = responseCode
                    mResponseMessage = responseMessage

                    when (mResponseCode) {
                        SUCCESS_LOGIN_CODE -> {
                            startActivity(
                                ReceiptActivity.newIntent(
                                    context = this,
                                    successfulCheckOut = mSuccessfulCheckOut,
                                    cinemaName = mCinemaName ?: ""
                                )
                            )
                        }

                        else -> {
                            Toast.makeText(this, mResponseMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onFailure = {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolBarPaymentCardScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_black_24dp)
    }

    private fun setUpCarousel() {
        cardListAdapter = PaymentCardListAdapter()
        carousel = Carousel(this, carouselView, cardListAdapter)
        carousel.setOrientation(CarouselView.HORIZONTAL, true)

        // Carousel Listener
        carousel.addCarouselListener(object : CarouselListener {
            override fun onPositionChange(position: Int) {
                if (mCardList.isNotEmpty()) {
                    mCardId = mCardList[position].id ?: 0
                    println("CardId: $mCardId")
                }
            }

            override fun onScroll(dx: Int, dy: Int) {
            }
        })
    }

    private fun getUserProfileByStatus(status: String = "") {
        // Call Updated UserProfile
        mMovieBookingModel.getUserProfile(
            status = status,
            onSuccess = { user ->
                mCardList = user.cards ?: listOf()
                cardListAdapter.setNewData(user.cards ?: listOf())

                // Set Default Position of Carousel
                if(mCardList.isNotEmpty()) {
                    carousel.setCurrentPosition(mCardList.lastIndex)
                }
            },
            onFailure = {
                showError(it)
            }
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val code = data?.getIntExtra(NewPaymentCardActivity.EXTRA_RESPONSE_CODE, 0)
            if (code == SUCCESS_LOGIN_CODE) {
                getUserProfileByStatus(UPDATED)
            }
        }
    }

    private fun getDataFromIntent() {
        // Total Price
        mTotalPrice = intent?.getIntExtra(EXTRA_TOTAL_PRICE, 0)
        mTotalPrice?.let {
            tvPaymentCashAmount.text = "$it.00"
        }

        // Selected Snack List
        mSelectedSnackList = intent?.getSerializableExtra(SELECTED_SNACK_LIST) as List<SnackVO>
        if (mSelectedSnackList.isNotEmpty()) {
            mSelectedSnackList = mSelectedSnackList.filterNot { selectedSnack ->
                selectedSnack.quantity == 0
            }.toList()
        }
        mSelectedSnackList.forEach { selectedSnack ->
            selectedSnack.apply {
                description = null
                name = null
                price = null
                image = null
            }
        }

        // CinemaTimeSlot Id
        mCinemaTimeSlotId = intent?.getIntExtra(EXTRA_CINEMA_TIMESLOT_ID, 0)

        // SeatRow List
        mSeatRowList = intent?.getSerializableExtra(EXTRA_SEAT_ROW_LIST) as MutableSet<String>
        mSeatRowList = mSeatRowList.sorted().toMutableSet()
        mSeatRow = mSeatRowList.joinToString(",")

        // SeatName List
        mSeatNameList = intent?.getSerializableExtra(EXTRA_SEAT_NAME_LIST) as MutableList<String>
        mSeatNameList = mSeatNameList.sorted().toMutableList()
        mSeatNumber = mSeatNameList.joinToString(",")

        // BookingDate
        mBookingDate = intent?.getStringExtra(EXTRA_BOOKING_DATE)

        // CinemaId
        mCinemaId = intent?.getIntExtra(EXTRA_CINEMA_ID, 0)

        // MovieId
        mMovieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)

        // CinemaName
        mCinemaName = intent?.getStringExtra(EXTRA_CINEMA_NAME)
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}