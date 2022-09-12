package com.padc.themoviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themoviebookingapp.data.vos.CinemaVO

class CinemaTimeSlotTypeConverter {

    @TypeConverter
    fun toString(cinemaList: List<CinemaVO>?): String {
        return Gson().toJson(cinemaList)
    }

    @TypeConverter
    fun toCinemas(cinemaListJsonString: String): List<CinemaVO>? {
        val cinemaListType = object: TypeToken<List<CinemaVO>?>(){}.type
        return Gson().fromJson(cinemaListJsonString, cinemaListType)
    }
}