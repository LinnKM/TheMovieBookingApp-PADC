package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.data.vos.CheckOutVO
import com.padc.themoviebookingapp.data.vos.MovieVO
import com.padc.themoviebookingapp.utils.Movie_IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_receipt.*
import kotlinx.android.synthetic.main.view_holder_snack_section.*
import kotlinx.coroutines.selects.select
import java.io.Serializable
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReceiptActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_SUCCESSFUL_CHECKOUT = "EXTRA_SUCCESSFUL_CHECKOUT"
        private const val EXTRA_CINEMA_NAME = "EXTRA_CINEMA_NAME"

        fun newIntent(
            context: Context,
            successfulCheckOut: CheckOutVO,
            cinemaName: String
        ): Intent {
            val intent = Intent(context, ReceiptActivity::class.java)
                .putExtra(EXTRA_SUCCESSFUL_CHECKOUT, successfulCheckOut)
                .putExtra(EXTRA_CINEMA_NAME, cinemaName)
            return intent
        }
    }

    private var mSuccessfulCheckOut: CheckOutVO? = null
    private var mCinemaName: String? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingImpl

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        setUpToolbar()
        // Get Data From Intent
        mSuccessfulCheckOut = intent?.getSerializableExtra(EXTRA_SUCCESSFUL_CHECKOUT) as CheckOutVO
        mCinemaName = intent?.getStringExtra(EXTRA_CINEMA_NAME)

        mSuccessfulCheckOut?.let {
            bindData(it)
        }
        setUpListeners()
    }

    private fun setUpListeners() {

        // Close button listener
        toolBarReceiptScreen.setNavigationOnClickListener {
            finish()
            startActivity(HomeScreenActivity.newIntent(this))
        }
    }

    override fun onBackPressed() {
        finish()
        startActivity(HomeScreenActivity.newIntent(this))
        super.onBackPressed()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolBarReceiptScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_48dp)

        // Status Bar Color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorGrayLight)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindData(successfulCheckOut: CheckOutVO) {
        val movieId = successfulCheckOut.movieId ?: 0
        var selectedMovie: MovieVO
        mMovieBookingModel.getMovieDetails(
            movieId = movieId.toString(),
            onSuccess = {
                selectedMovie = it
                Glide.with(this)
                    .load("$Movie_IMAGE_BASE_URL${selectedMovie.posterPath}")
                    .into(ivMovie)

                tvMovieName.text = selectedMovie.originalTitle ?: ""
                tvDuration.text = "${selectedMovie.runtime}m - IMAX"
            }
        )

        tvTimeAndDate.text =
            "${successfulCheckOut.timeSlot?.startTime} - ${getFormattedDate(successfulCheckOut.bookingDate ?: "")}"
        tvBookingNumbers.text = successfulCheckOut.bookingNo ?: ""
        tvRowType.text = successfulCheckOut.row ?: ""
        tvSeatNumbers.text = successfulCheckOut.seat ?: ""
        tvAmount.text = "${successfulCheckOut.total}.00"
        tvTheaterName.text = mCinemaName ?: ""

        generateBarCode(
            barCode = successfulCheckOut.bookingNo ?: "",
            resources.getDimensionPixelSize(R.dimen.barCode_width),
            resources.getDimensionPixelSize(R.dimen.barCode_height)
        )
    }

    // Get Formatted Date
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
        val formatter = DateTimeFormatter.ofPattern("d MMMM")
        return bookingDate.format(formatter)
    }

    // Generate BarCode
    private fun generateBarCode(barCode: String, width: Int, height: Int) {
        val multiFormatWriter = MultiFormatWriter()

        try {
            val bitMatrix = multiFormatWriter.encode(
                barCode,
                BarcodeFormat.CODE_128,
                width,
                height
            )
            val bitMap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (i in 0 until width) {
                for (j in 0 until height) {
                    bitMap.setPixel(i, j, if (bitMatrix.get(i, j)) Color.BLACK else Color.WHITE)
                }
            }
            ivBarCode.setImageBitmap(bitMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}