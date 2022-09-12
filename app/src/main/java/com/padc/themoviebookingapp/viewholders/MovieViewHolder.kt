package com.padc.themoviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.MovieVO
import com.padc.themoviebookingapp.delegates.MovieViewHolderDelegate
import com.padc.themoviebookingapp.utils.Movie_IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie.view.*

class MovieViewHolder(itemView: View, private val delegate: MovieViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.id?.let { id ->
                delegate.onTapMovie(id)
            }
        }
    }

    fun bindData(movie: MovieVO){
        mMovie = movie
        Glide.with(itemView.context)
            .load("$Movie_IMAGE_BASE_URL${movie.posterPath}")
            .placeholder(R.drawable.grey_loading_placeholder)
            .into(itemView.ivMovie)

        itemView.tvMovieNameLabel.text = movie.originalTitle?: ""
        val firstGenre = movie.genresNames?.firstOrNull()?: ""
        val secondGenre = movie.genresNames?.getOrNull(1)?: ""
        itemView.tvGenreAndDuration.text = "$firstGenre/$secondGenre"
    }
}