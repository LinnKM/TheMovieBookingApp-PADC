package com.padc.themoviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.CastVO
import com.padc.themoviebookingapp.utils.MOVIE_BOOKING_API_BASE_URL
import com.padc.themoviebookingapp.utils.Movie_IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_cast.view.*

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(cast: CastVO){
        Glide.with(itemView.context)
            .load("$Movie_IMAGE_BASE_URL${cast.profilePath}")
            .placeholder(R.drawable.grey_loading_placeholder)
            .into(itemView.ivActorImage)
        itemView.tvActorName.text = cast.name?: ""
    }
}