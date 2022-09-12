package com.padc.themoviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themoviebookingapp.data.vos.CastVO

class CastTypeConverter {

    @TypeConverter
    fun toString(castList: List<CastVO>?): String {
        return Gson().toJson(castList)
    }

    @TypeConverter
    fun toCasts(castListJsonString: String): List<CastVO>? {
        val castListType = object :TypeToken<List<CastVO>?>(){}.type
        return Gson().fromJson(castListJsonString, castListType)
    }
}