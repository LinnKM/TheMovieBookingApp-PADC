package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.viewholders.GenreChipViewHolder

class GenreChipListAdapter: RecyclerView.Adapter<GenreChipViewHolder>() {
    private var mGenreList: List<String> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreChipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_genre_chip,parent , false)
        return GenreChipViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreChipViewHolder, position: Int) {
        if(mGenreList.isNotEmpty()){
            holder.bindData(mGenreList[position])
        }
    }

    override fun getItemCount(): Int {
        return mGenreList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(genreList: List<String>){
        mGenreList = genreList
        notifyDataSetChanged()
    }
}