package com.padc.themoviebookingapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.padc.themoviebookingapp.data.vos.UserVO
import com.padc.themoviebookingapp.utils.MOVIE_BOOKING_API_BASE_URL
import kotlinx.android.synthetic.main.view_pod_custom_drawer.view.*

class CustomDrawerViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    fun bindData(user: UserVO){
        tvDrawerEmail.text = user.email?: ""
        tvDrawerProfileName.text = user.name?: ""
        Glide.with(this)
            .load("$MOVIE_BOOKING_API_BASE_URL${user.profileImage}")
            .into(ivDrawerProfilePic)
    }
}