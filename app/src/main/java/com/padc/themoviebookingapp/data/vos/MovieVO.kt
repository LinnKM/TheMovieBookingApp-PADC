package com.padc.themoviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.padc.themoviebookingapp.persistence.typeconverters.CastTypeConverter
import com.padc.themoviebookingapp.persistence.typeconverters.GenreIdTypeConverter
import com.padc.themoviebookingapp.persistence.typeconverters.GenreNameTypeConverter

@Entity(tableName = "movies")
@TypeConverters(
    GenreIdTypeConverter::class,
    GenreNameTypeConverter::class,
    CastTypeConverter::class
)
data class MovieVO(
    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @SerializedName("genre_ids")
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>?,

    @SerializedName("genres")
    @ColumnInfo(name = "genres")
    val genresNames: List<String>?,

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    val overview: String?,

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String?,

    @SerializedName("video")
    @ColumnInfo(name = "video")
    val video: Boolean?,

    @SerializedName("rating")
    @ColumnInfo(name = "rating")
    val rating: Double?,

    @SerializedName("runtime")
    @ColumnInfo(name = "runtime")
    val runtime: Int?,

    @SerializedName("casts")
    @ColumnInfo(name = "casts")
    val casts: List<CastVO>?,

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,

    @ColumnInfo(name = "type")
    var type: String?
){
    // RunTime In Hour Minute
    fun getDurationInHourAndMinute(): String{
        runtime?.let {
            val runTime = (it.toDouble() / 60)
            val hoursInTwoDecimal = String.format("%.2f", runTime)
            val h = hoursInTwoDecimal.split(".").first()
            val minute = hoursInTwoDecimal.split(".").last()
            val m =(minute.toDouble() / 100 * 60).toInt()

            return if(h == "0") "${m}min" else "${h}h ${m}m"
        }
        return ""
    }

    // Rating Based On Five Stars
    fun getRatingBasedOnFiveStars(): Float{
        return rating?.div(2)?.toFloat()?: 0.0f
    }

    // Rating Value With Two Decimal Places
    fun getMovieRating(): String{
        rating?.let {
            return String.format("%.1f", rating)
        }
        return "-"
    }
}