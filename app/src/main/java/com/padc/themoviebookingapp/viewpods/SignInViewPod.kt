package com.padc.themoviebookingapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.padc.themoviebookingapp.delegates.ConfirmButtonDelegate
import com.padc.themoviebookingapp.delegates.LoginOrRegisterDelegate
import com.padc.themoviebookingapp.utils.LOGIN_STATE
import kotlinx.android.synthetic.main.view_pod_sign_in.view.*

class SignInViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var mDelegate: ConfirmButtonDelegate

    override fun onFinishInflate() {
        setUpListener()
        super.onFinishInflate()
    }

    fun setDelegate(delegate: ConfirmButtonDelegate) {
        mDelegate = delegate
    }

    private fun setUpListener() {
        // Confirm Button Listener
        btnAccConfirm.setOnClickListener {
            mDelegate.onTapConfirm()
        }
    }
}