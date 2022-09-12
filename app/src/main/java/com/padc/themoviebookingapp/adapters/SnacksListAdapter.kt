package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.SnackVO
import com.padc.themoviebookingapp.delegates.SnackViewHolderDelegate
import com.padc.themoviebookingapp.viewholders.SnackSectionViewHolder

class SnacksListAdapter(private val delegate: SnackViewHolderDelegate): RecyclerView.Adapter<SnackSectionViewHolder>() {
    private var mSnackList: List<SnackVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackSectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_snack_section, parent, false)
        return SnackSectionViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: SnackSectionViewHolder, position: Int) {
        holder.bindData(mSnackList[position])
    }

    override fun getItemCount(): Int {
        return mSnackList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(snackList: List<SnackVO>){
        mSnackList = snackList
        notifyDataSetChanged()
    }
}