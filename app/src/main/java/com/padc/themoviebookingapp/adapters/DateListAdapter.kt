package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.DateVO
import com.padc.themoviebookingapp.delegates.DateSlotViewHolderDelegate
import com.padc.themoviebookingapp.viewholders.DateSectionViewHolder

class DateListAdapter(private val delegate: DateSlotViewHolderDelegate): RecyclerView.Adapter<DateSectionViewHolder>() {
    private var mDateList: List<DateVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateSectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_date_section, parent, false)
        return DateSectionViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: DateSectionViewHolder, position: Int) {
        if(mDateList.isNotEmpty()){
            holder.bindData(mDateList[position])
        }
    }

    override fun getItemCount(): Int {
        return mDateList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(dateList: List<DateVO>){
        mDateList = dateList
        notifyDataSetChanged()
    }
}