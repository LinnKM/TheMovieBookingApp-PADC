package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.TimeSlotVO
import com.padc.themoviebookingapp.delegates.TimeSlotViewHolderDelegate
import com.padc.themoviebookingapp.viewholders.TimeSectionViewHolder

class TimeListAdapter(private val delegate: TimeSlotViewHolderDelegate): RecyclerView.Adapter<TimeSectionViewHolder>() {
    private var mTimeSlotList: List<TimeSlotVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_time_section,parent, false)
        return TimeSectionViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: TimeSectionViewHolder, position: Int) {
        if(mTimeSlotList.isNotEmpty()){
            holder.bindData(mTimeSlotList[position])
        }
    }

    override fun getItemCount(): Int {
        return mTimeSlotList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(timeSlotList: List<TimeSlotVO>){
        mTimeSlotList = timeSlotList
        notifyDataSetChanged()
    }
}