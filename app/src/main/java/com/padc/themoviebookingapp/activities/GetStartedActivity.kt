package com.padc.themoviebookingapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import kotlinx.android.synthetic.main.activity_get_started.*

class GetStartedActivity : AppCompatActivity() {
    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        mMovieBookingModel.getUserToken {
            if(it.isNotEmpty()){
                startActivity(HomeScreenActivity.newIntent(this))
                finish()
            }
        }
        setUpListeners()
    }

    private fun setUpListeners(){
        btnGetStarted.setOnClickListener {
            startActivity(SetUpActivity.newIntent(this))
            finish()
        }
    }
}