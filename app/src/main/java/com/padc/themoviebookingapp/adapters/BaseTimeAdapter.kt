package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.CinemaVO
import com.padc.themoviebookingapp.delegates.TimeSlotViewHolderDelegate
import com.padc.themoviebookingapp.viewholders.BaseTimeViewHolder

class BaseTimeAdapter(private val delegate: TimeSlotViewHolderDelegate): RecyclerView.Adapter<BaseTimeViewHolder>() {
    private var mCinemaList: List<CinemaVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_base_time, parent, false)
        return BaseTimeViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: BaseTimeViewHolder, position: Int) {
        if(mCinemaList.isNotEmpty()){
            holder.bindData(mCinemaList[position])
        }
    }

    override fun getItemCount(): Int {
        return mCinemaList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cinemaList: List<CinemaVO>){
        mCinemaList = cinemaList
        notifyDataSetChanged()
    }
}