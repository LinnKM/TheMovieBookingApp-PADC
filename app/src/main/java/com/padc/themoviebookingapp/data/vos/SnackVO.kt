package com.padc.themoviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "snacks")
data class SnackVO(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String?,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String?,

    @ColumnInfo(name = "price")
    @SerializedName("price")
    var price: Int?,

    @ColumnInfo(name = "image")
    @SerializedName("image")
    var image: String?,

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    var quantity: Int = 0,

    @ColumnInfo(name = "unit_price")
    @SerializedName("unit_price")
    var unitPrice: Int?,

    @ColumnInfo(name = "total_price")
    @SerializedName("total_price")
    var totalPrice: Int?
): Serializable