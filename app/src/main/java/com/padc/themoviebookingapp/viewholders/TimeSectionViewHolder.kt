package com.padc.themoviebookingapp.viewholders

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.TimeSlotVO
import com.padc.themoviebookingapp.delegates.TimeSlotViewHolderDelegate
import kotlinx.android.synthetic.main.view_holder_time_section.view.*

class TimeSectionViewHolder(itemView: View, private val delegate: TimeSlotViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mTimeSlot: TimeSlotVO? = null

    init {
        itemView.setOnClickListener {
            mTimeSlot?.cinemaDayTimeSlot?.let {
                delegate.onTapTimeSlot(it)
            }
        }
    }

    fun bindData(timeSlot: TimeSlotVO) {
        mTimeSlot = timeSlot
        if (timeSlot.isSelected) {
            changeBackground(
                timeSlotBackground = R.drawable.selected_background_button,
                textColor = R.color.white
            )
        } else {
            changeBackground(
                timeSlotBackground = R.drawable.background_transparent_button,
                textColor = R.color.black
            )
        }
        itemView.tvTimeNumber.text = timeSlot.startTime?.split(" ")?.first() ?: ""
        itemView.tvTimeAmPm.text = timeSlot.startTime?.split(" ")?.last() ?: ""
    }

    private fun changeBackground(timeSlotBackground: Int, textColor: Int) {
        itemView.rlTimeSlot.background = ContextCompat.getDrawable(
            itemView.context,
            timeSlotBackground
        )

        itemView.tvTimeNumber.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                textColor
            )
        )

        itemView.tvTimeAmPm.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                textColor
            )
        )
    }
}