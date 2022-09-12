package com.padc.themoviebookingapp

import android.app.Application
import com.padc.themoviebookingapp.activities.HomeScreenActivity
import com.padc.themoviebookingapp.activities.SetUpActivity
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.persistence.MovieBookingDatabase

class MovieBookingApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        MovieBookingImpl.initDatabase(applicationContext)
    }
}