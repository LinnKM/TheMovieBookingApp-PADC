package com.padc.themoviebookingapp.viewholders

import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.DateVO
import com.padc.themoviebookingapp.delegates.DateSlotViewHolderDelegate
import kotlinx.android.synthetic.main.view_holder_date_section.view.*

class DateSectionViewHolder(itemView: View, delegate: DateSlotViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mDate: DateVO? = null

    init {
        itemView.setOnClickListener {
            mDate?.let {
                delegate.onTapDate(it.day)
            }
        }
    }

    fun bindData(date: DateVO) {
        mDate = date

        if (date.isSelected) {
            changeBackground(R.color.white, 20f)
        } else changeBackground(R.color.unselectedDayColor, 16f)

        itemView.tvDayName.text = date.dayName.removeRange(2, 3)
        itemView.tvDay.text = date.day
    }

    private fun changeBackground(textColor: Int, textSize: Float) {
        itemView.tvDayName.apply {
            setTextColor(ContextCompat.getColor(itemView.context, textColor))
        }

        itemView.tvDay.apply {
            setTextColor(ContextCompat.getColor(itemView.context, textColor))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        }


    }
}