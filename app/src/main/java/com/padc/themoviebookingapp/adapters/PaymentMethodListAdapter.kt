package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.PaymentVO
import com.padc.themoviebookingapp.delegates.PaymentMethodViewHolderDelegate
import com.padc.themoviebookingapp.viewholders.PaymentSectionViewHolder

class PaymentMethodListAdapter(private val delegate: PaymentMethodViewHolderDelegate) :
    RecyclerView.Adapter<PaymentSectionViewHolder>() {
    private var mPaymentMethodList: List<PaymentVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentSectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_payment_section, parent, false)
        return PaymentSectionViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: PaymentSectionViewHolder, position: Int) {
        holder.bindData(mPaymentMethodList[position])
    }

    override fun getItemCount(): Int {
        return mPaymentMethodList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(paymentMethodList: List<PaymentVO>) {
        mPaymentMethodList = paymentMethodList
        notifyDataSetChanged()
    }
}