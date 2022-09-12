package com.padc.themoviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreNameTypeConverter {

    @TypeConverter
    fun toString(genreNameList: List<String>?): String {
        return Gson().toJson(genreNameList)
    }

    @TypeConverter
    fun toGenreNames(genreNameListJsonString: String): List<String>? {
        val genreNameListType = object: TypeToken<List<String>?>(){}.type
        return Gson().fromJson(genreNameListJsonString, genreNameListType)
    }
}