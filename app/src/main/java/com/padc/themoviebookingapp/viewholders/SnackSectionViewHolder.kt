package com.padc.themoviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.data.vos.SnackVO
import com.padc.themoviebookingapp.delegates.SnackViewHolderDelegate
import kotlinx.android.synthetic.main.view_holder_snack_section.view.*

class SnackSectionViewHolder(itemView: View, private val delegate: SnackViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mSnack: SnackVO? = null

    init {
        itemView.btnIncrease.setOnClickListener{
            mSnack?.let {
                delegate.onTapSnack(it.id?: 0, '+')
            }
        }

        itemView.btnDecrease.setOnClickListener{
            mSnack?.let {
                delegate.onTapSnack(it.id?: 0, '-')
            }
        }
    }

    fun bindData(snack: SnackVO) {
        mSnack = snack

        itemView.tvComboSetLabel.text = snack.name ?: ""
        itemView.tvComboItemsDescription.text = snack.description ?: ""
        itemView.tvPrice.text = "${snack.price}$"
        itemView.tvAmountLabel.text = snack.quantity.toString()
    }
}