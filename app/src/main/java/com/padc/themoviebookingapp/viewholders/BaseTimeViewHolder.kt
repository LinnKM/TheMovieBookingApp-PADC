package com.padc.themoviebookingapp.viewholders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.adapters.TimeListAdapter
import com.padc.themoviebookingapp.data.vos.CinemaVO
import com.padc.themoviebookingapp.delegates.TimeSlotViewHolderDelegate
import kotlinx.android.synthetic.main.view_holder_base_time.*
import kotlinx.android.synthetic.main.view_holder_base_time.view.*

class BaseTimeViewHolder(itemView: View, private val delegate: TimeSlotViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    private var mTimeListAdapter: TimeListAdapter = TimeListAdapter(delegate)

    init {
        itemView.rvTimeList.adapter = mTimeListAdapter
        itemView.rvTimeList.layoutManager = GridLayoutManager(itemView.context, 3)
    }

    fun bindData(cinema: CinemaVO){
        mTimeListAdapter.setNewData(cinema.timeSlots?: listOf())
        itemView.tvLocation.text = cinema.cinema?: ""
    }
}