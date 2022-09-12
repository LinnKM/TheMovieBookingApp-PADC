package com.padc.themoviebookingapp.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.padc.themoviebookingapp.data.vos.CinemaVO
import com.padc.themoviebookingapp.persistence.typeconverters.CinemaTimeSlotTypeConverter

@Entity(tableName = "dateAndCinemaTimeSlots")
@TypeConverters(
    CinemaTimeSlotTypeConverter::class
)
data class DateAndCinemaTimeSlotVO(

    @ColumnInfo(name = "date")
    @PrimaryKey
    val date: String,

    @ColumnInfo(name = "cinemas")
    val cinemas: List<CinemaVO>?
)