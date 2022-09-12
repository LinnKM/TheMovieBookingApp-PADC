package com.padc.themoviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.vos.CastVO
import com.padc.themoviebookingapp.viewholders.CastViewHolder

class CastListAdapter: RecyclerView.Adapter<CastViewHolder>() {
    private var mCastList: List<CastVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        if(mCastList.isNotEmpty()){
            holder.bindData(mCastList[position])
        }
    }

    override fun getItemCount(): Int {
        return mCastList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(castList: List<CastVO>){
        mCastList = castList
        notifyDataSetChanged()
    }
}