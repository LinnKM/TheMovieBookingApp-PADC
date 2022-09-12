package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.adapters.CastListAdapter
import com.padc.themoviebookingapp.adapters.GenreChipListAdapter
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.data.vos.MovieVO
import com.padc.themoviebookingapp.utils.MOVIE_BOOKING_API_BASE_URL
import com.padc.themoviebookingapp.utils.Movie_IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    companion object{

        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun newIntent(context: Context, movieId: Int): Intent{
            val intent = Intent(context, MovieDetailActivity::class.java)
                .putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    lateinit var mGenreListAdapter: GenreChipListAdapter
    lateinit var mCastListAdapter: CastListAdapter
    private var mMovieId: Int? = null
    private var mMovieName: String? = null
    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setUpGenreChipRecyclerView()
        setUpCastRecyclerView()
        setUpListeners()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        mMovieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        mMovieId?.let {
            requestData(it)
        }
    }

    private fun setUpListeners(){

        // Get Ticket Button Listener
        btnGetTicket.setOnClickListener {
            startActivity(DateTimeActivity.newIntent(this, mMovieId?: 0, mMovieName?: ""))
        }

        // Back Button Listener
        btnBackButton.setOnClickListener{
            super.onBackPressed()
        }

        // Play Button Listener
        btnPlayButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=1roy4o4tqQM"))
            startActivity(intent)
        }
    }

    private fun setUpGenreChipRecyclerView(){
        mGenreListAdapter = GenreChipListAdapter()

        rvGenreList.adapter = mGenreListAdapter
        rvGenreList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpCastRecyclerView(){
        mCastListAdapter = CastListAdapter()

        rvCastList.adapter = mCastListAdapter
        rvCastList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun requestData(movieId: Int){
        // Call MovieDetails
        mMovieBookingModel.getMovieDetails(
            movieId = movieId.toString(),
            onSuccess = {
                bindData(it)
                // Assign Movie Name
                mMovieName = it.originalTitle
            }
        )
    }

    private fun bindData(movie: MovieVO){
        Glide.with(this)
            .load("$Movie_IMAGE_BASE_URL${movie.posterPath}")
            .into(ivMovieImage)
        tvMovieNameLabel.text = movie.originalTitle?: ""
        tvDurationLabel.text = movie.getDurationInHourAndMinute()
        ratingBarMovie.rating = movie.getRatingBasedOnFiveStars()
        tvRatingMovie.text = "IMDb ${movie.getMovieRating()}"
        showPlotSummary(isOverviewEmpty = movie.overview.isNullOrEmpty(), movie)
        showCastList(isCastEmpty = movie.casts.isNullOrEmpty(), movie)
        mGenreListAdapter.setNewData(movie.genresNames?: listOf())
    }

    private fun showPlotSummary(isOverviewEmpty: Boolean, movie: MovieVO){
        if(isOverviewEmpty) tvPlotLabel.visibility = View.GONE
        else {
            tvPlotLabel.visibility = View.VISIBLE
            tvPlot.text = movie.overview
        }
    }

    private fun showCastList(isCastEmpty: Boolean, movie: MovieVO){
        if(isCastEmpty) tvCastLabel.visibility = View.GONE
        else {
            tvCastLabel.visibility = View.VISIBLE
            mCastListAdapter.setNewData(movie.casts?: listOf())
        }
    }
}