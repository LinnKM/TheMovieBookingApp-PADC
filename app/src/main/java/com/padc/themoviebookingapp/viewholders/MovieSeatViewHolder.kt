package com.padc.themoviebookingapp.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.MovieSeatVO
import com.padc.themoviebookingapp.delegates.CinemaSeatViewHolderDelegate
import kotlinx.android.synthetic.main.view_holder_movie_seat.view.*

class MovieSeatViewHolder(itemView: View, private val delegate: CinemaSeatViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mMovieSeat: MovieSeatVO? = null

    init {
        itemView.setOnClickListener {
            mMovieSeat?.let { movieSeat ->
                delegate.onTapSeat(movieSeat.seatName ?: "")
            }
        }
    }

    fun bindData(data: MovieSeatVO) {
        mMovieSeat = data
        when {
            data.isMovieSeatAvailable() -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_movie_seat_available
                )
            }

            data.isMovieSeatTaken() -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_movie_seat_taken
                )
            }

            data.isMovieSeatEmpty() -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }

            else -> {
                itemView.tvMovieSeatTitle.visibility = View.VISIBLE
                itemView.tvMovieSeatTitle.text = data.symbol
                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
            }
        }

        if (data.isSelected) {
            itemView.tvMovieSeatTitle.visibility = View.VISIBLE
            itemView.tvMovieSeatTitle.text = data.seatName?.split("-")?.last() ?: ""
            itemView.tvMovieSeatTitle.setTextColor(ContextCompat.getColor(
                itemView.context,
                R.color.white
            ))
            itemView.background =
                ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_movie_seat_selected
                )
        }
    }
}