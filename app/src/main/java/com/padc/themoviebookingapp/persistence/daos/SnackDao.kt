package com.padc.themoviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.themoviebookingapp.data.vos.SnackVO

@Dao
interface SnackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSnacks(snacks: List<SnackVO>)

    @Query("SELECT * FROM snacks")
    fun getSnacks(): List<SnackVO>

    @Query("DELETE FROM snacks")
    fun deleteSnacks()
}