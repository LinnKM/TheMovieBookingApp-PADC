package com.padc.themoviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.persistence.typeconverters.CardTypeConverter

@Entity(tableName = "userProfile")
@TypeConverters(
    CardTypeConverter::class
)
data class UserVO(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String?,

    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    val phone: String?,

    @SerializedName("total_expense")
    @ColumnInfo(name = "total_expense")
    val totalExpense: Int?,

    @SerializedName("profile_image")
    @ColumnInfo(name = "profile_image")
    val profileImage: String?,

    @SerializedName("cards")
    @ColumnInfo(name = "cards")
    val cards: List<CardVO>?,

    @ColumnInfo(name = "user_token")
    var userToken: String?
)

const val UPDATED = "UPDATED"