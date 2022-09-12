package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.adapters.MovieSeatAdapter
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.data.vos.MovieSeatVO
import com.padc.themoviebookingapp.delegates.CinemaSeatViewHolderDelegate
import com.padc.themoviebookingapp.viewpods.SeatIndicatorViewPod
import kotlinx.android.synthetic.main.activity_seating_plan.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SeatingPlanActivity : AppCompatActivity(), CinemaSeatViewHolderDelegate {

    companion object {
        private const val EXTRA_MOVIE_NAME = "EXTRA_MOVIE_NAME"
        private const val EXTRA_CINEMA_TIMESLOT_ID = "EXTRA_CINEMA_TIMESLOT_ID"
        private const val EXTRA_CINEMA_NAME = "EXTRA_CINEMA_NAME"
        private const val EXTRA_START_TIME = "EXTRA_START_TIME"
        private const val EXTRA_WATCH_DATE = "EXTRA_WATCH_DATE"
        private const val EXTRA_CINEMA_ID = "EXTRA_CINEMA_ID"
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(
            context: Context,
            movieName: String,
            cinemaTimeslotId: Int,
            cinemaName: String,
            startTime: String,
            bookingDate: String,
            cinemaId: Int,
            movieId: Int
        ): Intent {
            val intent = Intent(context, SeatingPlanActivity::class.java)
                .putExtra(EXTRA_MOVIE_NAME, movieName)
                .putExtra(EXTRA_CINEMA_TIMESLOT_ID, cinemaTimeslotId)
                .putExtra(EXTRA_CINEMA_NAME, cinemaName)
                .putExtra(EXTRA_START_TIME, startTime)
                .putExtra(EXTRA_WATCH_DATE, bookingDate)
                .putExtra(EXTRA_CINEMA_ID, cinemaId)
                .putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    private lateinit var mMovieSeatAdapter: MovieSeatAdapter
    private lateinit var mSeatAvailableViewPod: SeatIndicatorViewPod
    private lateinit var mSeatReservedViewPod: SeatIndicatorViewPod
    private lateinit var mSeatSelectionViewPod: SeatIndicatorViewPod

    // Model
    private var mMovieBookingModel: MovieBookingModel = MovieBookingImpl
    // MovieName
    private var mMovieName: String? = null
    // CinemaId
    private var mCinemaTimeSlotId: Int? = null
    // CinemaName
    private var mCinemaName: String? = null
    // StartTime
    private var mStartTime: String? = null
    // WatchDate
    private var mBookingDate: String? = null
    private var totalTicket = 0
    private var seatNameList: MutableList<String> = mutableListOf()
    private var mSeatList: List<MovieSeatVO>? = null
    private var totalPrice = 0
    private var seatRowList: MutableList<String> = mutableListOf()
    private var mCinemaId: Int? = null
    private var mMovieId: Int? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seating_plan)

        setUpToolBar()
        setUpMovieSeatRecyclerView()
        setUpSeatIndicatorViewPod()
        setUpListeners()
        getDataFromIntent()
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolBarSeatingPlanScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_black_24dp)
        supportActionBar?.title = ""
    }

    private fun setUpMovieSeatRecyclerView() {
        mMovieSeatAdapter = MovieSeatAdapter(this)
        rvSeatingPlan.adapter = mMovieSeatAdapter
        rvSeatingPlan.layoutManager = GridLayoutManager(applicationContext, 14)
    }

    private fun setUpSeatIndicatorViewPod() {
        mSeatAvailableViewPod = vPodSeatAvailable as SeatIndicatorViewPod
        mSeatAvailableViewPod.setUpViewPod(
            "Available",
            R.drawable.background_seat_available_indicator
        )

        mSeatReservedViewPod = vPodSeatReserved as SeatIndicatorViewPod
        mSeatReservedViewPod.setUpViewPod("Reserved", R.drawable.background_seat_taken_indicator)

        mSeatSelectionViewPod = vPodSeatYourSelection as SeatIndicatorViewPod
        mSeatSelectionViewPod.setUpViewPod(
            "Your selection",
            R.drawable.background_seat_selection_indicator
        )
    }

    private fun setUpListeners() {
        // Back button listener
        toolBarSeatingPlanScreen.setNavigationOnClickListener {
            super.onBackPressed()
        }

        // Buy button listener
        btnBuyTicket.setOnClickListener {
            if (totalPrice != 0) {
                startActivity(
                    SnacksActivity.newIntent(
                        context = this,
                        totalPrice = totalPrice,
                        cinemaTimeSlotId = mCinemaTimeSlotId ?: 0,
                        seatRowList = seatRowList.toMutableSet(),
                        seatNameList = seatNameList,
                        bookingDate = mBookingDate?: "",
                        cinemaId = mCinemaId?: 0,
                        movieId = mMovieId?: 0,
                        cinemaName = mCinemaName?: ""
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataFromIntent() {
        // Assign MovieName
        mMovieName = intent?.getStringExtra(EXTRA_MOVIE_NAME)
        mCinemaTimeSlotId = intent?.getIntExtra(EXTRA_CINEMA_TIMESLOT_ID, 0)
        mCinemaName = intent?.getStringExtra(EXTRA_CINEMA_NAME)
        mStartTime = intent?.getStringExtra(EXTRA_START_TIME)
        mBookingDate = intent?.getStringExtra(EXTRA_WATCH_DATE)
        mCinemaId = intent?.getIntExtra(EXTRA_CINEMA_ID, 0)
        mMovieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)

        // Bind Data From Intent
        tvMovieNameLabel.text = mMovieName ?: ""
        tvLocation.text = mCinemaName ?: ""
        tvShowTimeDate.text = "${getFormattedDate(mBookingDate ?: "")}, $mStartTime"

        // Call CinemaSeatingPlan
        requestData(cinemaTimeSlotId = mCinemaTimeSlotId ?: 0, date = mBookingDate ?: "")
    }

    private fun requestData(cinemaTimeSlotId: Int, date: String) {
        // Call Cinema SeatingPlan
        mMovieBookingModel.getCinemaSeatingPlan(
            cinemaTimeSlotId = cinemaTimeSlotId.toString(),
            bookingDate = date,
            onSuccess = {
                mSeatList = it.flatten()
                mMovieSeatAdapter.setNewData(mSeatList ?: listOf())
            },
            onFailure = {
                showError(it)
            }
        )
    }

    override fun onTapSeat(seatName: String) {
        mSeatList?.forEach { seatVO ->
            if (seatVO.seatName == seatName && seatVO.type == "available") {
                if (seatVO.isSelected) {
                    seatVO.isSelected = false
                    if (totalTicket > 0) totalTicket--
                    if (totalPrice > 0) totalPrice -= seatVO.price ?: 0
                    seatNameList =
                        seatNameList.filterNot { it.equals(seatVO.seatName) }.toMutableList()
                    seatRowList.remove(seatVO.symbol)
                } else {
                    seatVO.isSelected = true
                    totalTicket++
                    totalPrice += seatVO.price ?: 0
                    seatNameList.add(seatVO.seatName)
                    seatRowList.add(seatVO.seatName.split("-").first())
                }
            }
        }

        mMovieSeatAdapter.setNewData(mSeatList ?: listOf())
        tvTicketAmount.text = if (totalTicket == 0) "-" else totalTicket.toString()
        tvSeatPosition.text =
            if (seatNameList.isEmpty()) "-" else seatNameList.joinToString(separator = ", ")
        btnBuyTicket.text = "Buy Ticket $${totalPrice}.00"

        setButtonBackground()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFormattedDate(dateString: String): String {
        // List the dateString
        val dateStringList = dateString.split("-").toList()
        var bookingDateString = ""
        dateStringList.forEach {
            bookingDateString += it
        }
        // Type Cast String to LocalDate
        val bookingDate: LocalDate =
            LocalDate.parse(bookingDateString, DateTimeFormatter.BASIC_ISO_DATE)
        // DateTimeFormatter
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM")
        return bookingDate.format(formatter)
    }

    private fun setButtonBackground() {
        when (totalPrice) {
            0 -> btnBuyTicket.background = ContextCompat.getDrawable(
                this, R.drawable.invalid_button_background
            )

            else -> btnBuyTicket.background = ContextCompat.getDrawable(
                this, R.drawable.valid_button_background
            )
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}