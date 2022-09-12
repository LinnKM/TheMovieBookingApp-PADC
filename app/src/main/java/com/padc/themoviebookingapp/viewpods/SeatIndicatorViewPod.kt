package com.padc.themoviebookingapp.viewpods

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_pod_seat_indicator.view.*

class SeatIndicatorViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    fun setUpViewPod(title: String, background: Int){
        viewSeatIndicator.background = ContextCompat.getDrawable(context, background)
        tvTitle.text = title
    }
}