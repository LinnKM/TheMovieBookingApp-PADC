package com.padc.themoviebookingapp.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.PaymentVO
import com.padc.themoviebookingapp.delegates.PaymentMethodViewHolderDelegate
import kotlinx.android.synthetic.main.view_holder_payment_section.view.*

class PaymentSectionViewHolder(itemView: View, delegate: PaymentMethodViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mPaymentMethod: PaymentVO? = null

    init {
        itemView.setOnClickListener {
            mPaymentMethod?.id?.let { id ->
                delegate.onTapPaymentMethod(id)
            }
        }
    }

    fun bindData(paymentMethod: PaymentVO) {
        mPaymentMethod = paymentMethod

        when {
            paymentMethod.isSelected -> changeBackground(R.color.colorPrimary, R.color.colorPrimary, R.drawable.ic_baseline_credit_card_primarycolor_24dp)
            else -> changeBackground(R.color.black, R.color.colorPrimaryTextLight, R.drawable.ic_baseline_credit_card_primarytextlightcolor_24dp)
        }

        itemView.tvPaymentLabel.text = paymentMethod.name ?: ""
        itemView.tvPaymentDescription.text = paymentMethod.description ?: ""

    }

    private fun changeBackground(colorOne: Int, colorTwo: Int, icon: Int) {
        itemView.tvPaymentLabel.setTextColor(ContextCompat.getColor(itemView.context, colorOne))
        itemView.tvPaymentDescription.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                colorTwo
            )
        )
        itemView.ivPaymentIcon.setImageResource(icon)
    }
}