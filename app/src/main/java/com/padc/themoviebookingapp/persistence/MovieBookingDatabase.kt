package com.padc.themoviebookingapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padc.themoviebookingapp.data.vos.MovieVO
import com.padc.themoviebookingapp.data.vos.PaymentVO
import com.padc.themoviebookingapp.data.vos.SnackVO
import com.padc.themoviebookingapp.data.vos.UserVO
import com.padc.themoviebookingapp.network.responses.RegisterUserResponse
import com.padc.themoviebookingapp.persistence.daos.*
import com.padc.themoviebookingapp.persistence.entities.DateAndCinemaTimeSlotVO

// Entity Declaration
@Database(
    entities = [UserVO::class, MovieVO::class, PaymentVO::class, SnackVO::class, DateAndCinemaTimeSlotVO::class],
    version = 6,
    exportSchema = false
)
abstract class MovieBookingDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "THE_MOVIE_BOOKING_DB"

        var dbInstance: MovieBookingDatabase? = null

        fun getDBInstance(context: Context): MovieBookingDatabase? {
            when (dbInstance) {
                null -> dbInstance =
                    Room.databaseBuilder(context, MovieBookingDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return dbInstance
        }
    }

    // Dao Declaration
    abstract fun userDataDao(): UserDataDao
    abstract fun movieDao(): MovieDao
    abstract fun paymentDao(): PaymentDao
    abstract fun snackDao(): SnackDao
    abstract fun cinemaDao(): CinemaDao
}