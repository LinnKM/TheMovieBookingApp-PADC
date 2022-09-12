package com.padc.themoviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.themoviebookingapp.persistence.entities.DateAndCinemaTimeSlotVO

@Dao
interface CinemaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCinemaListByDate(cinemas: DateAndCinemaTimeSlotVO?)

    @Query("SELECT * FROM dateAndCinemaTimeSlots WHERE date = :date")
    fun getCinemasByDate(date: String): DateAndCinemaTimeSlotVO?

    @Query("DELETE FROM dateAndCinemaTimeSlots WHERE date = :date")
    fun deleteCinemasByDate(date: String)

    @Query("DELETE FROM dateAndCinemaTimeSlots")
    fun deleteCinemas()
}