package com.padc.themoviebookingapp.viewpods

import android.content.Context
import android.graphics.Movie
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.themoviebookingapp.adapters.MovieListAdapter
import com.padc.themoviebookingapp.data.vos.MovieVO
import com.padc.themoviebookingapp.delegates.MovieViewHolderDelegate
import kotlinx.android.synthetic.main.view_pod_movie_list.*
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class MovieListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var mMovieListAdapter: MovieListAdapter
    private lateinit var mDelegate: MovieViewHolderDelegate

    fun setUpViewPod(delegate: MovieViewHolderDelegate, titleText: String){
        setDelegate(delegate)
        setUpRecyclerView()
        tvShowTimeLabel.text = titleText
    }

    private fun setDelegate(delegate: MovieViewHolderDelegate){
        mDelegate = delegate
    }

    private fun setUpRecyclerView(){
        mMovieListAdapter = MovieListAdapter(mDelegate)
        rvMovieList.adapter = mMovieListAdapter
        rvMovieList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setData(movieList: List<MovieVO>){
        mMovieListAdapter.setNewData(movieList)
    }

}