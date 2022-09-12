package com.padc.themoviebookingapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themoviebookingapp.data.vos.CardVO

class CardTypeConverter {

    @TypeConverter
    fun toString(cardList: List<CardVO>?): String {
        return Gson().toJson(cardList)
    }

    @TypeConverter
    fun toCards(cardListJsonString: String): List<CardVO>? {
        val cardListType = object :TypeToken<List<CardVO>?>(){}.type
        return Gson().fromJson(cardListJsonString, cardListType)
    }
}