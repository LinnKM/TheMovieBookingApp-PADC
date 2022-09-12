package com.padc.themoviebookingapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.adapters.BaseTimeAdapter
import com.padc.themoviebookingapp.adapters.DateListAdapter
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.data.vos.CinemaVO
import com.padc.themoviebookingapp.data.vos.DateVO
import com.padc.themoviebookingapp.delegates.DateSlotViewHolderDelegate
import com.padc.themoviebookingapp.delegates.TimeSlotViewHolderDelegate
import kotlinx.android.synthetic.main.activity_date_time.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeActivity : AppCompatActivity(), TimeSlotViewHolderDelegate,
    DateSlotViewHolderDelegate {

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        private const val EXTRA_MOVIE_NAME = "EXTRA_MOVIE_NAME"

        fun newIntent(context: Context, movieId: Int, movieName: String): Intent {
            val intent = Intent(context, DateTimeActivity::class.java)
                .putExtra(EXTRA_MOVIE_ID, movieId)
                .putExtra(EXTRA_MOVIE_NAME, movieName)
            return intent
        }
    }

    private lateinit var mDateListAdapter: DateListAdapter
    private lateinit var mBaseTimeAdapter: BaseTimeAdapter
    // Cinema List
    private var mCinemaList: List<CinemaVO>? = null
    // Date List
    private var mDateList: MutableList<DateVO> = mutableListOf()
    // MovieId
    private var mMovieId: Int? = null
    // MovieName
    private var mMovieName: String? = null
    // Model
    private var mMovieBookingModel: MovieBookingModel = MovieBookingImpl
    // Cinema TimeSlot Id
    private var mCinemaTimeSlotId: Int? = null
    // Cinema Name
    private var mCinemaName: String? = null
    // Start Time
    private var mStartTime: String? = null
    // Date
    private var mDate: String? = null
    // Cinema Id
    private var mCinemaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time)

        setUpToolBar()
        setUpListeners()
        setUpDateSectionRecyclerView()
        setUpTimeSectionRecyclerView()

        // Assign MovieId
        mMovieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        // Assign MovieName
        mMovieName = intent?.getStringExtra(EXTRA_MOVIE_NAME)
        generateTwoWeeks()
    }

    private fun setUpListeners() {
        // Back Button Listener
        toolBarDateTimeScreen.setNavigationOnClickListener {
            super.onBackPressed()
        }

        // Next Button Listener
        btnNext.setOnClickListener {
            if (mCinemaTimeSlotId == null) {
                Toast.makeText(this, "Please Select One TimeSlot", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(
                    SeatingPlanActivity.newIntent(
                        context = this,
                        movieName = mMovieName ?: "",
                        cinemaTimeslotId = mCinemaTimeSlotId ?: 0,
                        cinemaName = mCinemaName ?: "",
                        startTime = mStartTime ?: "",
                        bookingDate = mDate ?: "",
                        cinemaId = mCinemaId?: 0,
                        movieId = mMovieId?: 0
                    )
                )
            }
        }
    }

    private fun setUpTimeSectionRecyclerView() {
        mBaseTimeAdapter = BaseTimeAdapter(this)
        rvBaseTimeList.adapter = mBaseTimeAdapter
        rvBaseTimeList.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpDateSectionRecyclerView() {
        mDateListAdapter = DateListAdapter(this)
        rvDateList.adapter = mDateListAdapter
        rvDateList.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolBarDateTimeScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_white_24dp)
    }

    // Generate 2 Weeks
    @SuppressLint("NewApi")
    private fun generateTwoWeeks() {
        var date = LocalDate.now()
        var day = getFormattedDate(date).first
        var dayName = getFormattedDate(date).second

        mDateList.add(DateVO(date.toString(), day, dayName))
        for (i in 0..12) {
            date = date.plusDays(1)
            day = getFormattedDate(date).first
            dayName = getFormattedDate(date).second

            mDateList.add(DateVO(date.toString(), day, dayName))
        }

        // Set the default date
        mDateList[0].isSelected = true
        mDate = mDateList[0].date
        mDateListAdapter.setNewData(mDateList)

        // Call the default date's cinema time
        requestData(mDateList[0], mMovieId ?: 0)
    }

    private fun requestData(date: DateVO, movieId: Int) {
        // Call Cinema TimeSlots
        mMovieBookingModel.getCinemaTimeSlots(
            movieId = movieId.toString(),
            date = date.date,
            onSuccess = {
                mCinemaList = it
                mCinemaTimeSlotId = null
                mBaseTimeAdapter.setNewData(mCinemaList ?: listOf())
                changeButtonBackground()
            },
            onFailure = {
                showError(it)
            }
        )
    }

    override fun onTapTimeSlot(dayTimeSlotId: Int) {
        mCinemaList?.forEach { cinema ->
            cinema.timeSlots?.forEach { timeSlot ->
                if (timeSlot.cinemaDayTimeSlot == dayTimeSlotId) {
                    when {
                        timeSlot.isSelected -> {
                            timeSlot.isSelected = false
                            mCinemaTimeSlotId = null
                        }

                        else -> {
                            timeSlot.isSelected = true
                            mCinemaTimeSlotId = timeSlot.cinemaDayTimeSlot
                            mCinemaName = cinema.cinema
                            mStartTime = timeSlot.startTime
                            mCinemaId = cinema.cinema_id
                        }
                    }
                } else {
                    if (timeSlot.isSelected) {
                        timeSlot.isSelected = false
                    }
                }
            }
        }
        mBaseTimeAdapter.setNewData(mCinemaList ?: listOf())
        // Change button background according to validation
        changeButtonBackground()

    }

    override fun onTapDate(day: String) {
        mDateList.forEach { dateVO ->
            if (dateVO.day == day) {
                dateVO.isSelected = true
                mDate = dateVO.date
                requestData(dateVO, mMovieId ?: 0)
            } else {
                if (dateVO.isSelected) {
                    dateVO.isSelected = false
                }
            }
        }
        mDateListAdapter.setNewData(mDateList)
    }

    // Format the date
    @SuppressLint("NewApi")
    private fun getFormattedDate(date: LocalDate): Pair<String, String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-EE")
        val formattedDate = date.format(formatter)
        val dayName = formattedDate.split("-").last()
        val day = date.toString().split("-").last()

        return Pair(day, dayName)
    }

    // Change Button Background according to validation
    private fun changeButtonBackground() {
        if (mCinemaTimeSlotId != null) {
            btnNext.background = ContextCompat.getDrawable(
                this,
                R.drawable.valid_button_background
            )
        } else {
            btnNext.background = ContextCompat.getDrawable(
                this,
                R.drawable.invalid_button_background
            )
        }
    }

    private fun showError(error: String){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}