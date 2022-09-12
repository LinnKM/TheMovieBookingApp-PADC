package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.MovieSeatVO
import com.padc.themoviebookingapp.delegates.CinemaSeatViewHolderDelegate
import com.padc.themoviebookingapp.viewholders.MovieSeatViewHolder

class MovieSeatAdapter(private val delegate: CinemaSeatViewHolderDelegate) :
    RecyclerView.Adapter<MovieSeatViewHolder>() {
    private var mMovieSeats: List<MovieSeatVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSeatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_seat, parent, false)
        return MovieSeatViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: MovieSeatViewHolder, position: Int) {
        if (mMovieSeats.isNotEmpty()) {
            holder.bindData(mMovieSeats[position])
        }
    }

    override fun getItemCount(): Int {
        return mMovieSeats.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(moviesSeats: List<MovieSeatVO>) {
        this.mMovieSeats = moviesSeats
        notifyDataSetChanged()
    }
}