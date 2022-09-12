package com.padc.themoviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_genre_chip.view.*

class GenreChipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(genre: String){
        itemView.tvGenreLabel.text = genre
    }
}