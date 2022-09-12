package com.padc.themoviebookingapp.adapters

import alirezat775.lib.carouselview.CarouselAdapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.CardVO
import kotlinx.android.synthetic.main.view_holder_payment_card.view.*

class PaymentCardListAdapter: CarouselAdapter() {
    private var mCardList: List<CardVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_payment_card, parent, false)
        return PaymentCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCardList.count()
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        when(holder){
            is PaymentCardViewHolder -> {
                holder.bindData(mCardList[position])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cardList: List<CardVO>){
        mCardList = cardList
        notifyDataSetChanged()
    }

    inner class PaymentCardViewHolder(itemView: View) : CarouselViewHolder(itemView){
        fun bindData(card: CardVO){
            itemView.tvExpireDate.text = card.expirationDate?: ""
            itemView.tvUserName.text = card.cardHolder?: ""

            var cardLastNum = ""
            card.cardNumber?.let {
                for(i in 0..card.cardNumber.lastIndex){
                    if(i in card.cardNumber.lastIndex-3..card.cardNumber.lastIndex){
                        cardLastNum += "${card.cardNumber[i]}"
                    }
                }
            }

            itemView.tvCardLastNum.text = cardLastNum
        }
    }
}