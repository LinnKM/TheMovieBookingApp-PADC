package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.adapters.PaymentMethodListAdapter
import com.padc.themoviebookingapp.adapters.SnacksListAdapter
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.data.vos.PaymentVO
import com.padc.themoviebookingapp.data.vos.SnackVO
import com.padc.themoviebookingapp.delegates.PaymentMethodViewHolderDelegate
import com.padc.themoviebookingapp.delegates.SnackViewHolderDelegate
import kotlinx.android.synthetic.main.activity_snacks.*
import java.io.Serializable

class SnacksActivity : AppCompatActivity(), PaymentMethodViewHolderDelegate,
    SnackViewHolderDelegate {
    companion object {
        private const val EXTRA_TICKET_PRICE = "EXTRA_TICKET_PRICE"
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
            cinemaTimeSlotId: Int,
            seatRowList: MutableSet<String>,
            seatNameList: MutableList<String>,
            bookingDate: String,
            cinemaId: Int,
            movieId: Int,
            cinemaName: String
        ): Intent {
            val intent = Intent(context, SnacksActivity::class.java)
                .putExtra(EXTRA_TICKET_PRICE, totalPrice)
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

    private lateinit var mSnacksListAdapter: SnacksListAdapter
    private lateinit var mPaymentMethodListAdapter: PaymentMethodListAdapter

    // Model
    private var mMovieBookingModel: MovieBookingModel = MovieBookingImpl
    private var mSnackList: List<SnackVO> = listOf()
    private var mPaymentMethodList: List<PaymentVO> = listOf()
    private var mTicketPrice: Int? = null
    private var mTotalPrice: Int = 0
    private var mSnackPrice: Int = 0
    private var mCinemaTimeSlotId: Int? = null
    private var mSeatRowList: MutableSet<String> = mutableSetOf()
    private var mSeatNameList: MutableList<String> = mutableListOf()
    private var mBookingDate: String? = null
    private var mCinemaId: Int? = null
    private var mMovieId: Int? = null
    private var mCinemaName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snacks)

        setUpToolBar()
        setUpSnackSectionRecyclerView()
        setUpPaymentSectionRecyclerView()
        // Get Data From Intent
        getDataFromIntent()
        setUpListeners()

        requestData()
    }

    private fun setUpListeners() {
        // Back Button Listener
        toolBarSnackScreen.setNavigationOnClickListener {
            super.onBackPressed()
        }

        // Pay Button Listener
        btnPay.setOnClickListener {
            startActivity(
                PaymentCardActivity.newIntent(
                    context = this,
                    totalPrice = if (mTotalPrice == 0) mTicketPrice ?: 0 else mTotalPrice,
                    selectedSnackList = mSnackList,
                    cinemaTimeSlotId = mCinemaTimeSlotId ?: 0,
                    seatRowList = mSeatRowList,
                    seatNameList = mSeatNameList,
                    bookingDate = mBookingDate?: "",
                    cinemaId = mCinemaId?: 0,
                    movieId = mMovieId?: 0,
                    cinemaName = mCinemaName?: ""
                )
            )
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolBarSnackScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_black_24dp)
    }

    private fun setUpSnackSectionRecyclerView() {
        mSnacksListAdapter = SnacksListAdapter(this)
        rvSnackList.adapter = mSnacksListAdapter
        rvSnackList.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpPaymentSectionRecyclerView() {
        mPaymentMethodListAdapter = PaymentMethodListAdapter(this)
        rvPaymentMethodList.adapter = mPaymentMethodListAdapter
        rvPaymentMethodList.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun requestData() {
        // Call Snack List
        mMovieBookingModel.getSnackList(
            onSuccess = {
                mSnackList = it
                mSnacksListAdapter.setNewData(mSnackList)
            },
            onFailure = {
                showError(it)
            }
        )

        // Call PaymentMethod List
        mMovieBookingModel.getPaymentMethodList(
            onSuccess = {
                mPaymentMethodList = it
                mPaymentMethodListAdapter.setNewData(mPaymentMethodList)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    // Action on tap PaymentMethod
    override fun onTapPaymentMethod(id: Int) {
        mPaymentMethodList.forEach { paymentMethod ->
            paymentMethod.isSelected = paymentMethod.id == id
        }

        mPaymentMethodListAdapter.setNewData(mPaymentMethodList)
    }

    // Action on tap SnackItem
    override fun onTapSnack(id: Int, tappedState: Char) {
        mSnackList.forEach { snack ->
            if (snack.id == id) {
                calculateSnackPrice(snack = snack, tappedState = tappedState)
//                if (mSelectedSnackList.isEmpty()) {
//                    mSelectedSnackList.add(
//                        createSelectedSnackVO(
//                            snackId = snack.id,
//                            snackQuantity = snack.quantity
//                        )
//                    )
//                    mSelectedSnackIdList.add(snack.id)
//                } else {
//                    if (!mSelectedSnackIdList.contains(snack.id)) {
//                        mSelectedSnackList.add(
//                            createSelectedSnackVO(
//                                snackId = snack.id,
//                                snackQuantity = snack.quantity
//                            )
//                        )
//                        mSelectedSnackIdList.add(snack.id)
//                    } else {
//                        for(i in 0..mSelectedSnackIdList.lastIndex){
//                            if(snack.id == mSelectedSnackIdList[i]){
//                                mSelectedSnackList.removeAt(i)
//                                mSelectedSnackList.add(i, createSelectedSnackVO(
//                                    snackId = snack.id,
//                                    snackQuantity = snack.quantity
//                                ))
//                            }
//                        }
//                    }
//                }
            }
        }
        mSnacksListAdapter.setNewData(mSnackList)
        bindTotalPrice(mTotalPrice)
    }

    // Calculate the Snack Price
    private fun calculateSnackPrice(snack: SnackVO, tappedState: Char) {
        when (tappedState) {
            '+' -> {
                snack.quantity++
                mSnackPrice += snack.price ?: 0
                mTotalPrice = mSnackPrice + (mTicketPrice ?: 0)
            }
            '-' -> {
                if (snack.quantity != 0) {
                    snack.quantity--
                    mSnackPrice -= snack.price ?: 0
                    mTotalPrice = mSnackPrice + (mTicketPrice ?: 0)
                }
            }
        }
    }

    // Bind Total Price Data
    private fun bindTotalPrice(totalPrice: Int) {
        tvSubTotal.text = "Sub total : $totalPrice$"
        btnPay.text = "Pay $$totalPrice.00"
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun getDataFromIntent() {
        // Ticket Price
        mTicketPrice = intent?.getIntExtra(EXTRA_TICKET_PRICE, 0)
        mTicketPrice?.let {
            bindTotalPrice(it)
        }
        // CinemaTimeSlot Id
        mCinemaTimeSlotId = intent?.getIntExtra(EXTRA_CINEMA_TIMESLOT_ID, 0)
        // SeatRowList
        mSeatRowList = intent?.getSerializableExtra(EXTRA_SEAT_ROW_LIST) as MutableSet<String>
        // SeatNameList
        mSeatNameList = intent?.getSerializableExtra(EXTRA_SEAT_NAME_LIST) as MutableList<String>
        // BookingDate
        mBookingDate = intent?.getStringExtra(EXTRA_BOOKING_DATE)
        // CinemaId
        mCinemaId = intent?.getIntExtra(EXTRA_CINEMA_ID, 0)
        // MovieId
        mMovieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        // CinemaName
        mCinemaName = intent?.getStringExtra(EXTRA_CINEMA_NAME)
    }
}